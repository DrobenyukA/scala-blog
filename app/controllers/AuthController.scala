package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class AuthController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def login = TODO

  def logout = TODO

  def register = TODO

  def renderLoginForm = Action { implicit request: RequestHeader =>
    Ok(views.html.login.form())
  }

  def renderRegistrationForm = TODO

}
