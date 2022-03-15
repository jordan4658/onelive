package com.onelive.api.modules.demo.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Sets;
import com.onelive.api.util.ApiBusinessRedisUtils;

@Controller
public class TestController {

    private String ws = "ws://127.0.0.1:11111/ws";

    /**
     * 登录页面
     */
    @RequestMapping("/login")
    public String login() {
    	ApiBusinessRedisUtils.zSet("maomaotest", "asd1212", 99);
    	ApiBusinessRedisUtils.zSet("maomaotest", "vdsgv4", System.currentTimeMillis() / 1000);
    	ApiBusinessRedisUtils.zSet("maomaotest", "88888", 22);
    	ApiBusinessRedisUtils.zSet("maomaotest", "bdfg234", 4);
    	ApiBusinessRedisUtils.zSet("maomaotest", "121123231", 65);
    	ApiBusinessRedisUtils.zSet("maomaotest", "bytyyyy", 2);
    	
    	LinkedHashSet<String> rangeZset = ApiBusinessRedisUtils.reverseRangeZset("maomaotest", 0, -1);
    	System.out.println(rangeZset);
    	HashSet hashSet = new HashSet();
    	hashSet.add("88888");
    	hashSet.add("121123231");
    	hashSet.add("bytyyyy");
    	hashSet.add("3124a");
    	hashSet.add("dasd121");
    	Sets.SetView<String> intersection = Sets.intersection(rangeZset, hashSet);
    	System.out.println(intersection);
        return "test/login";
    }

    /**
     * 登录后跳转到测试主页
     */
    @PostMapping("/login.do")
    public String login(@RequestParam Integer userId, HttpSession session, Model model) {
    	
    	
        model.addAttribute("ws", ws);
        session.setAttribute("userId", userId);
        session.setAttribute("chatId", 88);
        session.setMaxInactiveInterval(30);
        model.addAttribute("groupList", Arrays.asList(1,2,3,4));
        return "test/index";
    }
}
