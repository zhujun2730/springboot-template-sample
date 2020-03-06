package com.egreat.movie.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egreat.movie.api.entity.AssignDto;
import com.egreat.movie.api.entity.PageDto;
import com.egreat.movie.api.entity.PostponeDto;
import com.egreat.movie.api.entity.SysUser;
import com.egreat.movie.api.service.IUserService;
import com.egreat.movie.api.utils.MD5Util;
import com.egreat.movie.api.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
@Controller
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public String login(HashMap<String, Object> map, Model model) throws Exception {
        return "login";
    }

    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    @ResponseBody
    public R loginSubmit(@ModelAttribute SysUser user, HttpSession session) throws Exception {

        if (StringUtils.isEmpty(user.getUsername())) {
            return R.error(500, "用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return R.error(500, "密码不能为空");
        }

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser sysUser = userService.getOne(queryWrapper);

        if (sysUser == null) {
            return R.error(500, "用户名不存在");
        }

        if (!MD5Util.MD5(user.getPassword()).equals(sysUser.getPassword())) {
            return R.error(500, "密码错误");
        }

        if (sysUser.getLevel() == 3) {
            return R.error(500, "该账户无权限登录");
        }

        session.setAttribute("user", sysUser);
        logger.info(user.toString());
        return R.ok();
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("/user")
    public String user(HttpSession session, Model model) throws Exception {
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        logger.info(user.toString());

        return "user";
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public R getUserInfo(HttpSession session, @ModelAttribute PageDto dto) throws Exception {

        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            return R.error("未登录");
        }

        user.setPassword("");

        return R.ok(user);
    }

    @RequestMapping(value = "/user/getList", method = RequestMethod.POST)
    @ResponseBody
    public R getList(HttpSession session, @ModelAttribute PageDto dto) throws Exception {

        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            return R.error("未登录");
        }

        Long level = user.getLevel();

        return userService.getList(dto);
    }

    @RequestMapping(value = "/user/getZpList", method = RequestMethod.POST)
    @ResponseBody
    public R getZpList(HttpSession session, @ModelAttribute PageDto dto) throws Exception {

        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            return R.error("未登录");
        }

        Integer level = dto.getLevel();

        if (level == 3) {
            level = 2;
        } else if (level == 2) {
            level = 1;
        }

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SysUser::getLevel, level)
                .eq(SysUser::getStatus, 1)
                .orderByDesc(SysUser::getCreateTime);

        List<SysUser> list = userService.list(queryWrapper);

        return R.ok(list);
    }

    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    @ResponseBody
    public R addUser(@ModelAttribute SysUser user, HttpSession session) throws Exception {

        SysUser sessionUser = (SysUser) session.getAttribute("user");
        if (sessionUser == null) {
            return R.error("未登录");
        }

        logger.info(user.toString());

        if (StringUtils.isEmpty(user.getUsername())) {
            return R.error(500, "登录账号不能为空");
        }

        if (StringUtils.isEmpty(user.getChannelName())) {
            return R.error(500, "会员(代理商)名称不能为空");
        }

        SysUser sysUser = userService.getOne(
                new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, user.getUsername())
        );

        if (sysUser != null) {
            return R.error(500, "登录账号已存在");
        }

        user.setPassword(MD5Util.MD5(user.getPassword()));

        if (user.getLevel() == 3) {
            user.setExpiredTime(new Date());
        }

        userService.add(user);

        return R.ok();
    }

    @RequestMapping(value = "/user/postpone", method = RequestMethod.POST)
    @ResponseBody
    public R postpone(@ModelAttribute PostponeDto dto, HttpSession session) throws Exception {

        SysUser sessionUser = (SysUser) session.getAttribute("user");
        if (sessionUser == null) {
            return R.error("未登录");
        }

        Integer days = 0;
        if (dto.getDiyDays() == null) {
            if (dto.getRadio() == 1) {
                days = 365;
            } else if (dto.getRadio() == 2) {
                days = 30;
            } else if (dto.getRadio() == 3) {
                days = 90;
            }
        } else {
            days = dto.getDiyDays();
        }

        Map map = new HashMap<>();
        map.put("days", days);
        map.put("userId", dto.getUserId());

        userService.postponeUser(map);

        logger.info(dto.toString());

        return R.ok();
    }

    @RequestMapping(value = "/user/assign", method = RequestMethod.POST)
    @ResponseBody
    public R assign(@ModelAttribute AssignDto dto, HttpSession session) throws Exception {

        SysUser sessionUser = (SysUser) session.getAttribute("user");
        if (sessionUser == null) {
            return R.error("未登录");
        }

        logger.info(dto.toString());

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(SysUser::getUserId, dto.getSelectUserId());

        userService.update(new SysUser().setCreateId(dto.getAssignUserId()),queryWrapper);

        return R.ok();
    }
}
