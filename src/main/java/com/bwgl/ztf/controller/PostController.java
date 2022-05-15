package com.bwgl.ztf.controller;

import com.bwgl.ztf.entity.Partition;
import com.bwgl.ztf.entity.Field;
import com.bwgl.ztf.entity.Post;
import com.bwgl.ztf.service.*;
import com.bwgl.ztf.upload.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
//    @Autowired
//    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private PartitionService partitionService;
    @Autowired
    private FieldService fieldService;
    @Resource
    private UploadProperties uploadProperties;
    @Autowired
    private UserService userServices;
    @Autowired
    private FollowService followServices;
//    @Autowired
//    private CollectionsService collectionsServices;

    @GetMapping("postList")
    public  String postList(Model model){
        List<Post> postList =postService.findAllByStateOrderByCreateTimeDesc(1);
        model.addAttribute("allpost",postList);
        return "admin/admin_list";
    }

    @GetMapping("save")
    public String save(Model model){
        Post post=new Post();
        model.addAttribute(post);
        //获取分区列表
        List<Partition> partitionList=partitionService.partitionAll();
        model.addAttribute("partitionList",partitionList);
        //分类
        List<Field> fieldList = fieldService.findByStateOrderByCreateTimeDesc(1);
        model.addAttribute("fieldList",fieldList);
        return "/post/post_add";
    }
//    @PostMapping("save")
//    public String save(Integer id, String name, String type, Integer field, String introduction, String content, Integer partition, Integer uid, String img){
//         postService.save(id,name,type,field,introduction,content,partition,uid,img);
//
//        return "redirect:/post/post_List";
//    }
}
