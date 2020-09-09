package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@TableName("zzw_article")
public class Article extends Model<Article> {

	private static final long serialVersionUID = 1596074305201L;
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;

	@ApiModelProperty(name = "managerId" , value = "作者id")
	private String managerId;

	@ApiModelProperty(name = "articleConNum" , value = "文章评论数")
	private Integer articleConNum;
    
	@ApiModelProperty(name = "articleContent" , value = "文章内容")
	private String articleContent;

	@ApiModelProperty(name = "articleContentHtml" , value = "文章内容HTML")
	private String articleContentHtml;

	@ApiModelProperty(name = "articleHeadPic" , value = "文章封面")
	private String articleHeadPic;

	@ApiModelProperty(name = "articleHeadPicKey" , value = "文章封面key")
	private String articleHeadPicKey;

	@ApiModelProperty(name = "articleReadCount" , value = "文章阅读量")
	private Integer articleReadCount;
    
	@ApiModelProperty(name = "articleRemark" , value = "文章简介")
	private String articleRemark;
    
	@ApiModelProperty(name = "articleStarCount" , value = "文章点赞数")
	private Integer articleStarCount;
    
	@ApiModelProperty(name = "articleState" , value = "文章状态")
	private Integer articleState;
    
	@ApiModelProperty(name = "articleTags" , value = "文章标签")
	private String articleTags;
    
	@ApiModelProperty(name = "articleTitle" , value = "文章标题")
	private String articleTitle;
    
	@ApiModelProperty(name = "articleCategoryId" , value = "文章类型")
	private Integer articleCategoryId;

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
