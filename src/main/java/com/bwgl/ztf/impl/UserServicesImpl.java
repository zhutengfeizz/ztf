package com.bwgl.ztf.impl;

import com.bwgl.ztf.dao.PostDao;
import com.bwgl.ztf.dao.UserDao;
import com.bwgl.ztf.entity.Post;
import com.bwgl.ztf.entity.User;
import com.bwgl.ztf.service.UserService;
import com.bwgl.ztf.upload.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServicesImpl implements UserService {
    @Resource
    private UploadProperties uploadProperties;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ok", false);
        User user;
        user = userDao.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                map.put("ok", true);
                map.put("user", user);
            } else {
                map.put("error", "密码错误");
            }

        } else {
            map.put("error", "用户名不存在！");
        }
        return map;

    }


    @Override
    public Map<String, Object> register(String phone,String username,String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ok", false);
        User user1 = userDao.findByUsername(username);
        if (user1 == null) {
            User user2 = new User();
            user2.setUsername(username);
            user2.setPassword(password);
            user2.setCreate_time(new Timestamp(System.currentTimeMillis()));
            user2.setPhone(phone);
            user2.setSignature("此人还没填写个性签名");
            //头像地址
            String headimg = "/user/" + "a4d244e8-550c-4875-908f-fea99cce8f54.png";
            user2.setHeadshot(headimg);
             user1 = userDao.save(user2);
            map.put("ok", true);
            map.put("user",user1);
        } else {
            map.put("error", "该用户名已被注册！");
        }

        return map;
    }

    /*上传头像*/
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

    @Override
    public User save(User user) {
        User user1 = userDao.save(user);
        return user1;
    }

    @Override
    public Map<String,Object> sava(Integer id, RedirectAttributes redirectAttributes, String username, String password, String password2, int sex, String email, Model model, MultipartFile file, String phone, String signature) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user=null;
        if (id==null){
            user=new User();
            user.setPassword(password);
            user.setUsername(username);
            user.setSignature("此人还没填写个性签名");
        }else {
            user=userDao.findByUid(id);

        }
        user.setSex(sex);
        user.setPhone(phone);
        user.setEmail(email);
        user.setSignature(signature);
        //上传头像
        String headshotFilename = null;
        try {
            headshotFilename = uploadHeadshot(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //头像名不为空，才更新头像的路径
        if(headshotFilename!=null) {
            user.setHeadshot("/user/"+headshotFilename);
            System.out.println("修改的图像为："+user.getHeadshot());

        }
        user = userDao.save(user);
        if (user.getUsername().equals(username)){
            map.put("ok",true);
            map.put("user",user);
        }else {
            map.put("ok",false);
        }
        return map;
    }

    @Override
    public void delete(Integer id) {
        User user = userDao.findById(id).get();
        user.setState(0);
        userDao.save(user);

    }

    @Override
    public User finByid(Integer id) {
        User user = userDao.findById(id).get();

        return user;
    }

    @Override
    public List<User> findByDeleteAll() {
        return null;
    }


    @Override
    public User update(User user) {
        User user1 = userDao.save(user);
        return user1;
    }

    @Override
    public List<User> findAll() {
        return null;
    }


    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }



    @Override
    public void finalDelete(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public User updateheadshot(Integer uid, String headshot) {
        User user = userDao.findById(uid).get();
        user.setHeadshot("/user/" + headshot);

        return userDao.save(user);
    }


    @Override
    public List<User> findByUsernameLikeAndStateOrderByCreateTimeDesc(String username, int state) {
        return null;
    }

    @Override
    public User findByUsernameLike(String username) {
        return null;
    }

    @Override
    public User findByPhoneAndUsername(String phone, String username) {

      User user=  userDao.findByPhoneAndUsername(phone, username);
        return user;

    }

    @Override
    public User findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public User updatePassword(String password) {
        return null;
    }

    @Override
    public User updatePassword(String username, String password) {
        return null;
    }
    /*更新密码找回密码*/
    @Override
    public Map<String, Object>  updatePassword(String username, String password, Integer id,String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ok", false);
        if (id == null){/*id为空就是找回密码*/
            User user1=userDao.findByUsername(username);
            User user2=userDao.findByPhone(phone);
            if (user1.getUsername().equals(user2.getUsername())){
                user1.setPassword(password);
                userDao.save(user1);
                map.put("ok",true);
            }else {
                map.put("error","用户名或手机号不正确！");
            }
        }else {
           User user3= userDao.findByUid(id);
            user3.setPassword(password);
            userDao.save(user3);
            map.put("ok",true);
        }


        return map;
    }
    /*我的帖子*/
    @Override
    public List<Post> myPost(Post post, User user) {
        return null;
    }


}