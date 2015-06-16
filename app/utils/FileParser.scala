package utils

import java.io.File
import java.text.SimpleDateFormat

import dao.TransactionDao
import model.Transaction
import play.api.Logger

import scala.io.Source
import scala.util.Try

/**
 * Created by Richard on 16/06/2015.
 */
class FileParser(dao: TransactionDao) {
  val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def parse(fileLocation: File) = {
    Source.fromFile(fileLocation).getLines().foreach(line => {
      Try({
        val txn = parseLine(line)
        dao.storeTransaction(txn)
      }) recover {
        case e => Logger.warn(s"Error storing $line", e)
      }
    })
  }

  def parseDate(date:String) = new java.sql.Date(simpleDateFormat.parse(date).getTime)

  def parseLine(line:String) = {
    Logger.info(s"$line")
    val banana = line.split(";")
    val date = parseDate(banana(0))
    val txnType = banana(1)
    val recipient = banana(2)
    Logger.info(s"big decimal attempt ${banana(3)}")
    val amount = BigDecimal(banana(3).replace("£", ""))
    Transaction.build(date, txnType, "joint", amount, recipient)
  }
}
