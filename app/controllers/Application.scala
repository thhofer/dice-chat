package controllers

import javax.inject.Inject

import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.mvc._

class Application @Inject()(val messagesApi: MessagesApi)  extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index(Chat.userForm))
  }

}