package com.bwgl.ztf.service;

import com.bwgl.ztf.entity.Post;
import com.bwgl.ztf.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> login (String username, String password);
    Map<String,Object> register(String phone,String username,String password);
    User save(User user);
    Map<String,Object> sava(Integer id, RedirectAttributes redirectAttributes, String username,
         String password, String password2, int sex, String email,
         Model model, @RequestParam("file") MultipartFile file, String phone, String signature);
    void delete(Integer id);
    User finByid(Integer id);
   List<User>findByDeleteAll();
    User  update(User user);
    List<User> findAll();
    User findByUsername(String username);
    void finalDelete(Integer id);
    User  updateheadshot(Integer uid,String headshot);
    List<User>findByUsernameLikeAndStateOrderByCreateTimeDesc(String username, int state);
    User findByUsernameLike(String username);
    User findByPhoneAndUsername(String phone,String username);
    User findByPhone(String phone);
    User updatePassword(String password);

    User updatePassword(String username, String password);

    Map<String, Object> updatePassword(String username, String password, Integer id,String phone);
    List<Post> myPost(Post post,User user);

}
