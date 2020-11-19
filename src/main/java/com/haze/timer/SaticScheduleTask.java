package com.haze.timer;


import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import com.haze.entity.Data;
import com.haze.entity.Log;
import com.haze.entity.User;
import com.haze.service.LogService;
import com.haze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    UserService userService;

    @Autowired
    LogService logService;

    @Scheduled(cron = "0 5 4 * * ?")
    public void timedTask() {
        List<User> userList = userService.list();
        for (User user : userList) {
            String s = SignIn(user);
            try {
                Thread.sleep (10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(s);
        }



    }


    public String SignIn(User user){


        String url = "https://api-takumi.mihoyo.com/event/bbs_sign_reward/sign";

        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头

        headers.put("Cookie",user.getCookie());


        headers.put("x-rpc-device_id", IdUtil.simpleUUID());
        headers.put("x-rpc-client_type","5");
        headers.put("x-rpc-app_version","2.1.0");
        headers.put("DS","1605753601,fl9pnd,dfd54d95ab705a1e857cc005737e1c6d");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 14_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) miHoYoBBS/2.1.0");
        headers.put("Referer","https://webstatic.mihoyo.com/bbs/event/signin-ys/index.html?bbs_auth_required=true&act_id=e202009291139501&utm_source=bbs&utm_medium=mys&utm_campaign=icon");

        Data data = new Data();
        data.setAct_id("e202009291139501");
        data.setRegion("cn_gf01");
        data.setUid(user.getUid());

        String s = JSON.toJSONString(data);

        //发送get请求并接收响应数据
        String result= HttpUtil.createPost(url).addHeaders(headers).body(s).execute().body();

        Log log = new Log();
        log.setUid(user.getUid());
        log.setLogContent(result);
        log.setCreateTime(LocalDateTime.now());
        logService.save(log);

        return result;
    }
}
