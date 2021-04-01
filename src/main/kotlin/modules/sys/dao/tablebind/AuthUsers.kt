package appshop.modules.sys.dao.tablebind

import appshop.modules.sys.po.*
import appshop.modules.sys.po.AuthUser
import org.ktorm.schema.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: Account
 * DATE: 2021/3/26
 * TIME: 16:07
 * JDK 1.8
 */
object AuthUsers : Table<AuthUser>("auth_user") {
    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val avater=varchar("avatar").bindTo { it.avatar}
    val salt=varchar("salt").bindTo { it.salt}
    val phone=varchar("phone").bindTo { it.phone}
    val email=varchar("email").bindTo { it.email}
    val sex=int("sex").bindTo { it.sex}
    val where=int("create_where").bindTo { it.where}
    val staus=int("status").bindTo { it.status}
    val gmtCreate=datetime("gmt_Create").bindTo { it.gmtCreate}
    val gmtUpdate=datetime("gmt_Update").bindTo { it.gmtUpdate }
}