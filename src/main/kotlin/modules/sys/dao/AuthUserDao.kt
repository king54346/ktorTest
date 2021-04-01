package appshop.modules.sys.dao

import appshop.modules.sys.dao.tablebind.*
import appshop.modules.sys.dto.*
import appshop.modules.sys.po.*
import appshop.util.*
import com.sun.org.apache.bcel.internal.generic.*
import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.entity.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: accountDao
 * DATE: 2021/3/26
 * TIME: 16:23
 * JDK 1.8
 */
class AuthUserDao {
    private val Database.auth_user get() = this.sequenceOf(AuthUsers)
    fun findAuthUserByUsername(username: String): AuthUser? {
        return db.auth_user.find {
            it.name eq username
        }
    }

    fun save(authUser: AuthUser):Boolean {
        val find = db.auth_user.find {
            it.name eq authUser.username
        }
        if(find!=null){
            return false
        }
        return db.auth_user.add(authUser)>0
    }
}