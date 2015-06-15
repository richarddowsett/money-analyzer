package model

import slick.lifted.ProvenShape

import scala.slick.driver.H2Driver.simple._

/**
 * Created by Richard on 29/05/2015.
 */
class TransactionTable(tag: Tag) extends Table[(String, String, BigDecimal, String)](tag, "TRANSACTIONS") {

  def id = column[String]("TRANSACTION_ID")

  def account = column[String]("ACCOUNT")

  def amount = column[BigDecimal]("AMOUNT")

  def recipient = column[String]("RECIPIENT")

  override def * : ProvenShape[(String, String, BigDecimal, String)] = (id, account, amount, recipient)
}


