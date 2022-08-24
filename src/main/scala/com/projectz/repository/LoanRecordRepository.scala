package com.projectz.repository

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.domain.response.LoanResponse

import java.util.Date

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
   * @param lenderId Id của chủ nợ
   * @param keyword  từ khóa  liên quan đến tên của con nợ chủ nợ muốn tìm
   * @return danh sách các ghi nợ của các con nợ
   */
  def getBorrower(lenderId: String, keyword: String): Seq[LoanResponse]

  /**
   * Xuất danh sách từ nơi lưu chử của con nợ
   *
   * @param lenderId   Id của chủ nợ
   * @param borrowerId Id của con nợ
   * @return Danh sách ghi nợ của con
   */
  def getRecordOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse]
}

class LoadRecordStore extends LoanRecordRepository {
  override def save(loanRecord: AddLoanRecordRequest): LoanRecord = ???

  override def getBorrower(lenderId: String, keyword: String): Seq[LoanResponse] = ???

  override def getRecordOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse] = ???
}

class MockLoanRecordRepository extends LoanRecordRepository {

  override def save(loanRecord: AddLoanRecordRequest): LoanRecord = {
    LoanRecord(id="0002",
      lenderId = loanRecord.lenderId,
      borrowerId = loanRecord.borrowerId,
      loanAmount = loanRecord.loanAmount,
      loanReason = loanRecord.loanReason,
      createdDate = new Date())
  }

  override def getBorrower(lenderId: String, keyword: String): Seq[LoanResponse] = {
    Seq(
      LoanResponse(
        loanAmount = 100000,
        borrower = "Vuong",
        lender = "VUOng",
        loanReason = "muon thu",
        createdDate = new Date()
      ),
      LoanResponse(
        loanAmount = 200000,
        borrower = "Vuong",
        lender = "VUOng",
        loanReason = " CUNG  LA muon thu",
        createdDate = new Date()
      )
    )
  }

  override def getRecordOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse] = {
    Seq(
      LoanResponse(
        loanAmount = 100000,
        borrower = "Vuong",
        lender = "VUOng",
        loanReason = "muon thu",
        createdDate = new Date()
      )
    )
  }
}
