package com.haze;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {


            String url = "https://api-takumi.mihoyo.com/binding/api/getUserGameRolesByCookie";

            HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头

            headers.put("Cookie","_gid=GA1.2.447626320.1604642722; UM_distinctid=1759c28e1c15e0-0f696feea594e2-230346d-1fa400-1759c28e1c2794; account_id=175671720; cookie_token=Yyjt5MrEwW7SvKp7zEhulCoPtoV3OhjsQgza2AqI; ltoken=QFAvTdqacuphpv6dCsY0G8oltGXdt7UQSect1Mt7; ltuid=175671720; _ga_KJ6J9V9VZQ=GS1.1.1604643504.1.0.1604643507.0; _ga=GA1.2.233111489.1604642722; _gat=1;CNZZDATA1275023096=216573569-1604642001-https%253A%252F%252Fwww.baidu.com%252F%7C1604646372");

            //发送get请求并接收响应数据
            String result= HttpRequest.get(url).addHeaders(headers).execute().body();
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObject1 = list.getJSONObject(i);

                String game_biz = jsonObject1.getString("game_biz");
                if ("hk4e_cn".equals(game_biz)){
                    System.out.println(jsonObject1);
                }

            }


    }


    @org.junit.jupiter.api.Test
    public  void  tese02(){
        String url = "https://api-takumi.mihoyo.com/event/bbs_sign_reward/sign";

        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头

        headers.put("Cookie","_gid=GA1.2.447626320.1604642722; UM_distinctid=1759c28e1c15e0-0f696feea594e2-230346d-1fa400-1759c28e1c2794; account_id=175671720; cookie_token=Yyjt5MrEwW7SvKp7zEhulCoPtoV3OhjsQgza2AqI; ltoken=QFAvTdqacuphpv6dCsY0G8oltGXdt7UQSect1Mt7; ltuid=175671720; _ga_KJ6J9V9VZQ=GS1.1.1604643504.1.0.1604643507.0; _ga=GA1.2.233111489.1604642722; _gat=1;CNZZDATA1275023096=216573569-1604642001-https%253A%252F%252Fwww.baidu.com%252F%7C1604646372");
        headers.put("x-rpc-device_id","application/json");
        headers.put("Content-Type","xiaomi");

//        Data data = new Data();
//        data.setAct_id("e202009291139501");
//        data.setRegion("cn_gf01");
//        data.setUid("106332242");

//        String s = JSON.toJSONString(data);
//        System.out.println(s);

//        //发送get请求并接收响应数据
//        String result= HttpUtil.createPost(url).addHeaders(headers).body(s).execute().body();
//        System.out.println(result);

    }
}
