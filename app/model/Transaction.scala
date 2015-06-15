package model

/**
 * Created by Richard on 17/05/2015.
 */
object Transaction {
  def build(id:String, account:String, amount:BigDecimal, recipient:String) = Transaction(Option(id), Option(account),Option(amount),Option(recipient))
}

case class Transaction(id: Option[String], account: Option[String], amount: Option[BigDecimal], recipient: Option[String]) {



  def isComplete = {
    id.isDefined && account.isDefined && amount.isDefined && recipient.isDefined
  }

}
