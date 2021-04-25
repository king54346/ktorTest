package com.jyf

import appshop.modules.sys.dao.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.impl.*
import appshop.util.*
import appshop.util.ValidationUtils.success
import org.kodein.di.instance
import org.redisson.api.RedissonClient
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() {
        val findAuthUserByUsername = AuthUserDao().findAuthUserByUsername("admin")
        println(findAuthUserByUsername)
        val save = AuthUserDao().save(authUser = AuthUser {
            username = "jinyf"
            password = "haha"
            status = 1
        })
        println(save)
    }

    @Test
    fun testRoot2() {
        val registerAccount = AccountServiceImpl().registerAccount(AccountDTO("admin2", "123"))
        println(registerAccount)
        val authorityUserRole = AccountServiceImpl().authorityUserRole("admin", 103)
        println(authorityUserRole)
    }

    @Test
    fun testRoot3() {
        val findAuthUserRoleByRoleAndUser = AuthUserRoleBindDao().findAuthUserRoleByRoleAndUser(111, 103)
        println(findAuthUserRoleByRoleAndUser)
    }

    @Test
    fun testRoot4() {
        val authenticateAccount = AccountServiceImpl().authenticateAccount(AccountDTO("admin2", "1231"))
        println(authenticateAccount)
    }
    @Test
    fun testRoot5(){
        val loadAccountRoles = AccountServiceImpl().loadAccountRoles("admin1")
        println(loadAccountRoles)
    }
    @Test
    fun testRoot6(){
        val disableResourcePathData = AuthResourceDao().getDisableResourcePathData()
        println(disableResourcePathData)
    }

    /**
     * select auth_resource.id as auth_resource_id,
     * auth_resource.name as auth_resource_name,
     * auth_resource.code as auth_resource_code,
     * auth_resource.description as auth_resource_description,
     * auth_resource.`METHOD` as auth_resource_method,
     * auth_resource.type as auth_resource_type,
     * auth_resource.uri as auth_resource_uri,
     * auth_resource.status as auth_resource_status,
     * auth_resource.gmt_create as auth_resource_gmt_create,
     * auth_resource.gmt_update as auth_resource_gmt_update
     * from auth_resource
     */
    @Test
    fun testRoot7(){
//        val orderBy = AuthResourceDao()
//            .findRoleOwnResource()
//        println(orderBy)

        println(AuthRoleResourceBindDao().findRoleBindResourceByRoleidOnlyResouceid(102))
        println(AuthResourceDao().AuthResourceByid(100))
        println(ResourceServiceImpl().getAllEnableResourcePath())
        println(ResourceServiceImpl().getAllDisableResourcePath())
    }
    private val redissonClient by totalDI.instance<RedissonClient>()

    @Test
    fun testRoot8(){
        println(redissonClient)
        println("hello")
    }
}
