package appshop.modules.sys.dao

import appshop.modules.sys.dao.tablebind.*
import appshop.modules.sys.po.*
import kotlinx.html.*
import appshop.util.*
import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.entity.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthUserRoleBindDao
 * DATE: 2021/3/26
 * TIME: 20:39
 * JDK 1.8
 */
class AuthUserRoleBindDao {
    private val Database.auth_user_role_bind get() = this.sequenceOf(AuthUserRoleBinds)

    fun findAuthUserRoleBindUserId(useid: Long): List<AuthUserRoleBind> {
        return db.auth_user_role_bind.filter { it.userid eq useid }.toList()
    }

    fun save(authUserRoleBind: AuthUserRoleBind): Boolean {

        if (findAuthUserRoleByRoleAndUser(authUserRoleBind.userid, authUserRoleBind.roleid) == null) {
            return db.auth_user_role_bind.add(authUserRoleBind) > 0
        }
        return false
    }

    fun deleteRoleResourceBind(userid: Long, roleId: Long): Boolean {
        findAuthUserRoleByRoleAndUser(userid, roleId)?.delete().let {
            if (it != null) {
                return it > 0
            }
            return false
        }
    }

    fun findAuthUserRoleByRoleAndUser(userid: Long, roleId: Long): AuthUserRoleBind? {
        return db.auth_user_role_bind.find {
            (it.roleid eq roleId) and (it.userid eq userid)
        }
    }
}