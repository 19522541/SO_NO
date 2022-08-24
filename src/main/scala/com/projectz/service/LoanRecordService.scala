package com.projectz.service

import com.projectz.domain.request.{GetLoanRecordRequest, UpdateLoanRecordRequest}
import com.projectz.domain.{LoanRecord, User}
import com.projectz.repository.LoanRecordRepository
import com.twitter.util.Future

import java.util.Date
import javax.inject.Inject
import scala.concurrent.duration.Deadline

trait LoanService {
  // Ham nay lấy danh sách các thông tin nghi nợ của chủ nợ
  // Input: Id(String) của chủ nợ ;Output: danh sách(Seq[LoanRecord]) các khoảng nợ được ghi trước đó
  def getRecord(getLoanRecordRequest: GetLoanRecordRequest): Seq[LoanRecord]
  //Chủ nợ ghị n
  //Input: là 1 object(LoanRecord)  lưu thong tin nợ ,Output: object(LoanRecord) là thể hiện chi tiết nợ đã ghi
  def addRecord( loanRecord: LoanRecord): LoanRecord
  // Xóa thông tin ghi nợ
  //Input: id(String) của record nợ cần xóa ;Output: Object(LoanRecord) thông tin nợ đã xóa
  def deleteRecord(id: String): LoanRecord
  /// thay đổi thông tin nợ
  //Input: object(UpdateLoanRecordRequest) đai diện cho các trường cần thay đổi và chứa id của record yêu cầu thay đổi
  //Output: object (LoanRecord)  Record đã được thay đổi
  def updateRecord(updateLoanRecordRequest: UpdateLoanRecordRequest ): LoanRecord
}
// LoanService description: Mô tả các method cần thiết của LoanService
/// Thuận tiện cho việc thay đổi trong trường hợp bảng Record được ghi trên DB khác
class LoanRecordServiceImpl @Inject()(
                                       LoanRecordRepository: LoanRecordRepository[String, LoanRecord]
                                     ) extends LoanService {

  override def deleteRecord(id: String): LoanRecord = {
    LoanRecordRepository.delete(id)
  }

  override def updateRecord(updateLoanRecordRequest: UpdateLoanRecordRequest): LoanRecord = ???

  override def getRecord(getLoanRecordRequest: GetLoanRecordRequest): Seq[LoanRecord] = {
    LoanRecordRepository.show(getLoanRecordRequest.id)

  }

  override def addRecord( loanRecord: LoanRecord): LoanRecord = {
      LoanRecordRepository.add(loanRecord).get
  }
}

