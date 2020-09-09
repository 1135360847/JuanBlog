package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
@TableName("zzw_permission")
public class Permission extends Model<Permission> {

	private static final long serialVersionUID = 1594728384243L;
	
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(name = "id" , value = "编号")
	private String id;
    
	@ApiModelProperty(name = "pid" , value = "所属上级")
	private String pid;
    
	@ApiModelProperty(name = "name" , value = "名称")
	private String name;
    
	@ApiModelProperty(name = "type" , value = "类型(1:菜单,2:按钮)")
	private Integer type;
    
	@ApiModelProperty(name = "permissionValue" , value = "权限值")
	private String permissionValue;
    
	@ApiModelProperty(name = "path" , value = "访问路径")
	private String path;
    
	@ApiModelProperty(name = "component" , value = "组件路径")
	private String component;
    
	@ApiModelProperty(name = "icon" , value = "图标")
	private String icon;
    
	@ApiModelProperty(name = "status" , value = "状态(0:禁止,1:正常)")
	private Integer status;
    
	@ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
	@TableLogic
	private Integer isDeleted;

	@ApiModelProperty(name = "gmtCreate" , value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreate;

	@ApiModelProperty(name = "gmtModified" , value = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtModified;

	@TableField(exist = false)
	@ApiModelProperty(name = "children" , value = "子类")
	private List<Permission> children;

	@TableField(exist = false)
	@ApiModelProperty(name = "Level" , value = "层级")
	private Integer Level;

	@ApiModelProperty(value = "是否选中")
	@TableField(exist = false)
	private boolean isSelect;
	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
