package appshop.modules.sys.controller

import appshop.modules.sys.core.*
import appshop.modules.sys.dao.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.service.*
import appshop.modules.sys.service.impl.*
import appshop.modules.sys.valid.*
import appshop.util.*
import appshop.util.ValidationUtils.success
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jakarta.validation.constraints.*
import org.kodein.di.*
import org.ktorm.logging.NoOpLogger.isDebugEnabled
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: RoleController
 * DATE: 2021/3/29
 * TIME: 18:49
 * JDK 1.8
 */

class RoleController : KtorRouter {
    val roleService by totalDI.instance<RoleService>()
    override fun Routing.route() {
        route("/role") {
            get("/resource/{roleId}/{currentPage}/{pageSize}") {
                try {
                    val roleId = call.parameters["roleId"]?.toLong()
                    val currentPage = call.parameters["currentPage"]?.toInt() ?: 1
                    val pageSize = call.parameters["pageSize"]?.toInt() ?: 10
                    if (roleId == null) {
                        call.respond(R().error("roleId为空"))
                        return@get finish()
                    }
                    val resourcePage = roleService.getPageResourceOwnRole(roleId, currentPage, pageSize)
                    call.respond(R().ok("resourcePage" to resourcePage))
                    return@get finish()
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@get finish()
                }


            }
            get("/resource/-/{roleId}/{currentPage}/{pageSize}") {
                try {
                    val roleId = call.parameters["roleId"]?.toLong()
                    val currentPage = call.parameters["currentPage"]?.toInt() ?: 1
                    val pageSize = call.parameters["pageSize"]?.toInt() ?: 10
                    if (roleId == null) {
                        call.respond(R().error("roleId为空"))
                        return@get finish()
                    }
                    val resourcePage = roleService.getPageResourceNotOwnRole(roleId, currentPage, pageSize)
                    call.respond(R().ok("resourcePage" to resourcePage))
                    return@get finish()
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@get finish()
                }


            }

            get("/{currentPage}/{pageSize}") {
                try {
                    val currentPage = call.parameters["currentPage"]?.toInt() ?: 1
                    val pageSize = call.parameters["pageSize"]?.toInt() ?: 1

                    val rolePage = roleService.getPageRole(currentPage, pageSize)
                    call.respond(R().ok("rolePage" to rolePage))
                    return@get finish()
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@get finish()
                }

            }
            post("/authority/resource/{roleId}/{resourceId}") {
                try {
                    val roleId = call.parameters["roleId"]?.toLong()
                    val resourceId = call.parameters["resourceId"]?.toLong()
                    roleService.authorityRoleResource(roleId!!, resourceId!!)
                    call.respond(R().ok())
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@post finish()
                }

            }

            delete("/authority/resource/{roleId}/{resourceId}") {
                try {
                    val roleId = call.parameters["roleId"]?.toLong()
                    val resourceId = call.parameters["resourceId"]?.toLong()
                    roleService.deleteAuthorityRoleResource(roleId!!, resourceId!!)
                    call.respond(R().ok())
                    return@delete finish()
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@delete finish()
                }
            }

            post {
                val authRole = call.receive<AuthRoleDTO>()
                val validate = ValidationUtils.validate(authRole, AddGroup::class.java)
                if (validate.success()) {
                    if (roleService.addRole(authRole)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("add role success: {}", authRole)
                        }
                        call.respond(R().ok())
                        return@post finish()
                    } else {
                        call.respond(R().error("role already exist"))
                        return@post finish()
                    }
                } else {
                    call.respond(R().error(validate))
                    return@post finish()
                }
            }

            put {
                val authRole = call.receive<AuthRoleDTO>()
                val validate = ValidationUtils.validate(authRole, UpdateGroup::class.java)
                if (validate.success()) {
                    if (roleService.updateRole(authRole)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("update role success: {}", authRole)
                        }
                        call.respond(R().ok())
                        return@put finish()
                    } else {
                        call.respond(R().error("role not exist"))
                        return@put finish()
                    }
                } else {
                    call.respond(R().error(validate))
                    return@put finish()
                }
            }

            delete("/{roleId}") {
                try {
                    val roleId = call.parameters["roleId"]?.toLong()
                    if (roleId != null) {
                        if (roleService.deleteRole(roleId)) {
                            if (call.application.log.isDebugEnabled) {
                                call.application.log.debug("delete role success: {}", roleId)
                            }
                            call.respond(R().ok())
                            return@delete finish()
                        } else {
                            call.application.log.debug("delete role fail: {}", roleId)
                            call.respond(R().error("delete role fail, no this role here"))
                            return@delete finish()
                        }
                    } else {
                        call.respond(R().error("roleId 为空"))
                        return@delete finish()
                    }
                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@delete finish()
                }
            }
        }
    }
}