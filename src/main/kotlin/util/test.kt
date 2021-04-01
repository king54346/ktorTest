package appshop.util

import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: test
 * DATE: 2021/3/11
 * TIME: 19:00
 * JDK 1.8
 */


data class person2(
    @field: NotNull
    val name: String,
    @field:Range(min = 20, max = 50, message = "age应该在[20，50]之间")
    val age:Int
)


fun main() {

    try {
        val validate = ValidationUtils.validate(person2("", 1123))
        println(validate)
    } catch (e: Exception) {
        println(e.message)
    }
}