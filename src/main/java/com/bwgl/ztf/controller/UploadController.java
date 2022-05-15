package com.bwgl.ztf.controller;

import com.bwgl.ztf.entity.User;
import com.bwgl.ztf.service.UserService;
import com.bwgl.ztf.upload.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h3>monster</h3>
 * <p>${description}</p>
 *
 * @author : ztf
 * @date : 2019-07-01 14:50
 **/
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private UploadProperties uploadProperties;
    @Autowired
    private UserService userServices;
    @Resource
    private ResourceLoader resourceLoader;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> addImg(@RequestParam("file") MultipartFile file)throws IOException{
        String imgName = "/user/"+uploadHeadshot(file);
        System.out.println("上传的图片是："+imgName);
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> src=new HashMap<>();
        src.put("src",""+imgName);
        map.put("code",0);
        map.put("msg","ok");
        map.put("data",src);
        System.out.println(map);
        return map;

    }
    @ResponseBody
    @PostMapping("/updateHead")
    public Map<String, Object> updateHead(@RequestParam("file") MultipartFile file, HttpSession session)throws IOException{
        String imgName = uploadHeadshot(file);
        User user = (User) session.getAttribute("loginUser");
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> src=new HashMap<>();
        User user1 = userServices.updateheadshot(user.getUid(), imgName);
        System.out.println(user1);
        src.put("src",""+imgName);
        map.put("code",0);
        map.put("msg","ok");
        map.put("data",src);
        return map;
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


    /**
     * 获取头像
     * @param filename
     * @return
     */
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(uploadProperties.getBasePath() + filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
