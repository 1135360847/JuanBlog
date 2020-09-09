/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.flying.cattle.mdg.aid
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.zzw.generator.aid;

import lombok.Data;

import java.io.Serializable;

/**
 * app分页组件
 * @author zzw
 */
@Data
public class PageParam<T>  implements Serializable{
	
	private static final long serialVersionUID = -7248374800878487522L;
	/**
     * <p>当前页</p>
     */
    private int pageNum=1;
    /**
     * <p>每页记录数</p>
     */
    private int pageSize=10;

    /**
     * <p>分页外的其它参数</p>
     */
    private T param;
    
}
