package com.jwzhu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.util.HttpUtil;
import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.model.Login;
import com.jwzhu.platform.core.admin.service.LoginService;
import com.jwzhu.platform.plugs.web.token.TokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TokenService tokenService;

    @Test
    public void testLoginInsert(){
        LoginBean bean = new LoginBean();
        bean.setUsername("admin");
        bean.setPassword("123456");
        loginService.insert(bean);
    }

    @Test
    public void testLoginGetByUsername(){
        Login login = loginService.getByUsername("admin");
        System.out.println(JSON.toJSONString(login));
    }

    @Test
    public void testRedis(){
        redisTemplate.boundValueOps("test_1").set("PribiEDZZV-QvE-TH-YmqX7Z52_cA8e-WJ16-s29M4TQGm33JCcInw7LgxbUSgtzgA3q6fbTQewWPoQ8w__Q1Q");
        redisTemplate.boundValueOps("test_2").set("PribiEDZZV-QvE-TH-YmqX7Z52_cA8e-WJ16-s29M4TQGm33JCcInw7LgxbUSgtzgA3q6fbTQewWPoQ8w__Q1Q");
        System.out.println("test_1 \t "+redisTemplate.boundValueOps("test_1").get());
        System.out.println("test_2 \t "+redisTemplate.boundValueOps("test_2").get());
    }

    public static void main(String[] args){
        HttpUtil.RequestBean bean = new HttpUtil.RequestBean();
        bean.setUrl("http://192.168.2.166/shop/merchant/comment/queryByParam");
        bean.addParam("actionType","1");
        bean.addParam("authkey","BAA53B00F7B3A4324BA830833E923AC7");
        bean.addParam("merchantId","31");
//        bean.addParam("content","评论3");
        System.out.println(HttpUtil.doGet(bean));
    }

}
