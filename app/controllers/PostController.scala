package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import repositories.PostsRepository

@Singleton
class PostController @Inject()(
                                implicit  ec: ExecutionContext,
                                cc: ControllerComponents,
                                postsRepository: PostsRepository
                              )
  extends AbstractController(cc) {

  def renderBlog() = Action.async { implicit request: RequestHeader =>
    postsRepository.list().map(
      posts => Ok(views.html.blog.index(posts))
    )
  }

  def renderPostFrom(id: String = "") = Action { implicit request: RequestHeader =>
    Ok(views.html.blog.postFrom())
  }

  def renderPost(id: String) = Action.async { implicit request: RequestHeader =>
    postsRepository.list().map(
      posts => Ok(views.html.blog.post(posts.head))
    )
  }

  def createPost() = TODO

  def updatePost(id: String) = TODO

  def deletePost(id: String) = TODO

}
