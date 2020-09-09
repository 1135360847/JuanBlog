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
 * <p>说明： 标签分类表实体类</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zzw_article_category")
public class ArticleCategory extends Model<ArticleCategory> {

	private static final long serialVersionUID = 1596511191120L;
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;
    
	@ApiModelProperty(name = "isEnable" , value = "禁用状态0-正常，1-禁用")
	private Integer isEnable;
    
	@ApiModelProperty(name = "name" , value = "名称")
	private String name;
    
	@ApiModelProperty(name = "type" , value = "类型")
	private Integer type;

	@ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
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
