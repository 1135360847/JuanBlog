package com.zzw.common.core.rest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.common.core.rest.baseVO.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

public interface IReadonlyController<E extends Model, ID extends Serializable, S extends IService<E>> {

    @ApiOperation(value = "查询单条" ,  notes="查询单条")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType="path")
    })
    @ResponseBody
    @GetMapping(value = "{id}")
    default R get(@PathVariable("id") ID id) throws Exception {
        return new R<>(getService().getById(id));
    }

    @ResponseBody
    @GetMapping(value = "{id}/{field}")
    default R getField(@PathVariable("id") ID id, @PathVariable("field") String fieldName) throws Exception {
        E e = getService().getById(id);
        if (e == null) {
            return null;
        }
        //这里对象强制作为字符串返回不妥
//        String str = BeanUtils.getProperty(e, fieldName);
        Field field = e.getClass().getDeclaredField(fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return new R<>(field.get(e));
    }


    @ApiOperation(value = "分页查询" ,  notes="分页查询")
    @ResponseBody
    @GetMapping(value = "page")
    default R page(Page page, E e, Map<String, Object> params) throws Exception{
        return new R(getService().page(page, new QueryWrapper(params)));
    }

    @ApiOperation(value = "查询所有" ,  notes="查询所有")
    @ResponseBody
    @GetMapping(value = "")
    default R list(E e) throws Exception{
        return new R(getService().list(new QueryWrapper(e)));
    }

    S getService();
}
