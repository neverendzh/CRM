package com.kaishengit;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.exception.WeiXinException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zh
 * Created by Administrator on 2017/11/21.
 * 连接企业微信的工具类
 */
@Component
public class WeiXinUtil {
    public static final String ACCESSTOKEN_TYPE_NORMAL = "normal";
    public static final String ACCESSTOLEN_TYPE_CONTACTS = "contacts";
    /**
     * 获取AccessToken的url
     */
    private final String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    /**
     * 创建部门的URL
     */
    private static final  String POST_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    /**
     * 删除部门的URl
     */
    private static final String GET_DELETE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";
    /**
     * 创建成员的URL
     */
    private static final String POST_CREAT_MEMBER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";
    /**
     * 删除成员的url
     */
    private static final String GET_DELETE_MEMBER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";
    /**
     * 发送消息的URL
     */
    private static final String POST_SEND_MESSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";


    @Value("${weixin.corpID}")
    private String corpID;
    @Value("${weixin.secret}")
    private String secret;
    @Value("${weixin.concat.secret}")
    private String contactsSecret;
    @Value("${weixin.app.agentid}")
    private String agentId;

    /**
     * 构建缓存对象,accessToken的缓存
     */
    private LoadingCache<String,String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    //根据传入的type判断获取AccessToken的类型，是普通的AccessToken还是通讯录的AccessToken
                    if(ACCESSTOLEN_TYPE_CONTACTS.equals(type)){
                        url = String.format(GET_ACCESS_TOKEN_URL,corpID,contactsSecret);
                    }else{
                        url = String.format(GET_ACCESS_TOKEN_URL,corpID,secret);
                    }
                    //json对象
                    String resultJson = sendHttpGetRequest(url);
                    //吧json转化成map集合
                    Map<String,Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)){
                        System.out.println("get accessToke from weixin");
                        return (String) map.get("access_token");
                    }
                    throw new WeiXinException((String) map.get("errmsg"));
                }
            });

    /**
     * 获取AccessToken
     * @param type 传入获取AccessToken的类型 normal 正常的，contacts通讯录的
     * @return
     */
    public String getAccessToken(String type){
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            throw new RuntimeException("获取AccessToken异常"+e);
        }
    }



    /**
     * 创建部门
     * @param id
     * @param parentId
     * @param name
     */
    public void creatDept(Integer id,Integer parentId,String name){
        String url = String.format(POST_DEPT_URL,getAccessToken(ACCESSTOLEN_TYPE_CONTACTS));
        Map<String,Object> date = new HashMap<String, Object>();
        date.put("name",name);
        date.put("parentid",parentId);
        date.put("id",id);
        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(date));
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if (!resultMap.get("errcode").equals(0)){
            throw new WeiXinException("创建部门失败"+resultJson);
        }

    }


    /**
     * 删除部门
     * @param id
     */
    public void deleteDept(Integer id){
        String url = String.format(GET_DELETE_DEPT_URL,getAccessToken(ACCESSTOLEN_TYPE_CONTACTS),id);
        String resultJson = sendHttpGetRequest(url);
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if (!resultMap.get("errcode").equals(0)){
            throw new WeiXinException("删除部门异常"+resultJson);
        }
    }


    /**
     * 创建成员账号
     * @param accountId 账号
     * @param name 名称
     * @param mobile 手机
     * @param departmentIdList 部门id
     */
    public void creatMember(Integer accountId, String name, String mobile, List<Integer> departmentIdList){
        String url = String.format(POST_CREAT_MEMBER_URL,getAccessToken(ACCESSTOLEN_TYPE_CONTACTS));
        /**
         * 创建JSON对象
         */
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("userid",accountId);
        data.put("name",name);
        data.put("mobile",mobile);
        data.put("department",departmentIdList);
        String requestJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> resultMap = JSON.parseObject(requestJson,HashMap.class);
        if(!resultMap.get("errcode").equals(0)){
            throw new WeiXinException("添加成员异常"+requestJson);
        }
    }

    /**
     * 删除成员
     * @param accountId
     */
    public void deleteMember(String accountId){
        String url = String.format(GET_DELETE_MEMBER_URL,getAccessToken(ACCESSTOLEN_TYPE_CONTACTS),accountId);
        String requestJson = sendHttpGetRequest(url);
        Map<String,Object> reultJson = JSON.parseObject(requestJson,HashMap.class);
        if(!reultJson.get("errcode").equals(0)){
            throw new WeiXinException("删除成员异常"+requestJson);
        }
    }


    /**
     * 发送消息给客户
     * @param userIdList 接受消息的用户id
     * @param message 文本消息（支持HTML）
     */
    public void sendTestMessage(List<Integer> userIdList,String message){
        String url = String.format(POST_SEND_MESSAGE,getAccessToken(ACCESSTOKEN_TYPE_NORMAL));

        StringBuilder stringBuilder = new StringBuilder();
        for(Integer userId : userIdList){
            stringBuilder.append(userId).append("|");
        }
        String idString = stringBuilder.toString();
        idString = idString.substring(0,idString.lastIndexOf("|"));


        Map<String,Object> data = new HashMap<String, Object>();
        data.put("touser",idString);
        data.put("msgtype","text");
        data.put("agentid",agentId);
        Map<String,String> messageMap = new HashMap<String, String>();
        messageMap.put("content",message);
        data.put("text",messageMap);

        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if(!resultMap.get("errcode").equals(0)){
            throw new WeiXinException("消息发送异常"+resultJson);
        }


    }


    /**
     * 发出http的get请求
     * @param url URL地址
     */
    private String sendHttpGetRequest(String url){
        OkHttpClient client = new OkHttpClient();
        //构建了一个request请求对象
        Request request = new Request.Builder().url(url).build();
        try {
            //执行请求对象,返回一个json对象
            Response response =  client.newCall(request).execute();
            //转换成字符串
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("http-Get请求异常");
        }
    }


    /**
     * 发出post请求
     * @param url 请求的url地址
     * @param json 请求体
     */
    public String sendHttpPostRequest(String url,String json) {
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
