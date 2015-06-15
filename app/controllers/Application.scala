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
          "id" -> nonEmptyText,
          "account" -> nonEmptyText,
          "amount" -> bigDecimal,
          "recipient" -> nonEmptyText
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
    transaction.transactionList.foreach(transactionDao.storeTransaction)
    Ok(views.html.confirmation(transaction))
  }

}