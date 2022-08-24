package com.projectz.service

import com.projectz.domain.LoanRecord
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
  def addRecord(loanRecord: LoanRecord): LoanRecord

  /**
   * Chủ nợ lấy ra danh sách con nợ của mình
   *
   * @param lenderId Id của chủ nợ
   * @param keyword  Từ khóa để tìm kiếm con nợ.Trong chường hợp keyword trống thì sẽ hiện ra tất cả con nợ của chủ nợ
   * @return Danh sách các LoanResponse Object về tổng nợ của con nợ , với ngày tạo là của lần tạo ghi nợ gần nhất
   */
  @throws[NotFoundException]("Khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def getBorrowersOfLender(lenderId: String, keyword: String = ""): Seq[LoanResponse]

  /**
   * Chủ nợ lấy danh sách ghi nợ mà con nợ đã nợ mình
   *
   * @param lenderId   Id của chủ nợ
   * @param borrowerId Id của con nợ
   * @return Trả về danh sách các ghi nợ đã thêm
   */
  @throws[NotFoundException]("Khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def getLoanDetailOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse]
}

class LoanRecordServiceImpl @Inject()(
                                       loanRecordRepository: LoanRecordRepository
                                     ) extends LoanService {

  override def addRecord(loanRecord: LoanRecord): LoanRecord = {
    val newRecord: LoanRecord = loanRecordRepository.save(loanRecord)
     newRecord
  }


  override def getBorrowersOfLender(lenderId: String, keyword: String): Seq[LoanResponse] = {
    val borrowers: Seq[LoanResponse] = loanRecordRepository.getBorrower(lenderId, keyword)
    borrowers
  }


  override def getLoanDetailOfBorrower(lenderId: String, borrowerId: String): Seq[LoanResponse] = {
    val loadDetailOfBorrower: Seq[LoanResponse] = loanRecordRepository.getRecordOfBorrower(lenderId, borrowerId)
    loadDetailOfBorrower
  }
}


