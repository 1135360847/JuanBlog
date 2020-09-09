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
@TableName("zzw_admin_user")
public class AdminUser extends Model<AdminUser> {

	private static final long serialVersionUID = 1594728172035L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(name = "id" , value = "会员id")
	private String id;
    
	@ApiModelProperty(name = "username" , value = "微信openid")
	private String username;
    
	@ApiModelProperty(name = "password" , value = "密码")
	private String password;
    
	@ApiModelProperty(name = "nickName" , value = "昵称")
	private String nickName;
    
	@ApiModelProperty(name = "salt" , value = "用户头像")
	private String salt;
    
	@ApiModelProperty(name = "token" , value = "用户签名")
	private String token;

	@TableLogic
	@ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
	private Integer isDeleted;
    
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
