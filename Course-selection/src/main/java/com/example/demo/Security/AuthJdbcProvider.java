package com.example.demo.Security;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/*
    拦截未登录的请求
 */
@Service
public class AuthJdbcProvider implements AuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HttpServletRequest request;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //获取session
        HttpSession session = request.getSession();
        Long uid;
        Long group;
        try{
            //查询该账号的信息（包括密码和分组）
            Map user = jdbcTemplate.queryForMap("SELECT * FROM user WHERE uid = ?", new Object[]{username});
            //将密码转为哈希值（sha256）并进行检验
            String hashedPwd = new Sha256Hash(password).toHex();
            if(!hashedPwd.equals(user.get("password"))){
               throw new UsernameNotFoundException("Password error!");
            }
            //获取账号及分组
            uid = (Long)user.get("uid");
            group = (Long)user.get("group");
            //获取已存储的验证码正确值
            String checkCode = request.getParameter("checkCode");
            Object cko = session.getAttribute("checkcode") ; //验证码对象
            //session中没有验证码
            if(cko == null){
                throw new UsernameNotFoundException("验证码已失效，请重新输入");
            }
            //检测验证码是否正确及未过时
            String captcha = cko.toString();
            Date now = new Date();
            Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
            if(StringUtils.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
                throw new UsernameNotFoundException("验证码错误！");
            } else if ((now.getTime()-codeTime)/1000/60>5) {
                throw new UsernameNotFoundException("验证码已失效，请重新输入！");
            }else {
                //去除当前session中的验证码
                session.removeAttribute("checkcode");
            }
        } catch (EmptyResultDataAccessException e) {
            //用户不存在
            throw new UsernameNotFoundException("User not found!");
        }
        //向session中存储账号uid
        session.setAttribute("uid", uid);

        return new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(Group.getGroupName(group)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}