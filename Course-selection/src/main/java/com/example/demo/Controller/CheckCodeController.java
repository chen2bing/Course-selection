package com.example.demo.Controller;

import com.example.demo.ImageCode;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/*
    响应：/checkcode
    功能：返回验证码图片
 */
@Controller
public class CheckCodeController {

    @RequestMapping(value = "/checkcode")
    public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream os = response.getOutputStream();
        //调用ImageCode()（详情见ImageCode.class）
        Map<String,Object> map = ImageCode.getImageCode(60, 30, os);
        //session中checkcode的值就是验证码的正确值
        String simpleCaptcha = "checkcode";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        //添加当前时间，防止验证码超时
        request.getSession().setAttribute("codeTime",new Date().getTime());
        try {
            //返回验证码图片
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            return "";
        }
        return null;
    }

}
