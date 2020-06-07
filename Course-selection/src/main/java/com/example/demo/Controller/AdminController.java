package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

/*
*   教务老师（管理员）相关响应
*   包括：
*   /admin ： 返回教务老师界面
*
* */
@Controller
public class AdminController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*
        响应：/admin
        功能：返回教务老师登录系统之后的界面（admin.html）
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        return "html/admin";
    }
}
