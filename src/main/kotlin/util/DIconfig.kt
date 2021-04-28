package appshop.util

import appshop.modules.sys.dao.*
import appshop.modules.sys.service.*
import appshop.modules.sys.service.impl.*
import com.mysql.cj.jdbc.*
import com.zaxxer.hikari.*
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import org.kodein.di.*
import org.ktorm.database.*

import kotlin.reflect.jvm.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: DI
 * DATE: 2021/3/7
 * TIME: 23:02
 * JDK 1.8
 */
val totalDI= DI.lazy{
    import(connDI,true)
    import(daoDI,true)
    import(serviceDI,true)
}

val connDI = DI.Module("connDI"){
    bind<HikariDataSource>() with singleton {
        HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/ktor?serverTimezone=Hongkong"
            driverClassName = Driver::class.jvmName
            password = "jinyuefeng123"
            username = "root"
            maximumPoolSize=30
            minimumIdle=10
        })
    }
    bind<Database>() with singleton { Database.connect(dataSource = instance<HikariDataSource>()) }
//    bind<RedissonClient>() with singleton {
//        val config = Config().apply {
//            useSingleServer().address="redis://127.0.0.1:6379"
//        }
//        Redisson.create(config)
//    }

    bind<RedisAsyncCommands<String, String>>() with singleton {
        RedisClient.create("redis://127.0.0.1:6379").connect().async()
    }
}

val daoDI = DI.Module("daoDI") {
    bind<AuthUserDao>() with singleton { AuthUserDao() }
    bind<AuthRoleDao>() with singleton { AuthRoleDao() }
    bind<AuthUserRoleBindDao>() with singleton { AuthUserRoleBindDao() }
    bind<AuthResourceDao>() with singleton { AuthResourceDao() }
    bind<AuthRoleResourceBindDao>() with singleton { AuthRoleResourceBindDao() }

}
val serviceDI = DI.Module("serviceDI") {
    bind<AccountService>() with singleton { AccountServiceImpl() }
    bind<ResourceService>() with singleton { ResourceServiceImpl() }
    bind<RoleService>() with singleton { RoleServiceImpl() }
}
val db by totalDI.newInstance { instance<Database>() }