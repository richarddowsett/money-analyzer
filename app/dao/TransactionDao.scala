package dao

import model.{TransactionTable, Transaction}
import play.api.db._
import slick.driver.H2Driver.api._
import slick.lifted.ExtensionMethodConversions
/**
 * Created by Richard on 17/05/2015.
 */
class TransactionDao {
  import play.api.Play.current
  val ds = DB.getDataSource()
  val transactions = TableQuery[TransactionTable]
  transactions.schema.create

  def storeTransaction(txn:Transaction) = {
    transactions += (txn.id, txn.account, txn.amount, txn.recipient)
  }

  def loadAll():List[TransactionTable] = {
    val temp = for(t <- transactions.take(10))yield{
      (t.id, t.account, t.amount, t.recipient)
    }

  val m =  temp map (x => x._1)
  }

}
