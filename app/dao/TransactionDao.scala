package dao

import play.api.db._

/**
 * Created by Richard on 17/05/2015.
 */
class TransactionDao {
  val ds = DB.getDataSource()

  def storeTransaction()

}
