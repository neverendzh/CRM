package com.kaishengit.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.kaishengit.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zh
 * Created by Administrator on 2017/11/23.
 * 消费消息
 */
@Component
public class WeiXinConsumer {
    @Autowired
    private WeiXinUtil weiXinUtil;

    @JmsListener(destination = "weixinMessage-Queue")
    public void getMessageToUser(String json){
        Map<String,Object> map = JSON.parseObject(json, HashMap.class);
        System.out.println(">>>>>>>map.getMessage" + map.get("message").toString());
        weiXinUtil.sendTestMessage(Arrays.asList(1025,101,102),map.get("message").toString());

    }
}
