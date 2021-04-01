package appshop.modules.sys.dao

import appshop.modules.sys.dao.tablebind.*
import appshop.modules.sys.po.*
import appshop.util.*
import com.sun.org.apache.bcel.internal.generic.*
import com.sun.org.apache.xpath.internal.operations.*
import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.mysql.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRoleDao
 * DATE: 2021/3/26
 * TIME: 20:27
 * JDK 1.8
 */
class AuthRoleDao {
    private val Database.auth_role get() = this.sequenceOf(AuthRoles)
    fun findAuthRoleById(id: Long): AuthRole? {
        return db.auth_role.find { it.id eq id }
    }

    fun exits(authRole: AuthRole): Boolean {
        return db.auth_role.filter {
            (it.name eq authRole.name) and (it.code eq authRole.code)
        }.toList().isNotEmpty()
    }


    fun save(authRole: AuthRole): Boolean {
        return db.auth_role.add(authRole) > 0
    }

    fun flush(authRole: AuthRole): Boolean {
        val role = db.auth_role.find { it.id eq authRole.id } ?: return false
        role.code = authRole.code
        role.name = authRole.name
        role.status = authRole.status
        role.description = authRole.description
        role.gmtUpdate = authRole.gmtUpdate
        return role.flushChanges()>0
    }


    fun existsById(id: Long): Boolean {
        return db.auth_role.filter { it.id eq id }.toList().isNotEmpty()
    }

    fun deleteById(roleId: Long): Boolean {
        return db.auth_role.find { it.id eq roleId }!!.delete() > 0
    }

    fun findAll(): List<AuthRole> {
        return db.auth_role.toList()
    }

    fun findAll(currentPage: Int, pageSize: Int): List<AuthRole> {
        return db.auth_role.drop(pageSize * (currentPage - 1)).take(pageSize).toList()
    }
}