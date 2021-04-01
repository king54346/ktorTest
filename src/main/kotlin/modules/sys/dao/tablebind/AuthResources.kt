package appshop.modules.sys.dao.tablebind

import appshop.modules.sys.dao.tablebind.AuthRoles.bindTo
import appshop.modules.sys.dao.tablebind.AuthRoles.primaryKey
import appshop.modules.sys.po.*
import org.ktorm.schema.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthResources
 * DATE: 2021/3/28
 * TIME: 14:31
 * JDK 1.8
 */
object AuthResources : Table<AuthResource>("auth_resource") {
    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val code = varchar("code").bindTo { it.code }
    val description = varchar("description").bindTo { it.description}
    val method = varchar("method").bindTo { it.method}
    val type = varchar("type").bindTo { it.type}
    val uri = varchar("uri").bindTo { it.uri}
    val status = int("status").bindTo { it.status}
    val gmtCreate = datetime("gmt_Create").bindTo { it.gmtCreate }
    val gmtUpdate = datetime("gmt_Update").bindTo { it.gmtUpdate }
}