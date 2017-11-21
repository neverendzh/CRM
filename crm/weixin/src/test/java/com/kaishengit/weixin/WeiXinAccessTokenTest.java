package com.kaishengit.weixin;

import com.kaishengit.WeiXinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zh
 * Created by Administrator on 2017/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WeiXinAccessTokenTest {
    @Autowired
    private WeiXinUtil weiXinUtil;

    /**
     * 测试获取AccessToken
     */
    @Test
    public void getAccessToken(){
        String accessToken = weiXinUtil.getAccessToken(weiXinUtil.ACCESSTOKEN_TYPE_NORMAL);
        weiXinUtil.getAccessToken(weiXinUtil.ACCESSTOKEN_TYPE_NORMAL);
        weiXinUtil.getAccessToken(weiXinUtil.ACCESSTOKEN_TYPE_NORMAL);
        weiXinUtil.getAccessToken(weiXinUtil.ACCESSTOKEN_TYPE_NORMAL);
        weiXinUtil.getAccessToken(weiXinUtil.ACCESSTOKEN_TYPE_NORMAL);
        System.out.println(accessToken);
    }


    /**
     * 添加部门
     */
    @Test
    public void creatDept(){
        weiXinUtil.creatDept(2,1,"人力资源");
    }

    /**
     *删除部门
     */
    @Test
    public void deleteDetp(){
        weiXinUtil.deleteDept(2);
    }

    /**
     *添加成员
     */
    @Test
    public void creatMember(){
        weiXinUtil.creatMember("001","猎手","17630705330", Arrays.asList(2));
    }

    /**
     * 删除成员
     */
    @Test
    public void deleteMember(){
        weiXinUtil.deleteMember("001");
    }

    /**
     * 微信信息发送测试
     */
    @Test
    public void sendMessage(){
        weiXinUtil.sendTestMessage(Arrays.asList(100,101,102),"hell,用于信息发送测试");
    }
}
