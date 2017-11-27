package com.kaishengit.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author zh
 * Created by Administrator on 2017/11/24.
 * 测试shiro权限框架的使用
 */
public class ShiroTestCase {

    @Test
    public void helloShiro(){
//       1.创建SecurityManager工厂,传入ini文件，会自动读取文件中user的键值对。
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//       2.使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
//       3.使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
//        4.根据SecurityUtils创建Subject
        Subject subject = SecurityUtils.getSubject();
//        5.登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("tom","123123");
        try {
             subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        }catch (UnknownAccountException e){
            System.out.println("如果抛出该异常，则表示该账号不存在");
        }catch (LockedAccountException e){
            System.out.println("该账号被禁用");
        }catch (IncorrectCredentialsException e){
            System.out.println("账号或密码错误");
        }catch (AuthenticationException e){
            e.printStackTrace();
        }
//        6.安全退出
        subject.logout();



  }


    @Test
    public void shiroReameMy(){
//       1.创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
//       2.使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
//       3.使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
//        4.根据SecurityUtils创建Subject
        Subject subject = SecurityUtils.getSubject();
//        5.登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jak","123123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        }catch (UnknownAccountException e){
            System.out.println("如果抛出该异常，则表示该账号不存在");
        }catch (LockedAccountException e){
            System.out.println("该账号被禁用");
        }catch (IncorrectCredentialsException e){
            System.out.println("账号或密码错误");
        }catch (AuthenticationException e){
            e.printStackTrace();
        }
//        6.安全退出
        subject.logout();



    }
}

