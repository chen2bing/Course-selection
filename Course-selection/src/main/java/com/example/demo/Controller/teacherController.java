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
    老师的相关响应
    包括：
    /teacher : 返回老师登录后的页面（teacher.html）

 */
@Controller
public class teacherController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
        返回老师登录之后的页面（teacher.html)
        功能尚未完善，可读取老师的名字
     */
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        return "html/teacher";
    }
}
