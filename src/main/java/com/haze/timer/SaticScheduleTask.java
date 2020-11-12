package com.haze.timer;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import com.haze.entity.Data;
import com.haze.entity.User;
import com.haze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    UserService userService;


    @Scheduled(cron = "0 5 4 * * ?")
    public void timedTask() {
        List<User> userList = userService.list();
        for (User user : userList) {
            SignIn(user);
        }



    }


    public String SignIn(User user){


        String url = "https://api-takumi.mihoyo.com/event/bbs_sign_reward/sign";

        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头

        headers.put("Cookie",user.getCookie());
        headers.put("x-rpc-device_id","application/json");
        headers.put("Content-Type","xiaomi");

        Data data = new Data();
        data.setAct_id("e202009291139501");
        data.setRegion("cn_gf01");
        data.setUid(user.getUid());

        String s = JSON.toJSONString(data);

        //发送get请求并接收响应数据
        String result= HttpUtil.createPost(url).addHeaders(headers).body(s).execute().body();

        return result;
    }
}
