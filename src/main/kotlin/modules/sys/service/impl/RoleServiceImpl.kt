package appshop.modules.sys.service.impl

import appshop.modules.sys.dao.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.*
import appshop.util.*
import com.usthe.sureness.processor.exception.*
import org.kodein.di.*
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: RoleServiceImpl
 * DATE: 2021/3/29
 * TIME: 19:01
 * JDK 1.8
 */
class RoleServiceImpl : RoleService {
    private val authRoleDao by totalDI.instance<AuthRoleDao>()
    private val authResourceDao by totalDI.instance<AuthResourceDao>()
    private val AuthRoleResourceBindDao by totalDI.instance<AuthRoleResourceBindDao>()
    override fun isRoleExist(authRoledto: AuthRoleDTO): Boolean {
        val authRole = AuthRole {
            this.code = authRoledto.code
            this.name = authRoledto.name
        }
        return authRoleDao.exits(authRole)
    }

    override fun addRole(authRoledto: AuthRoleDTO): Boolean {
        return if (isRoleExist(authRoledto)) {
            false
        } else {
            val authRole = AuthRole {
                this.name = authRoledto.name
                this.code = authRoledto.code
                this.status = authRoledto.status
                this.description = authRoledto.description
                this.gmtUpdate = authRoledto.gmtUpdate
            }
            return authRoleDao.save(authRole)
        }
    }

    override fun updateRole(authRoledto: AuthRoleDTO): Boolean {
        return if (authRoledto.id != null) {
            if (authRoleDao.existsById(authRoledto.id!!)) {
                val authRole = AuthRole {
                    this.id = authRoledto.id!!
                    this.name = authRoledto.name
                    this.code = authRoledto.code
                    this.status = authRoledto.status
                    this.description = authRoledto.description
                    this.gmtUpdate = authRoledto.gmtUpdate
                }
                return authRoleDao.flush(authRole)
            } else {
                false
            }
        } else {
            false
        }

    }

    override fun deleteRole(roleId: Long): Boolean {
        return if (authRoleDao.existsById(roleId)) {
            return authRoleDao.deleteById(roleId)
        } else {
            false
        }
    }

    override fun getAllRole(): List<AuthRole> {
        return authRoleDao.findAll()
    }

    override fun getPageRole(currentPage: Int, pageSize: Int): List<AuthRole> {
        return authRoleDao.findAll(currentPage, pageSize)
    }

    override fun getPageResourceOwnRole(roleId: Long, currentPage: Int, pageSize: Int): List<AuthResource> {
        return AuthRoleResourceBindDao.findRoleBindResourceByRoleid(roleId).map {
            authResourceDao.findResourceByid(it.resourceid)
        }.drop(pageSize * (currentPage - 1)).take(pageSize).sortedBy { it.id }.toList()
    }

    override fun getPageResourceNotOwnRole(roleId: Long, currentPage: Int, pageSize: Int): List<AuthResource> {
        return authResourceDao.findNotOwnResourceidByid(
            AuthRoleResourceBindDao.findRoleBindResourceByRoleid(roleId).map { it.resourceid })
            .drop(pageSize * (currentPage - 1)).take(pageSize).sortedBy { it.id }.toList()

    }

    override fun authorityRoleResource(roleId: Long, resourceId: Long) {
        // Determine whether this resource and role exist
        if (!authRoleDao.existsById(roleId) || !authResourceDao.existsById(resourceId)) {
            throw BaseSurenessException("roleId or resourceId not exist")
        }
        // insert it in database, if existed the unique index will work
        AuthRoleResourceBindDao.saveAndFlush(AuthRoleResourceBind {
            this.resourceid = resourceId
            this.roleid = roleId
            this.gmtUpdate = LocalDateTime.now()
        })
        // refresh resource path data tree
        Config.pathRoleMatcher().rebuildTree()
    }

    override fun deleteAuthorityRoleResource(roleId: Long, resourceId: Long) {
        AuthRoleResourceBindDao.deleteRoleResourceBind(roleId, resourceId)
        // refresh resource path data tree
        Config.pathRoleMatcher().rebuildTree()
    }
}