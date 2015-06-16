package model

import java.sql.Date

/**
 * Created by Richard on 17/05/2015.
 */
object Transaction {
  def build(date: Date, txnType: String, account: String, amount: BigDecimal, recipient: String) = Transaction(Option(date), Option(txnType), Option(account), Option(amount), Option(recipient))
}

case class Transaction(date: Option[Date], txnType: Option[String], account: Option[String], amount: Option[BigDecimal], recipient: Option[String]) {


  def isComplete = {
    date.isDefined && txnType.isDefined && account.isDefined && amount.isDefined && recipient.isDefined
  }

}
