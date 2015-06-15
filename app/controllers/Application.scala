package controllers

import dao.TransactionDao
import model.{TransactionList, Transaction}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  val transactionDao = new TransactionDao

  val transactionForm: Form[TransactionList] = Form(
    mapping(
      "transactionList" -> seq(
        mapping(
          "id" -> optional(nonEmptyText),
          "account" -> optional(nonEmptyText),
          "amount" -> optional(bigDecimal),
          "recipient" -> optional(nonEmptyText)
        )(Transaction.apply)(Transaction.unapply)
      )
    )(TransactionList.apply)(TransactionList.unapply)
  )


  def index = Action {
    Ok(views.html.index(transactionForm))
  }

  def view = Action {
    Ok(views.html.view(transactionDao.loadAll))
  }

  def txnSubmit = Action { implicit request =>
    val transaction = transactionForm.bindFromRequest.get
    transaction.transactionList.filter(x => x.isComplete).foreach(transactionDao.storeTransaction)
    Ok(views.html.confirmation(transaction))
  }

}