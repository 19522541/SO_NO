package com.projectz.repository

import com.projectz.domain.{Loan, LoanRecord}
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.domain.response.LoanResponse
import com.twitter.util.Config.intoList

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}
import java.util.{Date, UUID}
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
  def getBorrowerLoans(lender: String, userIds: Seq[String]): Seq[Loan]

  /**
   * Xuất danh sách từ nơi lưu chử của con nợ
   *
   * @param lender   tên của chủ nợ
   * @param borrower tên của con nợ
   * @return Danh sách ghi nợ của con
   */
  def getLoanDetails(lender: String, borrower: String): Seq[LoanRecord]

}

class LoanRepositoryImpl @Inject()(client: JDBCClient) extends LoanRecordRepository {

  override def save(loanRecord: AddLoanRecordRequest): LoanRecord = {

    val connection = client.getConnection()
    val query = "insert into loan_record(id,lender,borrower,amount,reason,created_date) values (?,?,?,?,?,CURDATE())"
    val preparedStatement = connection.prepareStatement(query)
    val id: String = UUID.randomUUID().toString
    preparedStatement.setString(1, id)
    preparedStatement.setString(2, loanRecord.lender)
    preparedStatement.setString(3, loanRecord.borrower)
    preparedStatement.setInt(4, loanRecord.loanAmount.toInt)
    preparedStatement.setString(5, loanRecord.loanReason)
    preparedStatement.execute()
    connection.close()
    LoanRecord(id = id, loanAmount = loanRecord.loanAmount,
      lender = loanRecord.lender, borrower = loanRecord.borrower,
      loanReason = loanRecord.loanReason, createdDate = new Date()
    )
  }

  override def getBorrowerLoans(lender: String, userIds: Seq[String]): Seq[Loan] = {
    var borrower = Seq[Loan]()
    val connection = client.getConnection()
    userIds.foreach(id => {
      val query = s"select borrower,SUM(amount) from loan_record WHERE lender =?and borrower=? GROUP BY  borrower"
      //val query = s"select * from loan_record WHERE lender=? and borrower = ?"
      val preparedStatement = connection.prepareStatement(query)
      preparedStatement.setString(1, lender)
      preparedStatement.setString(2, id)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        borrower = borrower :+ Loan(borrower = resultSet.getString("borrower"), amount = resultSet.getInt("SUM(amount)"))
      }
    })
    connection.close()
    borrower
  }

  override def getLoanDetails(lender: String, borrower: String): Seq[LoanRecord] = {
    val connection = client.getConnection()
    val query = s"select * from loan_record WHERE lender=? and borrower=?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, lender)
    preparedStatement.setString(2, borrower)
    val resultSet: ResultSet = preparedStatement.executeQuery()
    convertResultSetToLoanRecordSeqAndDisconnDB(resultSet, connection)
  }

  def convertResultSetToLoanRecordSeqAndDisconnDB(resultSet: ResultSet, connection: Connection = null): Seq[LoanRecord] = {
    var loanRecords = Seq[LoanRecord]()
    while (resultSet.next()) {
      loanRecords = loanRecords :+ LoanRecord(
        id = resultSet.getString("id"),
        loanAmount = resultSet.getLong("amount"),
        borrower = resultSet.getString("borrower"),
        lender = resultSet.getString("lender"),
        loanReason = resultSet.getString("reason"),
        createdDate = resultSet.getDate("created_date")
      )
    }
    if (connection != null) {
      connection.close()
    }

    loanRecords
  }
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

