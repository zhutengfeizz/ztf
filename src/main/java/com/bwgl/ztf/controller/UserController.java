package com.bwgl.ztf.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.bwgl.ztf.entity.Post;
import com.bwgl.ztf.entity.User;
import com.bwgl.ztf.service.PostService;
import com.bwgl.ztf.service.UserService;
import com.bwgl.ztf.upload.UploadProperties;
import com.bwgl.ztf.util.AliyunSmsByUpdatePassWordUtils;
import com.bwgl.ztf.util.AliyunSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.bwgl.ztf.util.AliyunSmsUtils.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private UploadProperties uploadProperties;
    @Resource
    private ResourceLoader resourceLoader;
    @Autowired
    private PostService postService;

    /*我的主页*/
    @GetMapping("/myHome")
    public String myHome(Integer id, Model model) {
        User user = userService.finByid(id);
        model.addAttribute("user", user);
        return "user/myHome";
    }

    @GetMapping("/test")
    public String test(){
        return "user/test";
    }

    /*主页*/
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        List<Post> postList = postService.postAll();
        model.addAttribute("postList", postList);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            session.setAttribute("loginUser", userService.finByid(loginUser.getUid()));
        }

        return "user/index";
    }

    /*登录*/
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    /*注册*/
    @GetMapping("/register")
    public String register() {

        return "user/register";
    }

    /*登出*/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/index";
    }

    /*用户列表*/
    @GetMapping("/userList")
    public String List(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "admin/admin_list";
    }

    /*个人信息*/
    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        User user = userService.finByid(id);
        model.addAttribute("user", user);
        return "user/user_edit";
    }

    /*找回密码--修改密码*/
    @GetMapping("/upadatePassword")
    public String upadatePassword() {
        return "user/upadatePassword";
    }

    /*恢复用户*/
    @GetMapping("/updaterestore")
    private String restore(Integer id,RedirectAttributes redirectAttributes){
        User user = userService.finByid(id);
        user.setState(1);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message","恢复用户成功！");
        return "redirect:/user/userList";
    }
/*封禁用户*/
@GetMapping("/restore")
private String banUser(Integer id,RedirectAttributes redirectAttributes){
    User user = userService.finByid(id);
    user.setState(0);
    userService.save(user);
    redirectAttributes.addFlashAttribute("message","封禁用户成功！");
    return "redirect:/user/userList";
}
    /*登录*/
    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.printf("点击了登录");
        Map<String, Object> resultMap = userService.login(username, password);
        if ((Boolean) resultMap.get("ok")) {
            //把登录成功后的用户对象保存到session中
            User user = (User) resultMap.get("user");
            session.setAttribute("loginUser", user);
            System.out.println("头像是：" + user.getHeadshot());
            System.out.println(user.getUsername() + "password" + user.getPassword());
            return "redirect:/user/index";
        } else {
            model.addAttribute("error", resultMap.get("error"));
            return "user/login";
        }
    }

    /*注册的验证码*/
    @ResponseBody
    @RequestMapping(value = "/sendcode", method = RequestMethod.GET)
    public Map<String, Object> sendcode(String phone, HttpServletResponse response) throws ClientException {
        System.out.println("修改密码阿里云接口：手机号码为" + phone);
        Map<String, Object> map = new HashMap<>();
        AliyunSmsUtils.setNewcode();
        String code = Integer.toString(getNewcode());
        SendSmsResponse sendSmsResponse = sendSms(phone, code);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSmsResponse.getCode());
        System.out.println("Message=" + sendSmsResponse.getMessage());
        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
        System.out.println("BizId=" + sendSmsResponse.getBizId());
        map.put("Code", code);
        map.put("Message", sendSmsResponse.getMessage());
        System.out.println("发送的验证码为：" + code);
        //发短信
        return map;
    }

    /*更新与修改密码的手机验证码*/
    @ResponseBody
    @GetMapping("/sendcodeUpdatePassWord")
    public Map<String, Object> sendcodeUpdatePassWord(String phone, HttpServletResponse response) throws ClientException {
        System.out.println("修改密码阿里云接口：手机号码为" + phone);
        Map<String, Object> map = new HashMap<>();
        AliyunSmsByUpdatePassWordUtils.setNewcode();
        String code = Integer.toString(getNewcode());
        SendSmsResponse sendSmsResponse = sendSms(phone, code);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSmsResponse.getCode());
        System.out.println("Message=" + sendSmsResponse.getMessage());
        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
        System.out.println("BizId=" + sendSmsResponse.getBizId());
        map.put("Code", code);
        map.put("Message", sendSmsResponse.getMessage());
        System.out.println("发送的验证码为：" + code);
        //发短信
        return map;
    }
    /*保存*/
    @PostMapping("/save")
    public String sava(Integer id,RedirectAttributes redirectAttributes, String username,
                       String password, String password2, int sex,  String email,
                       Model model,@RequestParam("file") MultipartFile file,String phone,String signature)throws IOException{

        Map<String, Object> map =  userService.sava(id,redirectAttributes,username,password,password2,sex,email,model,file,phone,signature);
        if ((Boolean) map.get("ok")) {
            model.addAttribute("修改个人信息成功！");
            User user = (User) map.get("user");
            return "user/myHome";
        } else {
            model.addAttribute("error","修改失败！请重试！");
            return "user/myHome";
             }
          }



    /*注册*/
    @PostMapping("/register")
    public String register(String myCode, String phone, String code, String username, String password, String password2, Model model,HttpSession session) throws ClientException, InterruptedException {
        if (code != null) {
            if (!code.equals(myCode)) {
                model.addAttribute("error", "验证码不正确");
                return "user/register";
            } else {
                if (password.equals(password2)) {

                    Map<String, Object> map = userService.register(phone,username,password);
                    if ((Boolean) map.get("ok")) {
                        model.addAttribute("恭喜您注册成功！");
                        User user = (User) map.get("user");
                        System.out.printf("username:"+user.getUsername());
                        session.setAttribute("loginUser", user);
                        return "user/index";
                    } else {
                        model.addAttribute("error", map.get("error"));
                        return "user/register";
                    }
                } else {
                    System.out.println("密码不相同");
                    model.addAttribute("error", "亲亲！您输入的2次密码不相同哦！");
                    return "user/register";
                }
            }
        } else {
            model.addAttribute("error", "您还没输入验证码！");
            return "user/register";
        }
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(uploadProperties.getBasePath() + filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 上次头像方法
     * @param file
     * @return
     * @throws IOException
     */
    private String uploadHeadshot(MultipartFile file) throws IOException {
        // 获取头像存放路径
        String basePath = uploadProperties.getBasePath();
        // 判断文件夹是否存在，不存在则
        File folder = new File(basePath);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String filename = null;

        //如果上次的头像不为空
        if(!file.getOriginalFilename().equals("")){
            //获取文件的后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));;
            //新文件名
            filename = UUID.randomUUID().toString() + suffix;
            //新文件对象
            File saveFile = new File(folder + "/" + filename);
            //保存文件
            file.transferTo(saveFile);
            System.out.println("上传头像"+filename+"成功！");
        }

        return filename;
    }


}
