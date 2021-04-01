package appshop.modules.sys.po

import org.ktorm.entity.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRole
 * DATE: 2021/3/26
 * TIME: 20:15
 * JDK 1.8
 */
interface AuthRole : Entity<AuthRole>{
    companion object : Entity.Factory<AuthRole>()
    var id: Long
    var name:String
    var code: String
    var status:Int
    var description:String
    var gmtCreate: LocalDateTime
    var gmtUpdate: LocalDateTime
}