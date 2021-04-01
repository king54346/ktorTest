package appshop.util

import com.sun.corba.se.impl.corba.AnyImpl
import org.eclipse.jetty.http.HttpStatus
import kotlin.collections.HashMap

/**
 * 使用put添加
 *public fun <K, V> hashMapOf(vararg pairs: Pair<K, V>): HashMap<K, V>
 *     = HashMap<K, V>(mapCapacity(pairs.size)).apply { putAll(pairs) }
 *
 * return hashmap
 */
class R : HashMap<String, Any?>() {
     override fun put(key: String, value: Any?): R {
        super.put(key, value)
        return this
    }
    init {
        put("code", 0)
        put("msg", "success")
    }
}

fun R.error(msg: String): R {
    return error(HttpStatus.INTERNAL_SERVER_ERROR_500, msg)
}

@JvmOverloads
fun R.error(code: Int = HttpStatus.INTERNAL_SERVER_ERROR_500, msg: String = "未知异常，请联系管理员"): R {
    this["code"] = code
    this["msg"] = msg
    return this
}
fun R.ok(msg: String):R{
    this["msg"] = msg
    return this
}

fun R.ok(vararg pairs: Pair<String, Any?>): R {
    this.putAll(pairs)
    return this
}

fun R.ok(): R {
    return this
}