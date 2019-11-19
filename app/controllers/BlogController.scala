package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class BlogController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: RequestHeader =>
    val posts = List("Post 5", "Post 4", "Post 3", "Post 2", "Post 1")
    Ok(views.html.blog.index(posts))
  }

  def renderPostFrom(id: String) = TODO

  def renderPost(id: String) = Action { implicit request: RequestHeader =>
    Ok(views.html.blog.post(id))
  }

  def createPost() = TODO

  def updatePost(id: String) = TODO

  def deletePost(id: String) = TODO
}
