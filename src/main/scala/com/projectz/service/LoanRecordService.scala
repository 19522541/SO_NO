package com.projectz.service

import com.projectz.domain.{Loan, LoanRecord}
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.domain.response.{GetBorrowerResponse, LoanResponse, UserInfo}
import com.projectz.repository.LoanRecordRepository
import com.twitter.finatra.http.exceptions.NotFoundException
import com.twitter.util.{Await, Future}
import di.labs.domain.thrift.TUserDetail

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
  def getBorrowers(lender: String, keyword: String): Seq[GetBorrowerResponse]

  /**
   * Chủ nợ lấy danh sách ghi nợ mà con nợ đã nợ mình
   *
   * @param lender   Id của chủ nợ
   * @param borrower Id của con nợ
   * @return Trả về danh sách các ghi nợ đã thêm
   */
  @throws[NotFoundException]("Khi chủ nợ không tồn tại")
  @throws[InternalError]("gap bat cu exception nao")
  def getLoanDetails(lender: String, borrower: String): Seq[LoanResponse]

  def getUserInfoById(id:String):UserInfo

}


  class LoanRecordServiceImpl @Inject()(loanRecordRepository: LoanRecordRepository, userService: UserService) extends LoanService {
  override def addRecord(loanRecord: AddLoanRecordRequest): LoanRecord = {
   // if( getUserInfoById(loanRecord.borrower))
    getUserInfoById(loanRecord.borrower)
    getUserInfoById(loanRecord.lender)
    val newRecord: LoanRecord = loanRecordRepository.save(loanRecord)
     newRecord
  }
  override def getBorrowers(lender: String, keyword: String): Seq[GetBorrowerResponse] = {
    getUserInfoById(lender)
    val userIds:Seq[String]= getUserIdByKeyword(keyword)
    val borrowers: Seq[Loan] = loanRecordRepository.getBorrowerLoans(lender, userIds)
      convertArrayOfLoanToArrayOfGetBorrowerResponse(borrowers)
  }

  override def getLoanDetails(lender: String, borrower: String): Seq[LoanResponse] = {
    getUserInfoById(lender)
    val loadDetailOfBorrower: Seq[LoanRecord] = loanRecordRepository.getLoanDetails(lender, borrower)
    convertArrayOfLoanRecordToArrayOfLoanResponse(loadDetailOfBorrower)
  }

    override def getUserInfoById(id: String): UserInfo = {
     val userInfoResponse= Await.result(userService.getUserById(id))
      UserInfo(id = userInfoResponse.id, name =userInfoResponse.username, age = userInfoResponse.age, sex = userInfoResponse.sex, dob = userInfoResponse.dob)
    }
    def getUserIdByKeyword(keyword: String):Seq[String]={
      var userNames:Seq[String] =Seq[String]()
      val userInfoResponse= Await.result(userService.getUserByName(name =keyword ))
        userInfoResponse.foreach(item=>{
          userNames=userNames:+item.id
        })
      userNames
    }
    def convertArrayOfLoanRecordToArrayOfLoanResponse(loanRecords: Seq[LoanRecord]):Seq[LoanResponse]={
      var loanResponses= Seq[LoanResponse]()
      loanRecords.foreach( record =>{
       loanResponses= loanResponses:+LoanResponse(
          loanAmount = record.loanAmount, borrower =  getUserInfoById(record.borrower),
          lender = getUserInfoById(record.lender),
          loanReason = record.loanReason,
          createdDate = record.createdDate
        )
      }
      )
      loanResponses
    }
    def convertArrayOfLoanToArrayOfGetBorrowerResponse(loans: Seq[Loan]):Seq[GetBorrowerResponse]={
      var getBorrowerResponse = Seq[GetBorrowerResponse] ()
      loans.foreach (loan => {
      getBorrowerResponse = getBorrowerResponse :+ GetBorrowerResponse (
      amount = loan.amount,
        userInfo = getUserInfoById(loan.borrower)
      )
    }
      )
      getBorrowerResponse
    }

  }


