package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.lang.String;

@Controller
public class ChooseController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*
        返回选课页面
     */
    @RequestMapping(value = "/xk")
    public String xk(Model model, HttpSession session){
        model.addAttribute("reflush", "yes");
        return "/html/xk";
    }

    /*
        检索所有课程并返回显示
     */
    @RequestMapping(value = "/courseQuery")
    public String courseQuery(Model model, HttpSession session) {
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        //获取所有课程
        String sql = "SELECT * FROM courseQuery";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        //检测是否已选过
        for(Map<String, Object> r : result)
        {
            sql = "SELECT * FROM coursechoice WHERE cid = " + r.get("cid") + " AND sid = " + uid;
            List<Map<String, Object>> l = jdbcTemplate.queryForList(sql);
            if(!l.isEmpty())
            {
                r.put("got", "已选");
            }
            else{
                r.put("got", "选择");
            }
        }
        model.addAttribute("courses", result);
        model.addAttribute("reflush", "no");
        return "/html/xk";
    }

    /*
        点击选择按钮触发
     */
    @RequestMapping(value = "/choose/{cid}", method = RequestMethod.GET)
    public String choose(Model model, HttpSession session, @PathVariable String cid) {
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        String sql = "INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (?,?,?)";
        try{
            jdbcTemplate.update(sql, new Object[]{cid, uid, null});
        }catch (Exception e){
            model.addAttribute("message", "选课失败");
        }

        return "redirect:/xk";
    }
    /*
        返回退课页面
     */
    @RequestMapping(value = "/yx")
    public String yx(Model model, HttpSession session){
        model.addAttribute("reflush", "yes");
        return "/html/yx";
    }
    /*
        查看已选课程
     */
    @RequestMapping(value = "/got", method = RequestMethod.GET)
    public String courseGot(Model model, HttpSession session){
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        //获取所有已选课程
        String sql = "SELECT * FROM courseQuery WHERE cid in (" +
                "SELECT cid FROM coursechoice WHERE sid = " + uid + ")";
        System.out.println(sql);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        model.addAttribute("courses", result);
        model.addAttribute("reflush", "no");
        return "/html/yx";
    }
    /*
        退课
     */
    @RequestMapping(value = "/tk/{cid}", method = RequestMethod.GET)
    public String tk(Model model, HttpSession session, @PathVariable String cid) {
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        String sql = "DELETE FROM courseChoice WHERE sid = ? AND cid = ?";
        try{
            jdbcTemplate.update(sql, new Object[]{uid, cid});
        }catch (Exception e){
            model.addAttribute("message", "退课失败");
        }
        return "redirect:/yx";
    }
}
