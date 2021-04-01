package appshop.modules.sys.controller

import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.*
import appshop.util.*
import com.usthe.sureness.provider.*
import com.usthe.sureness.provider.ducument.*
import com.usthe.sureness.util.*
import com.wf.captcha.ArithmeticCaptcha
import com.wf.captcha.base.Captcha
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.engine.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.servlet.*
import io.ktor.sessions.*
import io.ktor.util.*
import jakarta.validation.*
import org.kodein.di.instance
import java.util.*
import java.util.UUID.randomUUID
import javax.print.attribute.standard.*
import javax.servlet.*
import kotlin.collections.HashMap
import kotlin.math.*

fun Route.AuthController() {
    /**获取验证码
     *
     */
    route("/auth") {
        get("/code.jpg") {
            // 算术类型 https://gitee.com/whvse/EasyCaptcha
            val s = call.parameters["uuid"]
            println(s)
            val captcha = ArithmeticCaptcha(200, 50)
            // 设置内置字体
            captcha.setFont(Captcha.FONT_10);
            // 几位数运算，默认是两位
            captcha.len = 4
            // 获取运算的结果
            var result = captcha.text().toDouble().toInt().toString() + ""
            println(result)
//            val uuid: String = properties.getCodeKey() + IdUtil.simpleUUID()
            // 保存
//            redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES)
            // 验证码信息
            val imgResult: HashMap<String?, Any?> = object : HashMap<String?, Any?>(2) {
                init {
                    put("img", captcha.toBase64())
                    put("uuid", randomUUID())
                }
            }
            //Provided OutputStream will be closed automatically.
            call.respondOutputStream(contentType = ContentType.Image.JPEG) {
                captcha.out(this)
            }

        }
        get {

        }
        get("/hello/{id}") {
            println(call.parameters["id"])
            println(call.request.queryParameters["id"])
            call.respond("hello")
        }
        post("/") {

        }
    }

    get("/d1") {
        call.respond("hello")
    }
    post("/api/v1/account/auth") {
        val receiveParameters = call.receive<Map<String, String>>()
        if (!receiveParameters.containsKey("appId") || !receiveParameters.containsKey("password")) {
            call.respond(HttpStatusCode.BadRequest)
            return@post finish()
        }
        val accountProvider = DocumentAccountProvider()
        val appId = receiveParameters["appId"]
        var password = receiveParameters["password"]
        val account = accountProvider.loadAccount(appId)
        if (account == null || account.isDisabledAccount || account.isExcessiveAttempts) {
            call.respond(HttpStatusCode.Forbidden)
            return@post finish()
        }
        if (account.password != null) {
            if (account.salt != null) {
                password = Md5Util.md5(password + account.salt)
            }
            if (account.password != password) {
                call.respond(HttpStatusCode.Forbidden)
                return@post finish()
            }
        }
        // Get the roles the user has - rbac
        val roles = account.ownRoles
        // issue jwt
        val jwt = JsonWebTokenUtil.issueJwt(
            randomUUID().toString(), appId,
            "token-server", 3600, roles
        )
        val body = Collections.singletonMap("token", jwt)
        call.respond(HttpStatusCode.OK,body)
    }
}
