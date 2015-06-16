package utils

import java.sql.Date
import java.time.{LocalDate, ZoneId}

import dao.TransactionDao
import model.Transaction
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * Created by Richard on 16/06/2015.
 */
class FileParserSpec extends Specification {

  val sampleDate = new Date(LocalDate.of(2015, 5, 16).atStartOfDay(ZoneId.systemDefault()).toInstant.toEpochMilli)

  "File parser spec" should {

    "parse sample line" in new TestScope {
      val result = classUnderTest.parseLine( """16/05/2015;CASH WDL;CASH WITHDRAWAL AT LLOYDS BANK PLC ATM BLUEWTER RETAIL, BLUEWATER CEN,20.00 GBP , ON 16-05;-£20.00;+£150.85;""")
      result should be_==(Transaction.build(sampleDate, "CASH WDL", "joint", BigDecimal(-20.00), "CASH WITHDRAWAL AT LLOYDS BANK PLC ATM BLUEWTER RETAIL, BLUEWATER CEN,20.00 GBP , ON 16-05"))
    }

    "not fail with wrong line" in new TestScope {
      classUnderTest.parseLine( """Date;Type;Merchant/Description;Debit/Credit;Balance;""") should throwA[Exception]
    }

    "not fail with blank line" in new TestScope {
      classUnderTest.parseLine("") should throwA[Exception]
    }

    "parse date" in new TestScope {
      classUnderTest.parseDate("16/05/2015") should be_==(sampleDate)
    }
  }


  class TestScope extends Scope with Mockito {
    val daoMock = mock[TransactionDao]
    val classUnderTest = new FileParser(daoMock)
  }

}
