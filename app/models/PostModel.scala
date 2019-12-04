package models

import org.joda.time.DateTime

import play.api.data._
import play.api.data.Forms.{ text, longNumber, mapping, nonEmptyText, optional }
import play.api.data.validation.Constraints.pattern

case class PostModel(
                      id: Option[String],
                      title: String,
                      description: String,
                      body: String,
                      author: String,
                      createdAt: Option[DateTime],
                      updatedAt: Option[DateTime]
                    )

object PostModel {
  import play.api.libs.json._

  implicit object PostWrites extends OWrites[PostModel] {
    def writes(post: PostModel): JsObject = Json.obj(
      "_id" -> post.id,
      "title" -> post.title,
      "description" -> post.description,
      "body" -> post.body,
      "author" -> post.author,
      "createdAt" -> post.createdAt.fold(-1L)(_.getMillis),
      "updatedAt" -> post.updatedAt.fold(-1L)(_.getMillis))
  }

  implicit object PostReads extends Reads[PostModel] {
    def reads(json: JsValue): JsResult[PostModel] = json match {
      case obj: JsObject => try {
        val id = (obj \ "_id").asOpt[String]
        val title = (obj \ "title").as[String]
        val description = (obj \ "description").as[String]
        val body = (obj \ "body").as[String]
        val author = (obj \ "author").as[String]
        val createdAt = (obj \ "createdAt").asOpt[Long]
        val updatedAt = (obj \ "updatedAt").asOpt[Long]

        JsSuccess(
          PostModel(
            id,
            title,
            description,
            body,
            author,
            createdAt.map(new DateTime(_)),
            updatedAt.map(new DateTime(_))
          )
        )

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }

      case _ => JsError("expected.jsobject")
    }
  }

  val form = Form(
    mapping(
      "id" -> optional(text verifying pattern("""[a-fA-F0-9]{24}""".r, error = "error.objectId")),
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "body" -> nonEmptyText,
      "author" -> nonEmptyText,
      "creationAt" -> optional(longNumber),
      "updateAt" -> optional(longNumber)) {
      (id, title, description, body, author, createdAt, updatedAt) =>
        PostModel(
          id,
          title,
          description,
          body,
          author,
          createdAt.map(new DateTime(_)),
          updatedAt.map(new DateTime(_)))
    } { post =>
      Some(
        (post.id,
          post.title,
          post.description,
          post.body,
          post.author,
          post.createdAt.map(_.getMillis),
          post.updatedAt.map(_.getMillis)
        )
      )
    })
}
