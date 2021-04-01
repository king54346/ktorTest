package appshop.modules.sys.controller

import appshop.modules.sys.core.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.service.*
import appshop.modules.sys.service.impl.*
import appshop.util.*
import appshop.util.ValidationUtils.success
import com.usthe.sureness.util.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.*
import java.util.*


class AccountController : KtorRouter {
    val accountService by totalDI.instance<AccountService>()
    override fun Routing.route() {
        route("/auth") {
            post("/token") {
                val account = call.receive<AccountDTO>()
                val validate = ValidationUtils.validate(account)

                if (validate.success()) {
                    val authenticatedFlag = accountService.authenticateAccount(account)
                    if (!authenticatedFlag) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("account: {} authenticated fail", account)
                        }
                        call.respond(R().error("username or password not incorrect"))
                        return@post finish()
                    }
                    val ownRole = accountService.loadAccountRoles(account.username)
                    val refreshPeriodTime = 36000L
                    val jwt = JsonWebTokenUtil.issueJwt(
                        UUID.randomUUID().toString(), account.username,
                        "tom-auth-server", refreshPeriodTime shr 1, ownRole
                    )
                    if (call.application.log.isDebugEnabled) {
                        call.application.log.debug("issue token success, account: {} -- token: {}", account, jwt)
                    }

                    call.respond(R().ok("token" to jwt))
                    return@post finish()
                } else {
                    call.application.log.warn(validate)
                    call.respond(R().error(validate))
                    return@post finish()
                }

            }
            post("/register") {
                val account = call.receive<AccountDTO>()
                val validate = ValidationUtils.validate(account)

                if (validate.success()) {
                    if (accountService.registerAccount(account)) {
                        if (call.application.log.isDebugEnabled) {
                            call.application.log.debug("account: {}, sign up success", account)
                        }
                        call.respond(R().ok("sign up success, login after"))
                        return@post finish()
                    } else {
                        call.respond(R().error("username already exist"))
                        return@post finish()
                    }
                } else {
                    call.application.log.warn(validate)
                    call.respond(R().error(validate))
                    return@post finish()
                }
            }
        }
    }
}
