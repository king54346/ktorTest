package appshop.modules.sys.dto

import appshop.annotation.*
import org.hibernate.validator.constraints.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AccountDTO
 * DATE: 2021/3/26
 * TIME: 14:17
 * JDK 1.8
 */
@noArg
data class AccountDTO(
     @field:NotBlank(message = "username can not null")
     @field:Length(min = 3, max = 100, message = "username length in 3-100")
     var username: String,
     @field:NotBlank(message = "password can not null")
     @field:Length(min = 3, max = 100, message = "password length in 3-100")
     var password: String)
