#@namespace scala com.projectz.service
include "UserCacheDT.thrift"

service TUserCacheService {
    string ping()
    void addUser(1: required UserCacheDT.TUserInfo userInfo)
    UserCacheDT.TUserInfo getUser(1:required UserCacheDT.TUserID userId)
}
