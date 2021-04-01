package appshop.modules.sys.dao

import appshop.modules.sys.dao.tablebind.*
import appshop.modules.sys.po.*
import appshop.util.*
import com.sun.org.apache.bcel.internal.generic.*
import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.mysql.*
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthResourceBindDao
 * DATE: 2021/3/28
 * TIME: 17:19
 * JDK 1.8
 */
class AuthRoleResourceBindDao {
    private val Database.auth_role_resource_bind get() = this.sequenceOf(AuthRoleResourceBinds)

    fun findRoleBindResourceByRoleid(roleId: Long): MutableList<AuthRoleResourceBind> {
        return db.auth_role_resource_bind.filterTo(mutableListOf()) { it.roleid eq roleId }
    }

    fun findRoleBindResourceByRoleidOnlyResouceid(roleId: Long): List<Long> {
        return findRoleBindResourceByRoleid(roleId).map {
            it.resourceid
        }.toSet().sorted()
    }

    fun deleteRoleResourceBind(roleId: Long, resourceId: Long): Boolean {
        findAuthRoleResourceRoleByRoleAndUser(roleId, resourceId)?.delete().let {
            if (it != null) {
                return it > 0
            }
            return false
        }
    }

    fun findAuthRoleResourceRoleByRoleAndUser(roleId: Long, resourceId: Long): AuthRoleResourceBind? {
        return db.auth_role_resource_bind
            .find { (it.roleid eq roleId) and (it.resourceid eq resourceId) }
    }

    fun findRoleidByresourceid(resourceId: Long): List<Long> {
        return db.auth_role_resource_bind.filter {
            it.resourceid eq resourceId
        }.map {
            it.roleid
        }.toList()


    }

    fun saveAndFlush(authRoleResourceBind: AuthRoleResourceBind): Boolean {
        val insertOrUpdate = db.insertOrUpdate(AuthRoleResourceBinds) {
            set(it.resourceid, authRoleResourceBind.resourceid)
            set(it.roleid, authRoleResourceBind.roleid)
            onDuplicateKey {
                set(it.resourceid, authRoleResourceBind.resourceid)
                set(it.roleid, authRoleResourceBind.roleid)
                set(it.gmtUpdate, authRoleResourceBind.gmtUpdate)
            }
        }
        return insertOrUpdate > 0
    }
}

