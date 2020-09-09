package com.zzw.core.api.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class FriendAddress extends Model<FriendAddress> {

	private static final long serialVersionUID = 1596074486715L;
	
	@ApiModelProperty(name = "address" , value = "友链地址")
	private String address;
    
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;
    
	@ApiModelProperty(name = "name" , value = "友链名称")
	private String name;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
