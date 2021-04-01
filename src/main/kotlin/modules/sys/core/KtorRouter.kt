package appshop.modules.sys.core

import io.ktor.routing.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: KtorRouter
 * DATE: 2021/3/30
 * TIME: 16:14
 * JDK 1.8
 */

interface KtorRouter {

    /**
     * 路由功能
     */
    fun Routing.route()
}