package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class AuthController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def login = Action { implicit request =>
    def data = request.body.asFormUrlEncoded
    data.map { item =>
      val login = item("login").head
      val pw = item("password").head
      // TODO: verify user
      // TODO: store user JWT in session
      Redirect(routes.PostController.renderBlog).withSession("email" -> login)
//      Ok(s"User $login logged in with password $pw")
    }.getOrElse(
      // TODO: render form with error
      Redirect(routes.AuthController.renderLoginForm)
    )
  }

  def logout = Action { implicit request =>
    Redirect(routes.AuthController.login).withNewSession
  }

  def register = TODO

  def renderLoginForm = Action { implicit request =>
    Ok(views.html.login.form())
  }

  def renderRegistrationForm = Action { implicit request =>
    Ok(views.html.login.registerFrom())
  }

}
