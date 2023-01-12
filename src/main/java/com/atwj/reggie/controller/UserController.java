package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.User;
import com.atwj.reggie.service.UserService;
import com.atwj.reggie.utils.QQEmail;
import com.atwj.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: wj
 * @create_time: 2022/11/15 12:59
 * @explain:
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsgApi(@RequestBody User user, HttpServletRequest request) {
        if (StringUtils.isNotBlank(user.getPhone())) {
            //随机生成4位数字
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码:{}", code);
//            SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", user.getPhone(), "{\"code\":\"" + code + "\"}");
//            try {
//                //发送验证码到qq邮箱
//                QQEmail.sendMsg(code);
//            } catch (MessagingException e) {
//                System.out.println(e.getMessage());
//            }
            request.getSession().setAttribute("code", code);
            return R.success("验证码发送成功");
        }
        return R.error("发送失败");
    }


    @PostMapping("/login")
    public R<User> loginApi(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        String phone = (String) map.get("phone");
        String actualCode = (String) request.getSession().getAttribute("code");
        String inputCode = (String) map.get("code");
        if (actualCode != null && actualCode.equals(inputCode)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            request.getSession().setAttribute("loginUserId", user.getId());
            return R.success(user);
        }
        return R.error("登入失败");
    }

    @PostMapping("/logout")
    public R<String> logoutApi(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        return R.success("用户退出");
    }
}
