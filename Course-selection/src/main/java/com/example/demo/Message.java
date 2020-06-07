package com.example.demo;
import org.springframework.ui.Model;

public class Message {
    /*
     * 提示警告消息
     *      @msg 消息内容
     *      @model 请求上下文的模型参数
     */
    static public String show(String msg, Model model){
        model.addAttribute("msg", msg);
        return "message";
    }
}
