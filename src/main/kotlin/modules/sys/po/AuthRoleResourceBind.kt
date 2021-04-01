package appshop.modules.sys.po

import org.ktorm.entity.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRoleResourceBind
 * DATE: 2021/3/28
 * TIME: 17:15
 * JDK 1.8
 */
interface AuthRoleResourceBind : Entity<AuthRoleResourceBind> {
    companion object : Entity.Factory<AuthRoleResourceBind>()
    var id: Long
    var roleid:Long
    var resourceid: Long
    var gmtCreate: LocalDateTime
    var gmtUpdate: LocalDateTime
}