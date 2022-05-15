package com.bwgl.ztf.service;

import com.bwgl.ztf.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    List<Post> postAll();
//    Post save(Integer id,String name,String type, Integer field,String introduction,String content,Integer partition,Integer uid,String img);
    Post findById(Integer id);
    List<Post> findAll();
    List<Post>findByNameLikeAndStateOrderByCreateTimeDesc(String name,int state);
    Post findByNameLike(String name);

    /*分页无条件*/
    Page<Post> findPostNoCriteria(Integer page, Integer size);
    /*分页带条件*/
    Page<Post>findPostCriteria(Integer page, Integer size, Post post);

    List<Post>findAllByStateOrderByCreateTimeDesc(int state);
    Post findByName(String name);/*帖子名字*/
    List<Post>findByIdAndState(Integer id,int state);/*根据帖子ID*/
    List<Post>findByUserAndStateOrderByCreateTimeDesc(Integer uid,int state);/*根据用户ID*/
    List<Post>findByFieldAndStateOrderByCreateTimeDesc(Integer fid,int state);/*分区*/
    List<Post>findByPartitionAndStateOrderByCreateTimeDesc(Integer pid ,int state);/*分类*/
}
