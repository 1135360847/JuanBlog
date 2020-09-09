package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博客标签相关实体类</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zzw_tag")
public class Tag extends Model<Tag> {

	private static final long serialVersionUID = 1597302248875L;

	@TableId(type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;

	@ApiModelProperty(name = "name" , value = "名称")
	private String name;

	@ApiModelProperty(name = "isDeleted" , value = "删除标志")
	@TableLogic
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
