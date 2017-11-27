package com.kaishengit;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zh
 * Created by Administrator on 2017/11/23.
 * 此类用于智能聊天的工具类
 */
@Component
public class TulingUtil {
    public final String POST_DEPT_URL = "http://www.tuling123.com/openapi/api";

    private String key = "";

    public String chatBot(){
        Map<String,Object> date = new HashMap<String, Object>();
        date.put("key",key);
        date.put("info","好吧");
        date.put("userid","11");
        String resultJson = sendHttpPostRequestTuLing(POST_DEPT_URL,JSON.toJSONString(date));
        return resultJson;
    }


    /**
     * 发出post请求
     * @param url 请求的url地址
     * @param json 请求体
     */
    public String sendHttpPostRequestTuLing(String url,String json) {

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        //通过json格式创建post请求体
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("http请求异常"+e);
        }


    }

}
