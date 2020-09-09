package com.zzw.common.core.rest.controller;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzw.common.core.rest.baseVO.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

public interface IController<E extends Model, ID extends Serializable, S extends IService<E>> extends IReadonlyController<E, ID, S> {

    @ApiOperation(value = "新增" ,  notes="新增")
    @ResponseBody
    @PostMapping
    default R post(@Valid @RequestBody E e, BindingResult result) throws Exception {
        validate(result);
        return new R<>(getService().save(e));
    }

    @ApiOperation(value = "修改" ,  notes="修改")
    @ResponseBody
    @PutMapping
    default R put(@Valid @RequestBody E e, BindingResult result) throws Exception {
        validate(result);
        return new R<>(getService().updateById(e));
    }


    @ApiOperation(value = "删除" ,  notes="删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType="path")
    })
    @ResponseBody
    @DeleteMapping(value = "{id}")
    default R delete(@PathVariable("id") ID id) throws Exception {
        return new R<>(getService().removeById(id));
    }

    default void validate(BindingResult result) throws Exception {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(error -> {
                sb.append(error.getDefaultMessage()).append("，");
            });
            String s = sb.toString();
            s = s.substring(0, s.length() - 1) + "！";
            throw new Exception(s);
        }
    }

    ObjectMapper getObjectMapper();
}
