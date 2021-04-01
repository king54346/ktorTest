package appshop.util

import appshop.modules.sys.service.*
import appshop.modules.sys.service.impl.*
import com.sun.org.apache.bcel.internal.generic.*
import com.usthe.sureness.*
import com.usthe.sureness.matcher.*
import com.usthe.sureness.mgt.*
import com.usthe.sureness.processor.*
import com.usthe.sureness.processor.exception.*
import com.usthe.sureness.processor.support.*
import com.usthe.sureness.provider.*
import com.usthe.sureness.provider.annotation.*
import com.usthe.sureness.provider.ducument.*
import com.usthe.sureness.subject.*
import com.usthe.sureness.subject.creater.*
import com.usthe.sureness.util.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.servlet.*
import org.kodein.di.*
import org.slf4j.*
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: SurenessConfig
 * DATE: 2021/3/26
 * TIME: 13:31
 * JDK 1.8
 */

@EngineAPI
fun Application.SurenessConfig() {
    Config()
    intercept()
}

class Config {
   init {
       securityManager(processorManager(),pathRoleMatcher(),subjectFactory())
   }

    companion object{
        private val logger = LoggerFactory.getLogger(Config::class.java)
        private const val SECRET_KEY = "MIIEowIBAl+f/dKhaX6csgOCTlCxq20yhmUea6H1JIpST3ST1SE3Rwp" +
                "LnfKefTjsIfJLBa2YkhEqE/GtcHHTNe4CU6+9y/S5z50Kik75LsP43r" +
                "RnLN7XNn2wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
                "4mWEa6EwrPhTZmbT5Mt45AM2UYhzDHK+0F0rUq3MwH+oXsm+L3F/zjj" +
                "M6EByXIO+SV4+8tVt4bisXQ13rbN0oxhUZR73+LDj9mxa6rFhMW+lfx" +
                "CyaFv1bwq9Eik0jdrKUtsA6bx2sDJeFV643R+yYxGMRIqcBIp6AKA98" +
                "GM2RIqcBIp6-?::4320fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546"

        public fun processorManager(): ProcessorManager {
            val processorList = mutableListOf<Processor>()
            val noneProcessor = NoneProcessor()
            processorList.add(noneProcessor)
            val jwtProcessor = JwtProcessor()
            processorList.add(jwtProcessor)
            // use default basic auth processor
            val passwordProcessor = PasswordProcessor()
            passwordProcessor.setAccountProvider(DatabaseAccountProvider())
            if (logger.isDebugEnabled) {
                logger.debug("DefaultProcessorManager init")
            }
            return DefaultProcessorManager(processorList)
        }

        public fun pathRoleMatcher(): TreePathRoleMatcher {

            // the path tree resource load from document - sureness.yml
            val documentPathTreeProvider = DocumentPathTreeProvider()
            val databasePathTreeProvider = DatabasePathTreeProvider()

            // pathRoleMatcher init
            val pathRoleMatcher = DefaultPathRoleMatcher()


            pathRoleMatcher.setPathTreeProviderList(
                listOf(
                    documentPathTreeProvider,
                    databasePathTreeProvider,
                )
            )
            pathRoleMatcher.buildTree()
            if (logger.isDebugEnabled) {
                logger.debug("DefaultPathRoleMatcher init")
            }
            return pathRoleMatcher
        }

        public fun subjectFactory(): SubjectFactory {
            // SubjectFactory init
            val subjectFactory: SubjectFactory = SurenessSubjectFactory()
            subjectFactory.registerSubjectCreator(
                Arrays.asList(
                    // attention! must add noSubjectCreator first
                    NoneSubjectServletCreator(),  // use default basic auth subject creator
                    JwtSubjectServletCreator(),  // use custom password creator
                )
            )
            if (logger.isDebugEnabled) {
                logger.debug("SurenessSubjectFactory init")
            }
            return subjectFactory
        }

        public fun securityManager(
            processorManager: ProcessorManager,
            pathRoleMatcher: TreePathRoleMatcher, subjectFactory: SubjectFactory
        ): SurenessSecurityManager{
            JsonWebTokenUtil.setDefaultSecretKey(SECRET_KEY)
            // surenessSecurityManager init
            val securityManager = SurenessSecurityManager.getInstance()
            securityManager.pathRoleMatcher = pathRoleMatcher
            securityManager.subjectFactory = subjectFactory
            securityManager.processorManager = processorManager
            if (logger.isDebugEnabled) {
                logger.debug("SurenessSecurityManager init")
            }
            return securityManager
        }

    }

}
class DatabaseAccountProvider : SurenessAccountProvider {
    val accountService by  totalDI.instance<AccountService>()
    override fun loadAccount(appId: String): SurenessAccount? {
        return accountService.loadAccount(appId)
    }
}
class DatabasePathTreeProvider : PathTreeProvider {
    private val resourceServiceImp by totalDI.instance<ResourceService>()
    override fun providePathData(): Set<String> {
        return SurenessCommonUtil.attachContextPath(contextPath, resourceServiceImp.getAllEnableResourcePath())
    }

    override fun provideExcludedResource(): Set<String> {
        return SurenessCommonUtil.attachContextPath(contextPath, resourceServiceImp.getAllDisableResourcePath())
    }
}
@EngineAPI
fun Application.intercept() {
    intercept(ApplicationCallPipeline.Call) {
        try {
            val request = (call.request as AsyncServletApplicationRequest).servletRequest
            val subject = SurenessSecurityManager.getInstance().checkIn(request)
            // 认证鉴权成功则会返回带用户信息的subject 可以将subject信息绑定到当前线程上下文holder供后面使用
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject)
                log.debug("auth success!")
            }
        } catch (e4: ProcessorNotFoundException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e4: UnknownAccountException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e4: UnsupportedSubjectException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e2: DisabledAccountException) {
            log.debug("the account is disabled")
            call.respondText(e2.localizedMessage)
            return@intercept finish()

        } catch (e2: ExcessiveAttemptsException) {
            log.debug("the account is disabled")
            call.respondText(e2.localizedMessage)
            return@intercept finish()

        } catch (e3: IncorrectCredentialsException) {
            log.debug("this account credential is incorrect or expired")
            call.respondText(e3.localizedMessage)
            return@intercept finish()

        } catch (e3: ExpiredCredentialsException) {
            log.debug("this account credential is incorrect or expired")
            call.respondText(e3.localizedMessage)
            return@intercept finish()

        } catch (e4: NeedDigestInfoException) {
            log.debug("you should try once again with digest auth information")
            call.response.header("WWW-Authenticate", e4.authenticate)
            call.response.status(HttpStatusCode.Unauthorized)
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e5: UnauthorizedException) {
            log.debug("this account can not access this resource")
            call.respondText(e5.localizedMessage)
            return@intercept finish()

        } catch (e: RuntimeException) {
            log.error("other exception happen: ", e)
            call.respondText(e.localizedMessage)
            return@intercept finish()
        }
    }
}