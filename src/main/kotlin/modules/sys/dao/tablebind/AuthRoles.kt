package appshop.modules.sys.dao.tablebind

import appshop.modules.sys.dao.tablebind.AuthUsers.bindTo
import appshop.modules.sys.dao.tablebind.AuthUsers.primaryKey
import appshop.modules.sys.po.*
import kotlinx.html.*
import org.ktorm.schema.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRoles
 * DATE: 2021/3/26
 * TIME: 20:14
 * JDK 1.8
 */
object AuthRoles : Table<AuthRole>("auth_role") {
    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val code = varchar("code").bindTo { it.code }
    val status = int("status").bindTo { it.status }
    val description = varchar("description").bindTo { it.description }
    val gmtCreate = datetime("gmt_Create").bindTo { it.gmtCreate }
    val gmtUpdate = datetime("gmt_Update").bindTo { it.gmtUpdate }
}