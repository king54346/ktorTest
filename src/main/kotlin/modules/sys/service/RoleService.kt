package appshop.modules.sys.service

import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: RoleService
 * DATE: 2021/3/29
 * TIME: 18:59
 * JDK 1.8
 */
open interface RoleService {
    /**
     * Determine whether the role already exists
     * @param authRole role
     * @return existed-true no-false
     */
    fun isRoleExist(authRole: AuthRoleDTO): Boolean

    /**
     * add role
     * @param authRole role
     * @return add success-true failed-false
     */
    fun addRole(authRole: AuthRoleDTO): Boolean

    /**
     * update role
     * @param authRole role
     * @return success-true failed-false
     */
    fun updateRole(authRole: AuthRoleDTO): Boolean

    /**
     * delete role
     * @param roleId role ID
     * @return success-true failed-false
     */
    fun deleteRole(roleId: Long): Boolean

    /**
     * get all role list
     * @return role list
     */
    fun getAllRole(): List<AuthRole>

    /**
     * get roles page
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of roles
     */
    fun getPageRole(currentPage: Int, pageSize: Int): List<AuthRole>

    /**
     * get pageable resources which this role owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    fun getPageResourceOwnRole(roleId: Long, currentPage: Int, pageSize: Int): List<AuthResource>

    /**
     * get pageable resources which this role not owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    fun getPageResourceNotOwnRole(roleId: Long, currentPage: Int, pageSize: Int): List<AuthResource>

    /**
     * authority this resource to this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    fun authorityRoleResource(roleId: Long, resourceId: Long)

    /**
     * unAuthority this resource in this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    fun deleteAuthorityRoleResource(roleId: Long, resourceId: Long)
}
