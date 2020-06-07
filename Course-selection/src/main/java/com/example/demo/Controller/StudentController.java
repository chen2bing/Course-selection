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
    学生的相关响应
    包括：
    /student : 返回学生登录后的页面（student.html)，以及学生名字

 */
@Controller
public class StudentController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
        查询学生的名字并返回
     */
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        //获取当前学生的学号
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        //查找名字
        String sql = "SELECT name FROM student WHERE sid = " + uid;
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        String name = result.get("name").toString();
        //向session中加入名字，以便后续读取
        session.setAttribute("name", name);
        //返回学生名字
        model.addAttribute("name", name);
        return "html/student";
    }
}
