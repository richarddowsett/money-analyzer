package controllers

import java.text.SimpleDateFormat

import dao.TransactionDao
import model.{Transaction, TransactionList}
import play.api.Logger
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import utils.FileParser

import scala.concurrent.Future

object Application extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  val transactionDao = new TransactionDao
  val fileParser = new FileParser(transactionDao)


  val transactionForm: Form[TransactionList] = Form(
    mapping(
      "transactionList" -> seq(
        mapping(
          "date" -> optional(sqlDate),
          "txnType" -> optional(nonEmptyText),
          "account" -> optional(nonEmptyText),
          "amount" -> optional(bigDecimal),
          "recipient" -> optional(nonEmptyText)
        )(Transaction.apply)(Transaction.unapply)
      )
    )(TransactionList.apply)(TransactionList.unapply)
  )


  def index = Action {
    Logger.info("Loading index page")
    Ok(views.html.index(transactionForm))
  }

  def view = Action {
    Logger.info("Loading view page")
    Ok(views.html.view(transactionDao.loadAll()))
  }

  def txnSubmit = Action { implicit request =>
    val transaction = transactionForm.bindFromRequest.get
    transaction.transactionList.filter(x => x.isComplete).foreach(transactionDao.storeTransaction)
    Ok(views.html.confirmation(transaction))
  }

  def upload = Action(parse.multipartFormData) { request =>
    Logger.info("Working on file upload")

    request.body.file("transactions").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      val fileLocation = new File(s"C:\\temp\\$filename")
      Logger.info(s"moved file to $fileLocation")
      picture.ref.moveTo(fileLocation)
      Logger.info(filename)
      Future {
        fileParser.parse(fileLocation)
        }
      Ok("File uploaded")
    }.getOrElse {
      Logger.info("blah blah bala")
      Redirect("/view").flashing(
        "error" -> "Missing file")
    }
  }

}