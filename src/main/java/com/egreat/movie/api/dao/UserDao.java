package com.egreat.movie.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egreat.movie.api.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
@Component
public interface UserDao extends BaseMapper<SysUser> {
    SysUser findUserByUserName(String userName);

    void postponeUser(Map map);
}
