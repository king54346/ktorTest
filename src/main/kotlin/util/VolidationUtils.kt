package appshop.util

import appshop.modules.sys.valid.*
import jakarta.validation.*
import org.hibernate.validator.*
import kotlin.reflect.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: VolidationUtils
 * DATE: 2021/3/11
 * TIME: 16:46
 * JDK 1.8
 */


object ValidationUtils {
    /**
     * 使用hibernate的注解来进行验证
     *
     */
    private val validator: Validator = Validation
        .byProvider(HibernateValidator::class.java)
        .configure()
        .failFast(true)//有一个失败就停止检查
        .buildValidatorFactory()
        .validator

    /**
     * 功能描述:
     * 〈注解验证参数〉
     *
     * @param obj
     */
    fun <T> validate(obj: T): String {
        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate(obj)
        //验证不通过，取得违规message 并返回
        return if (constraintViolations.isNotEmpty()) {
            constraintViolations.iterator().next().message
        } else "success"
    }
    fun <T> validate(obj: T,group:Class<*>): String {
        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate(obj,group)
        //验证不通过，取得违规message 并返回
        return if (constraintViolations.isNotEmpty()) {
            constraintViolations.iterator().next().message
        } else "success"
    }
    fun String.success():Boolean{
        return this=="success"
    }
}
