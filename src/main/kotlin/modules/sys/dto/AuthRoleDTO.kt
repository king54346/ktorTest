package appshop.modules.sys.dto

import appshop.annotation.*
import appshop.modules.sys.valid.*
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.*
import org.hibernate.validator.constraints.NotBlank
import java.time.*

/**
 * Created with IntelliJ IDEA
 * USER:jin
 * CLASSNAME: AuthRoleDTO
 * DATE: 2021/3/31
 * TIME: 23:09
 * JDK 1.8
 */
@noArg
data class AuthRoleDTO(
    @field:NotNull(message = "修改必须指定id", groups = [UpdateGroup::class])
    @field:Null(message = "新增不能指定id", groups = [AddGroup::class])
    var id: Long?,
    @field:NotBlank(message = "name can not null", groups = [AddGroup::class, UpdateGroup::class])
    @field:Length(min = 3, max = 100, message = "name length in 3-100", groups = [AddGroup::class, UpdateGroup::class])
    var name: String,
    @field:NotBlank(message = "code can not null", groups = [AddGroup::class, UpdateGroup::class])
    @field:Length(min = 3, max = 100, message = "code length in 3-100", groups = [AddGroup::class, UpdateGroup::class])
    var code: String,
    @field:Range(min = 0, max = 9, message = "1 enable, 9 disable", groups = [AddGroup::class, UpdateGroup::class])
    var status: Int,
    var description: String = "",
    var gmtUpdate: LocalDateTime = LocalDateTime.now(),
    var gmtCreate: LocalDateTime = LocalDateTime.now()
)
