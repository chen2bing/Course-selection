package com.example.demo.Controller;

import com.example.demo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/*
    登录界面相关响应
    包括：
    /login : 返回登录页面
    /who : 判断用户类型
    /logout : 退出系统
    /wanshan : 功能完善中，返回提示页面（wanshan.html）
    /register : 注册

 */
@Controller
public class LoginController {

    /*
        登录响应，如果是第一次登录，就调用第二个login（），
        如果登录失败，就调用第一个login（）并返回错误提示
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET, params = {"error"})
    public String login(Model model, HttpSession session) {
        //返回error，让login.html显示错误提示
        model.addAttribute("error", true);
        return "/html/login";
    }
    @RequestMapping(value = "/login")
    public String login(HttpSession session) {
        return "/html/login";
    }

    /*
        判断用户类型，分别对应学生（student）、老师（teacher）和教务老师（admin）
        然后产生相应的相应
     */
    @RequestMapping(value = "/who")
    public String hello(Authentication authentication, HttpSession session, Model model){
        String auth = authentication.getAuthorities().toArray()[0].toString();
        if (auth.equals("student")){
            return "redirect:/student";
        }else if (auth.equals("teacher")){
            return "redirect:/teacher";
        }else if (auth.equals("admin")){
            return "redirect:/admin/";
        }else {
            return Message.show("没有权限",model);
        }
    }

    /*
        退出系统的响应，直接返回到系统登录界面（login.html）
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        return "/html/login";
    }

    /*
        返回“功能正在完善中”的页面（wanshan.html）
     */
    @RequestMapping(value = "/wanshan")
    public String wanshan(HttpSession session) {
        return "/html/wanshan";
    }

    /*
        注册响应，尚未完善，直接返回“功能正在完善中”的页面（wanshan.html）
     */
    @RequestMapping(value = "/register")
    public String register(HttpSession session) {
        return "/html/wanshan";
    }
}
