package com.bwgl.ztf.dao;

import com.bwgl.ztf.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDao extends CrudRepository<Post,Integer>, JpaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {
//    Post save(Integer id,String name,String type, Integer field,String introduction,String content,Integer partition,Integer uid,String img);
    Optional<Post> findById(Integer id);
    Post findByNameLike(String name);
    List<Post>findAllByStateOrderByCreateTimeDesc(int state);
    Post findByName(String pname);/*帖子名字*/
    List<Post>findByIdAndState(Integer id,int state);/*根据帖子ID*/
    List<Post>findByUserAndStateOrderByCreateTimeDesc(Integer uid,int state);/*根据用户ID*/
    List<Post>findByFieldAndStateOrderByCreateTimeDesc(Integer fid,int state);/*分区*/
    List<Post>findByPartitionAndStateOrderByCreateTimeDesc(Integer pid ,int state);/*分类*/
    List<Post>findByStateOrderByCreateTimeDesc(int state);


}
