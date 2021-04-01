package appshop

import appshop.modules.sys.controller.*
import appshop.modules.sys.core.*
import appshop.util.*
import com.usthe.sureness.*
import com.usthe.sureness.provider.ducument.*
import com.usthe.sureness.util.*

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import org.ktorm.jackson.*
import org.reflections.*
import org.slf4j.event.*
import kotlin.reflect.full.*

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)


@EngineAPI
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            this.registerModule(KtormModule())
        }
    }
    SurenessConfig()

    val reflections = Reflections("appshop.modules.sys.controller")
    val subTypesOf = reflections.getSubTypesOf(KtorRouter::class.java)
    if (subTypesOf.isNotEmpty()) {
        subTypesOf.forEach { it ->
            it.kotlin.functions.find {
                it.name == "route"
            }?.call(it.newInstance(), routing { })
        }
    }
}

