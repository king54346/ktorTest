package appshop.modules.sys.dao

import appshop.modules.sys.dao.tablebind.*
import appshop.modules.sys.po.*
import appshop.util.*
import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.entity.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthResourceDao
 * DATE: 2021/3/28
 * TIME: 14:34
 * JDK 1.8
 */
class AuthResourceDao {
    private val Database.auth_resource get() = this.sequenceOf(AuthResources)


    fun getEnableResource(): List<AuthResource> {
        return db.auth_resource
            .filter { it.status eq 1 }.toList()


    }

    /**
     *Get disabled uri resources eg: /api/v2/host===post
     * @Query("select CONCAT(LOWER(resource.uri),'===', resource.method) " +
    "from AuthResourceDO resource where resource.status = 9 order by resource.id")
     */
    fun getDisableResourcePathData(): Set<String> {
        return db.auth_resource
            .filter { it.status eq 9 }
            .sortedBy { it.id }
            .map {
                it.uri.toLowerCase() + "===" + it.method
            }.toSet()
    }

    fun AuthResourceByid(resourceid: Long): AuthResource? {
        return db.auth_resource.find { it.id eq resourceid }
    }

    fun save(authResource: AuthResource): Boolean {
        return db.auth_resource.add(authResource) > 0
    }

    fun flush(authResource: AuthResource): Boolean {
        val resource = db.auth_resource.find { it.id eq authResource.id } ?: return false
        resource.code = authResource.code
        resource.name = authResource.name
        resource.status = authResource.status
        resource.description = authResource.description
        resource.uri = authResource.uri
        resource.type = authResource.type
        resource.method = authResource.method
        resource.gmtUpdate = authResource.gmtUpdate
        return resource.flushChanges() > 0
    }

    fun exists(authResource: AuthResource): Boolean {

        val find = db.auth_resource.find {
            (it.uri eq authResource.uri) and (it.method eq authResource.method)
        }
        if (find != null) {
            return true
        }
        return false
    }

    fun existsById(resourceid: Long): Boolean {
        val find = db.auth_resource.find {
            it.id eq resourceid
        }
        if (find != null) {
            return true
        }
        return false
    }

    fun deleteById(resourceId: Long): Boolean {
        return db.auth_resource.find { it.id eq resourceId }!!.delete() > 0
    }

    fun findAll(): List<AuthResource> {
        return db.auth_resource.toList()
    }

    fun findAll(currentPage: Int, pageSize: Int): List<AuthResource> {
        return db.auth_resource.drop(pageSize * (currentPage - 1)).take(pageSize).toList()
    }

    fun findResourceByid(id: Long): AuthResource {
        return db.auth_resource.filter {
            it.id eq id
        }.single()
    }

    fun findNotOwnResourceidByid(id: List<Long>): List<AuthResource> {
        return findAll().filterNot {
            it.id in id
        }
    }
}
