package ${controllerUrl};
import ${entityUrl}.${entityName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

<#if isSwagger=="true" >
import io.swagger.annotations.Api;
</#if>
/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： ${entityComment}API接口层</P>
 * @version: ${version}
 * @author: ${author}
 * @time    ${createTime}
 *
 */
 <#if isSwagger=="true" >
@Api(tags = "${entityComment}",value="${entityComment}" )
</#if>
@RestController
@AllArgsConstructor
@RequestMapping("/${objectName}")
public class ${entityName}Controller{

}