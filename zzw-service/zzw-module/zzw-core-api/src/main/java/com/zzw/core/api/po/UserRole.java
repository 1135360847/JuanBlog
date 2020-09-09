package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户相关实体类</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zzw_user_role")
public class UserRole extends Model<UserRole> {

	private static final long serialVersionUID = 1594728434409L;
	
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(name = "id" , value = "主键id")
	private String id;
    
	@ApiModelProperty(name = "roleId" , value = "角色id")
	private String roleId;
    
	@ApiModelProperty(name = "userId" , value = "用户id")
	private String userId;
    
	@ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
	@TableLogic
	private String isDeleted;

	@ApiModelProperty(name = "gmtCreate" , value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreate;

	@ApiModelProperty(name = "gmtModified" , value = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtModified;

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
