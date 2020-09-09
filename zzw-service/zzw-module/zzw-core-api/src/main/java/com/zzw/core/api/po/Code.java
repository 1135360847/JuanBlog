package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * <p>说明： 博客文章相关实体类</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Code extends Model<Code> {

	private static final long serialVersionUID = 1596074374389L;

	@TableId(type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;

	@ApiModelProperty(name = "gmtCreate" , value = "创建时间")
	private LocalDateTime gmtCreate;
    
	@ApiModelProperty(name = "gmtModified" , value = "更新时间")
	private LocalDateTime gmtModified;

    
	@ApiModelProperty(name = "status" , value = "状态")
	private Integer status;
    
	@ApiModelProperty(name = "type" , value = "类型")
	private Integer type;
    
	@ApiModelProperty(name = "vaule" , value = "名称")
	private String vaule;

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
