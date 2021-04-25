package appshop.modules.sys.service

import appshop.modules.sys.dto.*
import com.sun.org.glassfish.gmbal.Description
import com.usthe.sureness.provider.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AccountService
 * DATE: 2021/3/26
 * TIME: 14:16
 * JDK 1.8
 */
interface AccountService {
    /**
     * Verify account validity, username and password
     * @param account account info
     * @return success-true failed-false
     */
    fun authenticateAccount(account: AccountDTO): Boolean

    /**
     * Get all roles owned by this username account, combine them into string list
     * @param username account username
     * @return role-string eg role1,role3,role2
     */
    fun loadAccountRoles(username: String): List<String>

    /**
     * register account
     * @param account account info
     * @return success-true failed-false
     */
    @Description("判断是否有account 有则false不注册，没有返回true然后保存account\n")
    fun registerAccount(account: AccountDTO): Boolean

    /**
     * Determine whether the account already exists
     * @param account account info
     * @return exist-true no-false
     */
    fun isAccountExist(account: AccountDTO): Boolean

    /**
     * Load the account information by username
     * @param username account username
     * @return account
     */
    fun loadAccount(username: String): SurenessAccount?

    /**
     * authority User Role by username and roleId
     * @param appId account username
     * @param roleId roleId
     * @return success-true failed-false
     */
    @Description("添加用户的role，用户存在返回false")
    fun authorityUserRole(appId: String, roleId: Long): Boolean

    /**
     * delete authority User Role by username and roleId
     * @param appId account username
     * @param roleId roleId
     * @return success-true failed-false
     */
    fun deleteAuthorityUserRole(appId: String, roleId: Long): Boolean
}