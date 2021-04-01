package appshop.modules.sys.service.impl

import appshop.modules.sys.dao.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.*
import appshop.util.*
import org.kodein.di.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: ResourceServiceImp
 * DATE: 2021/3/29
 * TIME: 0:14
 * JDK 1.8
 */
class ResourceServiceImpl : ResourceService {
    private val AuthResourceDao by totalDI.instance<AuthResourceDao>()
    private val AuthRoleResourceBindDao by totalDI.instance<AuthRoleResourceBindDao>()
    private val AuthRoleDao by totalDI.instance<AuthRoleDao>()

    override fun addResource(authResource: AuthResourceDTO): Boolean {
        return if (isResourceExist(authResource)) {
            false
        } else {
            return AuthResourceDao.save(AuthResource {
                this.name = authResource.name
                this.code = authResource.code
                this.description = authResource.description
                this.gmtCreate = authResource.gmtCreate
                this.gmtUpdate = authResource.gmtUpdate
                this.method = authResource.method
                this.status = authResource.status
                this.type = authResource.type
                this.uri = authResource.uri
            })
        }
    }

    override fun isResourceExist(authResource: AuthResourceDTO): Boolean {
        return AuthResourceDao.exists(AuthResource {
            this.method = authResource.method
            this.uri = authResource.uri
        })
    }

    override fun updateResource(authResource: AuthResourceDTO): Boolean {
        return if (authResource.id != null) {
            if (AuthResourceDao.existsById(authResource.id!!)) {
                return AuthResourceDao.flush(AuthResource {
                    this.id = authResource.id!!
                    this.name = authResource.name
                    this.code = authResource.code
                    this.description = authResource.description
                    this.gmtUpdate = authResource.gmtUpdate
                    this.method = authResource.method
                    this.status = authResource.status
                    this.type = authResource.type
                    this.uri = authResource.uri
                })
            } else {
                false
            }
        } else {
            false
        }
    }


    override fun deleteResource(resourceId: Long): Boolean {
        return if (AuthResourceDao.existsById(resourceId)) {
            return AuthResourceDao.deleteById(resourceId)
        } else {
            false
        }
    }

    override fun getAllResource(): List<AuthResourceDTO> {

        return AuthResourceDao.findAll().map {
            AuthResourceDTO(
                id = it.id,
                name = it.name,
                code = it.code,
                description = it.description,
                gmtCreate = it.gmtCreate,
                gmtUpdate = it.gmtUpdate,
                method = it.method,
                status = it.status,
                type = it.type,
                uri = it.uri

            )

        }

    }

    override fun getPageResource(currentPage: Int, pageSize: Int): List<AuthResourceDTO> {

        return AuthResourceDao.findAll(currentPage, pageSize).map {
            AuthResourceDTO(
                id = it.id,
                name = it.name,
                code = it.code,
                description = it.description,
                gmtCreate = it.gmtCreate,
                gmtUpdate = it.gmtUpdate,
                method = it.method,
                status = it.status,
                type = it.type,
                uri = it.uri

            )
        }
    }

    /**
     *  eg: 1./api/v2/host===post===[role2,role3,role4]
     */
    override fun getAllEnableResourcePath(): Set<String> {
        val enableResourcePathData = AuthResourceDao.getEnableResource()

        return enableResourcePathData.map {
            val listOf = mutableListOf<String>()
            for (roleid in AuthRoleResourceBindDao.findRoleidByresourceid(it.id)) {
                val findAuthRoleById = AuthRoleDao.findAuthRoleById(roleid)
                if (findAuthRoleById != null) {
                    listOf.add(findAuthRoleById.code)
                }
            }
            it.uri.toLowerCase() + "===" + it.method + "===" + listOf.toString()
        }.toSet()

    }

    override fun getAllDisableResourcePath(): Set<String> {
        return AuthResourceDao.getDisableResourcePathData()
    }
}