package controllers

import model.Transaction
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  val transactionForm: Form[Transaction] = Form(
    mapping(
      ("id", nonEmptyText),
      ("account", nonEmptyText),
      ("amount", bigDecimal),
      ("recipient", nonEmptyText)
    )(Transaction.apply)(Transaction.unapply)
  )

  def index = Action {

    Ok(views.html.index(transactionForm))
  }

  def txnSubmit = Action { implicit request =>
    val transaction = transactionForm.bindFromRequest.get
    Ok(views.html.confirmation(transaction))
  }

}