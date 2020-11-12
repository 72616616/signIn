package com.haze.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haze.entity.User;
import com.haze.service.UserService;
import com.haze.timer.SaticScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-11-07
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SaticScheduleTask saticScheduleTask;

    @PostMapping("/user/sigIn")
    public String sigIn(@RequestParam("cookie") String cookie, Map<String, Object> map) {


        if (StrUtil.isEmpty(cookie)) {
            return "cookie为空";
        }

        //{"is_chosen":true,"game_uid":"106332242","is_official":true,"level":41,"game_biz":"hk4e_cn","nickname":"服务器蛀虫","region_name":"天空岛","region":"cn_gf01"}
        JSONObject gameUser = getGameUser(cookie);

        String uid = gameUser.getString("game_uid");
        String nickname = gameUser.getString("nickname");

        User user = new User();
        user.setUid(uid);
        user.setCookie(cookie);

        String s = saticScheduleTask.SignIn(user);
        System.out.println(s);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uid", uid);

        System.out.println(uid);

        User one = userService.getOne(userQueryWrapper);
        if (one != null) {
            one.setCookie(cookie);
            userService.updateById(one);
        } else {
            userService.save(user);

        }
        return "保存成功";
    }


    public JSONObject getGameUser(String cookie) {

        String url = "https://api-takumi.mihoyo.com/binding/api/getUserGameRolesByCookie";

        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头

        headers.put("Cookie", cookie);

        //发送get请求并接收响应数据
        String result = HttpRequest.get(url).addHeaders(headers).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject1 = list.getJSONObject(i);

            String game_biz = jsonObject1.getString("game_biz");
            if ("hk4e_cn".equals(game_biz)) {
                return jsonObject1;
            }

        }

        return null;
    }
}
