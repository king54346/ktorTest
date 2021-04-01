package appshop.modules.sys.service.impl

import appshop.modules.sys.dao.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.modules.sys.service.*
import appshop.util.*
import com.usthe.sureness.provider.*
import com.usthe.sureness.util.*
import org.kodein.di.*
import java.time.*
import java.util.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AccountServiceImpl
 * DATE: 2021/3/26
 * TIME: 14:23
 * JDK 1.8
 */
class AccountServiceImpl : AccountService {
    private val AuthUserDao by totalDI.instance<AuthUserDao>()
    private val AuthRoleDao by totalDI.instance<AuthRoleDao>()
    private val AuthUserRoleBindDao by totalDI.instance<AuthUserRoleBindDao>()
    override fun authenticateAccount(account: AccountDTO): Boolean {
        val authUser = account.username.let { AuthUserDao.findAuthUserByUsername(it) } ?: return false
        var password = account.password
        if (Objects.nonNull(authUser.salt)) {
            // md5 with salt
            password = Md5Util.md5(password + authUser.salt)
        }
        return authUser.password == password
    }

    override fun loadAccountRoles(username: String): List<String> {
        val findAuthUserByUsername = AuthUserDao.findAuthUserByUsername(username)
        val list = mutableListOf<String>()
        findAuthUserByUsername?.id?.let {
            AuthUserRoleBindDao.findAuthUserRoleBindUserId(it)
        }
            ?.forEach {
                AuthRoleDao.findAuthRoleById(it.roleid)?.code?.let { it1 -> list.add(it1) }
            }
        return list
    }

    override fun registerAccount(account: AccountDTO): Boolean {
        if (isAccountExist(account)) {
            return false
        }
        val salt = SurenessCommonUtil.getRandomString(16)
        val password = Md5Util.md5(account.password + salt)
        val authUser = AuthUser {
            this.username = account.username
            this.password = password
            this.salt = salt
            this.status = 1
            this.where = 1
        }
        return AuthUserDao.save(authUser)
    }

    override fun isAccountExist(account: AccountDTO): Boolean {
        val authUser = AuthUserDao.findAuthUserByUsername(account.username)
        authUser ?: return false
        return true
    }

    override fun loadAccount(username: String): SurenessAccount? {
        val authUser = AuthUserDao.findAuthUserByUsername(username)
        return if (authUser != null) {
            val accountBuilder = DefaultAccount.builder(username)
                .setPassword(authUser.password)
                .setSalt(authUser.salt)
                .setDisabledAccount(1 != authUser.status)
                .setExcessiveAttempts(2 == authUser.status)
            val roles = loadAccountRoles(username)
            accountBuilder.setOwnRoles(roles)
            accountBuilder.build()
        } else {
            null
        }
    }

    override fun authorityUserRole(appId: String, roleId: Long): Boolean {
        val authuser = AuthUserDao.findAuthUserByUsername(appId)
        val findAuthRoleById = AuthRoleDao.findAuthRoleById(roleId)
        if (findAuthRoleById != null) {
            if (authuser != null) {
                val authUserRoleBind = AuthUserRoleBind {
                    this.userid = authuser.id
                    this.roleid = roleId
                }
                return AuthUserRoleBindDao.save(authUserRoleBind)
            }
        }
        return false
    }

    override fun deleteAuthorityUserRole(appId: String, roleId: Long): Boolean {
        val AuthUser = AuthUserDao.findAuthUserByUsername(appId)

        if (AuthUser != null) {
            return AuthUserRoleBindDao.deleteRoleResourceBind(AuthUser.id,roleId )
        }
        return false
    }
}