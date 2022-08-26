package com.projectz.service

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.domain.response.LoanResponse
import com.projectz.repository.LoanRecordRepository
import com.twitter.finatra.http.exceptions.NotFoundException

import javax.inject.Inject

/**
 * Định nghĩa các thao tác thêm xóa sửa của danh sách ghi nợ
 */
trait LoanService {
  /**
   * Chủ nợ thêm ghi nợ mới
   *
   * @param loanRecord Chi tiết nợ
   * @return Trả về thông tin khi nợ mà chủ nợ  mới thực hiện thêm vào
   */
  @throws[NotFoundException]("khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def addRecord(loanRecord: AddLoanRecordRequest): LoanRecord

  /**
   * Chủ nợ lấy ra danh sách con nợ của mình
   *
   * @param lender Id của chủ nợ
   * @param keyword  Từ khóa để tìm kiếm con nợ.Trong chường hợp keyword trống thì sẽ hiện ra tất cả con nợ của chủ nợ
   * @return Danh sách các LoanResponse Object về tổng nợ của con nợ , với ngày tạo là của lần tạo ghi nợ gần nhất
   */
  @throws[NotFoundException]("Khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def getBorrowersOfLender(lender: String, keyword: String): Seq[LoanResponse]

  /**
   * Chủ nợ lấy danh sách ghi nợ mà con nợ đã nợ mình
   *
   * @param lender   Id của chủ nợ
   * @param borrower Id của con nợ
   * @return Trả về danh sách các ghi nợ đã thêm
   */
  @throws[NotFoundException]("Khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def getLoanDetailOfBorrower(lender: String, borrower: String): Seq[LoanResponse]
  def isConnect():Boolean
}


  class LoanRecordServiceImpl @Inject()(
                                       loanRecordRepository: LoanRecordRepository
                                     ) extends LoanService {
  override def addRecord(loanRecord: AddLoanRecordRequest): LoanRecord = {
    val newRecord: LoanRecord = loanRecordRepository.save(loanRecord)
     newRecord
  }
  override def getBorrowersOfLender(lender: String, keyword: String): Seq[LoanResponse] = {

    val borrowers: Seq[LoanResponse] = loanRecordRepository.getBorrower(lender, keyword)
    borrowers
  }

  override def getLoanDetailOfBorrower(lender: String, borrower: String): Seq[LoanResponse] = {
    val loadDetailOfBorrower: Seq[LoanResponse] = loanRecordRepository.getRecordOfBorrower(lender, borrower)
    loadDetailOfBorrower
  }

    override def isConnect(): Boolean = {
      loanRecordRepository.isConnected()
    }
  }


