package appshop.modules.sys.po

import appshop.modules.sys.dao.tablebind.*
import org.ktorm.entity.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthResource
 * DATE: 2021/3/28
 * TIME: 14:28
 * JDK 1.8
 */
interface AuthResource: Entity<AuthResource> {
    companion object : Entity.Factory<AuthResource>()
    var id: Long
    var name:String
    var code: String
    var uri:String
    var type:String
    var method:String
    var status:Int
    var description:String
    var gmtCreate: LocalDateTime
    var gmtUpdate: LocalDateTime
}