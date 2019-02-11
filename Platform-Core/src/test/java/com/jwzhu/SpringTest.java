package com.jwzhu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.web.base.token.TokenService;
import com.jwzhu.platform.web.base.token.TokenSubject;
import com.jwzhu.platform.core.user.bean.LoginBean;
import com.jwzhu.platform.core.user.model.Login;
import com.jwzhu.platform.core.user.service.LoginService;

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

    @Test
    public void testToken(){
        String token = tokenService.createToken(new TokenSubject(1,(short) 1));
        redisTemplate.boundValueOps("Test_Valid_Token_" + token).set(token, 200);
        System.out.println(redisTemplate.boundValueOps("Test_Valid_Token_" + token).get());
        System.out.println(JSON.toJSONString(tokenService.analyzeToken(token)));
    }

    public static void main(String[] args){

    }

}
