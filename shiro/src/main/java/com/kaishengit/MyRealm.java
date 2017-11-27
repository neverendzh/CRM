package com.kaishengit;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author zh
 * Created by Administrator on 2017/11/24.
 * 这个类用于自定义类型的认证模式
 */
public class MyRealm implements Realm{
    /**
     * 获取的这个名字，必须在整个应用中不能重复
     * @return
     */
    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
//authenticationToken instanceof UsernamePasswordToken;表示这个方法中传过来的参数authenticationToken时UsernamePasswordToken类的对象返货true
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = new String(token.getPassword());
        if(!"jak".equals(userName)){
            throw new UnknownAccountException("账号不存在");
        }
        if(!"123123".equals(password)){
            throw new IncorrectCredentialsException("账号或密码错误");
        }
        return new SimpleAuthenticationInfo(userName,password,getName());
    }
}
