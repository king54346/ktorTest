package appshop.modules.sys.dto

import appshop.annotation.*
import appshop.modules.sys.valid.*
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.*
import org.hibernate.validator.constraints.NotBlank
import java.time.*
import javax.annotation.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthResourceDTO
 * DATE: 2021/3/30
 * TIME: 19:40
 * JDK 1.8
 */
@noArg
data class AuthResourceDTO(
    @field:NotNull(message = "修改必须指定id", groups = [UpdateGroup::class])
    @field:Null(message = "新增不能指定id",groups=[AddGroup::class])
    var id: Long?,
    @field:NotBlank(message = "name can not null",groups = [AddGroup::class,UpdateGroup::class])
    @field:Length(min = 3, max = 100, message = "name length in 3-100",groups = [AddGroup::class,UpdateGroup::class])
    var name: String,
    @field:NotBlank(message = "code can not null",groups = [AddGroup::class,UpdateGroup::class])
    @field:Length(min = 3, max = 100, message = "code length in 3-100",groups = [AddGroup::class,UpdateGroup::class])
    var code: String,
    @field:NotBlank(message = "uri can not null",groups = [AddGroup::class,UpdateGroup::class])
    var uri: String,
    var type: String,
    @field:NotBlank(message = "method can not null",groups = [AddGroup::class,UpdateGroup::class])
    var method: String,
    @field:Range(min = 0, max = 9, message = "1 enable, 9 disable",groups = [AddGroup::class,UpdateGroup::class])
    var status: Int,
    var description: String="",
    var gmtUpdate: LocalDateTime = LocalDateTime.now(),
    var gmtCreate: LocalDateTime= LocalDateTime.now()

)
