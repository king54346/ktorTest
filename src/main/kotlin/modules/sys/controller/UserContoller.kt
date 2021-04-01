package appshop.modules.sys.controller

import appshop.modules.sys.core.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.service.*
import appshop.util.*
import appshop.util.ValidationUtils.success
import com.usthe.sureness.provider.ducument.*
import com.usthe.sureness.subject.*
import com.usthe.sureness.util.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.*
import java.lang.Exception

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: UserContoller
 * DATE: 2021/3/31
 * TIME: 11:50
 * JDK 1.8
 */
class UserContoller : KtorRouter {
    private val accountService by totalDI.instance<AccountService>()
    override fun Routing.route() {
        route("/user") {
            get("/role") {
                val subject = SurenessContextHolder.getBindSubject()
                if (subject == null || subject.principal == null) {
                    call.respond(R().error("Forbidden"))
                    return@get finish()
                }
                val appId = subject.principal as String
                val roles: List<String> = accountService.loadAccountRoles(appId)
                call.respond(R().ok("roles" to roles))
                return@get finish()
            }
            post("/authority/role/{appId}/{roleId}") {
                try {
                    val appId = call.parameters["appId"]
                    val roleId = call.parameters["roleId"]?.toLong()
                    if (appId == null || roleId == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@post finish()
                    }
                    //获得当前操作角色的subjetsum
                    val subject = SurenessContextHolder.getBindSubject()
                    if (subject == null || subject.principal == null) {
                        call.respond(R().error("Forbidden"))
                        return@post finish()
                    }
                    val principal = subject.principal as String
                    if (principal != appId) {
                        call.respond(R().error("Forbidden"))
                        return@post finish()
                    }
                    val flag = accountService.authorityUserRole(appId, roleId)
                    if (flag) {
                        call.respond(R().ok())
                        return@post finish()
                    } else {
                        call.respond(R().error("Conflict"))
                        return@post finish()

                    }
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@post finish()
                }
            }
            delete("/authority/role/{appId}/{roleId}") {
                try {
                    val appId = call.parameters["appId"]
                    val roleId = call.parameters["roleId"]?.toLong()
                    if (appId == null || roleId == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@delete finish()
                    } else {
                        val subject = SurenessContextHolder.getBindSubject()
                        if (subject == null || subject.principal == null) {
                            call.respond(R().error("Forbidden"))
                            return@delete finish()
                        }

                        if (subject.principal as String != appId) {
                            call.respond(R().error("Forbidden"))
                            return@delete finish()
                        }
                        if (accountService.deleteAuthorityUserRole(appId, roleId)) {
                            call.respond(R().ok())
                            return@delete finish()
                        } else {
                            call.respond(R().error("Conflict"))
                            return@delete finish()
                        }
                    }
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@delete finish()
                }
            }

        }
    }
}