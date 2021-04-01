package appshop.modules.sys.dao.tablebind

import appshop.modules.sys.dao.tablebind.AuthRoles.bindTo
import appshop.modules.sys.dao.tablebind.AuthRoles.primaryKey
import appshop.modules.sys.po.*
import org.ktorm.schema.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthUserRoleBinds
 * DATE: 2021/3/26
 * TIME: 20:41
 * JDK 1.8
 */
object AuthUserRoleBinds: Table<AuthUserRoleBind>("auth_user_role_bind") {
    val id = long("id").primaryKey().bindTo { it.id }
    val userid = long("user_id").bindTo { it.userid }
    val roleid = long("role_id").bindTo { it.roleid }
    val gmtCreate = datetime("gmt_Create").bindTo { it.gmtCreate }
    val gmtUpdate = datetime("gmt_Update").bindTo { it.gmtUpdate }
}