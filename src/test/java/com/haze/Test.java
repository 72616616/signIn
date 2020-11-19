package com.haze;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haze.entity.Data;
import com.haze.entity.User;
import com.haze.service.UserService;
import com.haze.timer.SaticScheduleTask;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    SaticScheduleTask saticScheduleTask;




    @Autowired
    UserService userService;



    @org.junit.Test
    public void SignIn(){
        saticScheduleTask.timedTask();
    }


}
