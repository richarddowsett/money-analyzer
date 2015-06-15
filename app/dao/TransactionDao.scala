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
  val db = Database.forDataSource(ds)
  val transactions = TableQuery[TransactionTable]

  def storeTransaction(txn: Transaction) = {
    db.withSession {
      implicit session =>
        transactions +=(txn.id.get, txn.account.get, txn.amount.get, txn.recipient.get)
    }
  }

  def loadAll(): List[Transaction] = {
    db.withSession { implicit session =>
      val temp = transactions.take(10).list

      temp map (x => {
        Transaction.build(x._1, x._2, x._3, x._4)
      })
    }
  }

}
