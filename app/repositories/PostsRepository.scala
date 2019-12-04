package repositories

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import models.PostModel
import reactivemongo.api.commands.WriteResult

class PostsRepository @Inject()(
                              implicit ec: ExecutionContext,
                              reactiveMongoApi: ReactiveMongoApi
                              ){

  private def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("posts"))

  def list(limit: Int = 5): Future[Seq[PostModel]] = collection.flatMap(_
      .find(BSONDocument(), Option.empty[BSONDocument])
      .cursor[PostModel](ReadPreference.primary)
      .collect[Seq](limit, Cursor.FailOnError[Seq[PostModel]]())
  )

  def create(post: PostModel): Future[WriteResult] = collection.flatMap(_.insert(false).one(post))

  def read(id: BSONObjectID): Future[Option[PostModel]] =
    collection.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[BSONDocument]).one[PostModel])

  def update(post: PostModel): Future[Option[PostModel]] = collection.flatMap(
    _.findAndUpdate(
      BSONDocument("_id" -> post.id),
      BSONDocument(
        f"$$set" -> BSONDocument(
          "title" -> post.title,
          "description" -> post.description,
          "body" -> post.body,
          "author" -> post.author,
          "createdAt" -> post.createdAt.fold(-1L)(_.getMillis),
          "updatedAt" -> post.updatedAt.fold(-1L)(_.getMillis),
        )
      ),
      true
    ).map(_.result[PostModel])
  )

  def delete(id: BSONObjectID): Future[Option[PostModel]] = collection.flatMap(
    _.findAndRemove(BSONDocument("_id" -> id)).map(_.result[PostModel])
  )

}
