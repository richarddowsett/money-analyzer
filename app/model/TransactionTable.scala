package model


import java.sql.Date
import java.time.LocalDate

import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.JdbcType
import scala.slick.lifted.ProvenShape

/**
 * Created by Richard on 29/05/2015.
 */
class TransactionTable(tag: Tag) extends Table[(Date, String, String, BigDecimal,String)](tag, "TRANSACTIONS") {


  def date = column[Date]("DATE")

  def txnType = column[String]("TYPE")

  def account = column[String]("ACCOUNT")

  def amount = column[BigDecimal]("AMOUNT")

  def recipient = column[String]("RECIPIENT")

  override def * : ProvenShape[(Date, String, String, BigDecimal,String)] = (date, txnType, account, amount, recipient)
}


