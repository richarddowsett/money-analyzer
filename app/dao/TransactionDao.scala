package dao

import model.{Transaction, TransactionTable}
import play.api.db._

import scala.slick.driver.H2Driver.simple._

/**
 * Created by Richard on 17/05/2015.
 */
class TransactionDao {

  import play.api.Play.current

  val ds = DB.getDataSource()
  val transactions = TableQuery[TransactionTable]
  transactions.ddl.create

  def storeTransaction(txn: Transaction) = {
    transactions +=(txn.id, txn.account, txn.amount, txn.recipient)
  }

  def loadAll(): List[Transaction] = {
    val temp = transactions.take(10).list

    temp map (x => {
      Transaction(x._1, x._2, x._3, x._4)
    })
  }

}
