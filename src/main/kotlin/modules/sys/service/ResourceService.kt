package appshop.modules.sys.service

import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: ResourceService
 * DATE: 2021/3/28
 * TIME: 14:26
 * JDK 1.8
 */
interface ResourceService {
    /**
     * add uri resource
     * @param authResource resource
     * @return success-true failed-false
     */
    fun addResource(authResource: AuthResourceDTO): Boolean

    /**
     * Determine whether the resource already exists
     * @param authResource resource
     * @return existed-true no-false
     */
    fun isResourceExist(authResource: AuthResourceDTO): Boolean

    /**
     * update uri resource
     * @param authResource resource
     * @return success-true failed-false
     */
    fun updateResource(authResource: AuthResourceDTO): Boolean

    /**
     * delete uri resource
     * @param resourceId resource ID
     * @return success-true no existed-false
     */
    fun deleteResource(resourceId: Long): Boolean

    /**
     * get all resources
     * @return resource list
     */
    fun getAllResource(): List<AuthResourceDTO>

    /**
     * get resource by page
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resource
     */
    fun getPageResource(currentPage: Int, pageSize: Int): List<AuthResourceDTO>

    /**
     * get enabled resource-path-role eg: /api/v2/host===post===[role2,role3,role4]
     * @return resource-path-role
     */
    fun getAllEnableResourcePath(): Set<String>

    /**
     * get disable resource-path-role eg: /api/v2/host===post===[role2,role3,role4]
     * @return resource-path-role
     */
    fun getAllDisableResourcePath(): Set<String>
}