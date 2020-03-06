package com.egreat.movie.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egreat.movie.api.entity.PageDto;
import com.egreat.movie.api.entity.SysUser;
import com.egreat.movie.api.utils.R;

import java.util.Map;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
public interface IUserService extends IService<SysUser> {

    public R getList(PageDto dto) throws Exception;

    public R add(SysUser item) throws Exception;

    public R edit(SysUser item) throws Exception;

    public R del(Long id) throws Exception;

    public R getDetail(Long id) throws Exception;

    void postponeUser(Map map);
}
