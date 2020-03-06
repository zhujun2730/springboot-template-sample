package com.egreat.movie.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egreat.movie.api.dao.UserDao;
import com.egreat.movie.api.entity.PageDto;
import com.egreat.movie.api.entity.SysUser;
import com.egreat.movie.api.service.IUserService;
import com.egreat.movie.api.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public R getList(PageDto dto) throws Exception {
        IPage<SysUser> page = new Page<>();
        if(dto.getPageNo() != null){
            page.setCurrent(dto.getPageNo());
        }
        if(dto.getPageSize() != null){
            page.setSize(dto.getPageSize());
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SysUser::getCreateId,dto.getUserId())
                .ne(SysUser::getLevel,0) // 不等于
                .orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> iPage = this.page(page, queryWrapper);
        return R.ok(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R add(SysUser item) throws Exception {

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        item.setCreateTime(Timestamp.valueOf(nowTime));
        this.save(item);

        return R.ok("添加成功");
    }

    @Override
    public R edit(SysUser item) throws Exception {
        return null;
    }

    @Override
    public R del(Long id) throws Exception {
        this.removeById(id);
        return R.ok();
    }

    @Override
    public R getDetail(Long id) throws Exception {
        SysUser item = this.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserId,id));
        return R.ok(item);
    }

    @Override
    public void postponeUser(Map map) {
        userDao.postponeUser(map);
    }

}
