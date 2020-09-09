package com.zzw.common.core.rest.controller;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class  AbstractReadonlyController<E extends Model, ID extends Serializable> implements IReadonlyController<E, ID, IService<E>> {
    @Autowired
    private IService<E> service;

    @Override
    public IService<E> getService() {
        return service;
    }
}
