package appshop.modules.sys.po

import org.ktorm.entity.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthUserRoleBind
 * DATE: 2021/3/26
 * TIME: 20:40
 * JDK 1.8
 */
interface AuthUserRoleBind: Entity<AuthUserRoleBind> {
    companion object : Entity.Factory<AuthUserRoleBind>()
    var id: Long
    var userid:Long
    var roleid: Long
    var gmtCreate: LocalDateTime
    var gmtUpdate: LocalDateTime
}