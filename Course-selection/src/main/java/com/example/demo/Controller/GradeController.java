package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/*
    查询成绩(cj.html)相关响应
    包括：
    /cj：返回成绩页面（cj.html）
    /scoreQuery：从数据库中查询成绩并返回

 */
@Controller
public class GradeController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*
        返回成绩页面
     */
    @RequestMapping(value = "/cj")
    public String cj(Model model, HttpSession session){
        //控制页面刷新
        model.addAttribute("reflush", "yes");
        return "/html/cj";
    }
    /*
        检索所有成绩并返回显示
     */
    @RequestMapping(value = "/scoreQuery")
    public String scoreQuery(Model model, HttpSession session) {
        //获取当前用户的uid（即学号）
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        //获取该学生所有已结课课程
        String sql = "SELECT * FROM scoreQuery WHERE sid = " + uid;
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        /*  计算绩点
            95-100 : 4.3
            90-94 : 4.0
            85-89 : 3.7
            82-84 : 3.3
            78-81 : 3.0
            70-77 : 2.0
            60-69 : 1.0
            0-59 : 0
         */
        for(Map<String, Object> r : result)
        {
            int score = Integer.parseInt(r.get("score").toString());
            double gpa = 0.0;
            if(score >= 95)
                gpa = 4.3;
            else if(score >=90)
                gpa = 4.0;
            else if(score >=85)
                gpa = 3.7;
            else if(score >=82)
                gpa = 3.3;
            else if(score >=78)
                gpa = 3.0;
            else if(score >=70)
                gpa = 2.0;
            else if(score >=60)
                gpa = 1.0;
            else
                gpa = 0;
            r.put("gpa", gpa);
        }
        //返回scores对象
        model.addAttribute("scores", result);
        //页面不再继续刷新
        model.addAttribute("reflush", "no");
        return "/html/cj";
    }
}
