package com.projectz.repository

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.domain.response.LoanResponse

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

import java.util.Date
import javax.inject.Inject

trait LoanRecordRepository {
  /**
   * THực hiện thêm ghi nợ vào nơi lưu chử
   *
   * @param loanRecord thông tin của ghi nợ mà chủ nợ muốn tạo
   * @return Chi tiết ghi nợ mới tọa
   */
  def save(loanRecord: AddLoanRecordRequest): LoanRecord

  /**
   * Xuất ra danh sách các con nợ
   *
   * @param lender  chủ nợ
   * @param keyword từ khóa  liên quan đến tên của con nợ chủ nợ muốn tìm
   * @return danh sách các ghi nợ của các con nợ
   */
  def getBorrower(lender: String, keyword: String): Seq[LoanResponse]

  /**
   * Xuất danh sách từ nơi lưu chử của con nợ
   *
   * @param lender   tên của chủ nợ
   * @param borrower tên của con nợ
   * @return Danh sách ghi nợ của con
   */
  def getRecordOfBorrower(lender: String, borrower: String): Seq[LoanResponse]

  def isConnected(): Boolean
}

class LoanRepositoryImpl @Inject()(client: JDBCClient) extends LoanRecordRepository {

  override def save(loanRecord: AddLoanRecordRequest): LoanRecord = {

    val connection = client.getConnection()
    val query = "insert into loan_record(id,lender,borrower,amount,reason,created_date) values (UUID(),?,?,?,?,CURDATE())"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1,loanRecord.lender )
    preparedStatement.setString(2,loanRecord.borrower)
    preparedStatement.setInt(3,loanRecord.loanAmount.toInt)
    preparedStatement.setString(4,loanRecord.loanReason)
    println(preparedStatement.toString)
    preparedStatement.execute()

    connection.close()
   LoanRecord(id="HIDDEN",loanAmount = loanRecord.loanAmount,
     lender=loanRecord.lender,borrower=loanRecord.borrower,
     loanReason = loanRecord.loanReason, createdDate = new Date()
   )
  }
  override def getBorrower(lender: String, keyword: String): Seq[LoanResponse] = {
    print(s"LoanRecordResponsitory::getBorrow::keyword ${keyword}")
    val newKeyword= "%"+keyword+"%"
    var loanRecords = Seq[LoanResponse]()
    val connection = client.getConnection()
    val query = s"select * from loan_record WHERE lender=? and borrower like ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, lender)
    preparedStatement.setString(2,newKeyword)
    val sqlQueryResult: ResultSet = preparedStatement.executeQuery()
    while (sqlQueryResult.next()) {
      loanRecords= loanRecords:+ LoanResponse(
        loanAmount = sqlQueryResult.getLong("amount"),
        borrower = sqlQueryResult.getString("borrower"),
        lender = sqlQueryResult.getString("lender"),
        loanReason = sqlQueryResult.getString("reason"),
        createdDate = sqlQueryResult.getDate("created_date")
      )
    }
    connection.close()
    loanRecords

  }
  override def getRecordOfBorrower(lender: String, borrower: String): Seq[LoanResponse] = {
    var loanRecords = Seq[LoanResponse]()
    val connection = client.getConnection()
    val query = s"select * from loan_record WHERE lender=? and borrower=?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, lender)
    preparedStatement.setString(2,borrower)
    val sqlQueryResult: ResultSet = preparedStatement.executeQuery()
    while (sqlQueryResult.next()) {
     loanRecords= loanRecords:+ LoanResponse(
        loanAmount = sqlQueryResult.getLong("amount"),
        borrower = sqlQueryResult.getString("borrower"),
        lender = sqlQueryResult.getString("lender"),
        loanReason = sqlQueryResult.getString("reason"),
        createdDate = sqlQueryResult.getDate("created_date")
      )
    }
    connection.close()
    loanRecords
  }

  override def isConnected(): Boolean = ???
}

//class MockLoanRecordRepository extends LoanRecordRepository {
//
//  override def save(loanRecord: AddLoanRecordRequest): LoanRecord = {
//    LoanRecord(id = "0002",
//      lenderId = loanRecord.lenderId,
//      borrowerId = loanRecord.borrowerId,
//      loanAmount = loanRecord.loanAmount,
//      loanReason = loanRecord.loanReason,
//      createdDate = new Date())
//  }
//
//  override def getBorrower(lenderId: String, keyword: String): Seq[LoanResponse] = {
//    Seq(
//      LoanResponse(
//        borrowerId = "USER001",
//        loanAmount = 100000,
//        borrower = "Vuong",
//        lender = "VUOng",
//        loanReason = "muon thu",
//        createdDate = new Date()
//      ),
//      LoanResponse(
//        borrowerId = "USER001",
//        loanAmount = 200000,
//        borrower = "Vuong",
//        lender = "VUOng",
//        loanReason = " CUNG  LA muon thu",
//        createdDate = new Date()
//      )
//    )
//  }
//
//  override def getRecordOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse] = {
//    Seq(
//      LoanResponse(
//        borrowerId = "USER001",
//        loanAmount = 100000,
//        borrower = "Vuong",
//        lender = "VUOng",
//        loanReason = "muon thu",
//        createdDate = new Date()
//      )
//    )
//  }
//
//  override def isConnected(): Boolean = {
//    if(JDBCConnectObject.setConnect().isDefined) true
//    else false}
//}


object JDBCConnectObject {
  val jdbcConn: Option[Connection] = setConnect()

  private def setConnect(): Option[Connection] = {
    var connection: Connection = null
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/loanrecord"
    val user = "root"
    val password = "di@2020!"
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, user, password)
      Some(connection)
    } catch {
      case e => {
        println(e)
        None
      }
    }
  }
}