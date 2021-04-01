package appshop.modules.sys.po

import org.ktorm.entity.*
import java.math.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthUser
 * DATE: 2021/3/26
 * TIME: 16:10
 * JDK 1.8
 */
interface AuthUser : Entity<AuthUser> {
    companion object : Entity.Factory<AuthUser>()
    var id: Long
    var username:String
    var password: String
    var salt:String
    var avatar:String
    var phone:String
    var email:String
    var sex:Int
    var where:Int
    var status:Int
    var gmtCreate: LocalDateTime
    var gmtUpdate: LocalDateTime
}