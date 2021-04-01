package appshop.modules.sys.dao.tablebind

import appshop.modules.sys.dao.tablebind.AuthUserRoleBinds.bindTo
import appshop.modules.sys.dao.tablebind.AuthUserRoleBinds.primaryKey
import appshop.modules.sys.po.*
import org.ktorm.schema.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRoleResourceBinds
 * DATE: 2021/3/28
 * TIME: 17:16
 * JDK 1.8
 */
object AuthRoleResourceBinds: Table<AuthRoleResourceBind>("auth_role_resource_bind") {
    val id = long("id").primaryKey().bindTo { it.id }
    val resourceid = long("resource_id").bindTo { it.resourceid }
    val roleid = long("role_id").bindTo { it.roleid }
    val gmtCreate = datetime("gmt_Create").bindTo { it.gmtCreate }
    val gmtUpdate = datetime("gmt_Update").bindTo { it.gmtUpdate }
}