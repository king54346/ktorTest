package appshop.modules.sys.controller

import appshop.modules.sys.core.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.*
import appshop.modules.sys.service.impl.*
import appshop.modules.sys.valid.*
import appshop.util.*
import appshop.util.ValidationUtils.success
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.*
import java.lang.Exception
import java.util.*
import kotlin.math.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: ResourceController
 * DATE: 2021/3/29
 * TIME: 18:53
 * JDK 1.8
 */
class ResourceController : KtorRouter {
    private val resourceService by totalDI.instance<ResourceService>()

    override fun Routing.route() {
        route("/resource") {
            get("/{currentPage}/{pageSize}") {
                try {
                    val currentPage = call.parameters["currentPage"]?.toInt()
                    val pageSize = call.parameters["pageSize"]?.toInt()
                    if (currentPage == null || pageSize == null) {
                        // no pageable
                        val resourceList: List<AuthResourceDTO> = resourceService.getAllResource()
                        if (resourceList.isNotEmpty()) {
                            call.respond(R().ok("resourceList" to resourceList))
                            return@get finish()
                        } else {
                            call.respond(R().ok("resourceList" to listOf<AuthResource>()))
                            return@get finish()
                        }
                    } else {
                        // pageable
                        val resourcePage = resourceService.getPageResource(currentPage, pageSize)
                        call.respond(R().ok("resourceList" to resourcePage))
                        return@get finish()
                    }

                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@get finish()
                }
            }

            delete("/{resourceId}") {
                try {
                    val resourceId = call.parameters["resourceId"]!!.toLong()
                    if (resourceService.deleteResource(resourceId)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("delete resource success: {}", resourceId)
                        }
                        call.respond(R().ok())
                        return@delete finish()
                    } else {
                        call.application.log.error("delete resource fail: {}", resourceId)
                        call.respond(R().error("delete resource fail, please try again later"))
                        return@delete finish()
                    }

                } catch (e: Exception) {
                    call.respond(R().error(e.localizedMessage))
                    return@delete finish()
                }

            }
//            updateResource
            put {
                val receive = call.receive<AuthResourceDTO>()
                val validate = ValidationUtils.validate(receive,UpdateGroup::class.java)
                if (validate.success()) {
                    if (resourceService.updateResource(receive)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("update resource success: {}", receive)
                        }
                        call.respond(R().ok())
                        return@put finish()
                    } else {
                        call.respond(R().error("resource not exist"))
                        return@put finish()
                    }
                } else {
                    call.respond(R().error(validate))
                    return@put finish()
                }

            }

//            addResource
            post {
                val receive = call.receive<AuthResourceDTO>()
                val validate = ValidationUtils.validate(receive,AddGroup::class.java)
                if (validate.success()) {
                    if (resourceService.addResource(receive)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("add resource success: {}", receive)
                        }
                        call.respond(R().ok())
                    } else {
                       call.respond(R().error("resource already exist"))
                        return@post finish()
                    }
                }else {
                    call.respond(R().error(validate))
                    return@post finish()
                }
            }



        }

    }

}