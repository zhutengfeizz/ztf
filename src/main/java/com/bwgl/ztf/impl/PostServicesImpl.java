package com.bwgl.ztf.impl;

import com.bwgl.ztf.dao.FieldDao;
import com.bwgl.ztf.dao.PostDao;
import com.bwgl.ztf.dao.PartitionDao;
import com.bwgl.ztf.dao.UserDao;
import com.bwgl.ztf.entity.Field;
import com.bwgl.ztf.entity.Post;
import com.bwgl.ztf.entity.Partition;
import com.bwgl.ztf.entity.User;
import com.bwgl.ztf.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PostServicesImpl implements PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private PartitionDao partitionDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Post> postAll() {
        List<Post> postList=postDao.findAllByStateOrderByCreateTimeDesc(1);
        return postList;
    }



//    @Override
//    public Post save(Integer id,String name, String type, Integer field, String introduction, String content, Integer partition, Integer uid, String img) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("ok", false);
//        Post p=null;
//        if (id!=null){
//            p=postDao.findByid(id);
//            p.setName(name);
//            p.setType(type);
//            p.setContent(content);
//            p.setImg(img);
//            p.setIntroduction(introduction);
//            Field field1 = fieldDao.findByIdAndState(field,1);
//            p.setField(field1);
//            Partition postPartition = partitionDao.findById(partition).get();
//            p.setPartition(postPartition);
//        }else {
//            p =new Post();
//            User user = userDao.findByUid(uid);
//            user.setPostSize(user.getPostSize()+1);
//            p.setImg(img);
//            p.setUser(user);
//            p.setName(name);
//            p.setType(type);
//            p.setCreateTime(new Timestamp(System.currentTimeMillis()));
//            p.setIntroduction(introduction);
//            p.setContent(content);
//            Field field1 = fieldDao.findByIdAndState(field,1);
//            p.setField(field1);
//            Partition postPartition = partitionDao.findById(partition).get();
//            p.setPartition(postPartition);
//            p.setViews(0);
//            p.setAwesome(0);
//            p.setCunt(0L);
//        }
//        return null;
//    }


//    @Override
//    public void delete(Integer id) {
//        Post post=postDao.findById(id).get();
//        post.setState(0);
//        postDao.save(post);
//    }

    @Override
    public Post findById(Integer id) {

        return postDao.findById(id).get();
    }




    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public List<Post> findByNameLikeAndStateOrderByCreateTimeDesc(String name, int state) {
        return null;
    }

    @Override
    public Post findByNameLike(String name) {
        return postDao.findByNameLike(name);
    }

    @Override
    public Page<Post> findPostNoCriteria(Integer page, Integer size) {
        return null;
    }
 /*无条件分页查询*/
    @Override
    public Page<Post> findPostCriteria(Integer page, Integer size, Post post) {
        return null;
    }

    @Override
    public List<Post> findAllByStateOrderByCreateTimeDesc(int state) {
        return null;
    }

    @Override
    public Post findByName(String name) {
        return null;
    }

    @Override
    public List<Post> findByIdAndState(Integer id, int state) {
        return postDao.findByIdAndState(id,1);
    }

    @Override
    public List<Post> findByUserAndStateOrderByCreateTimeDesc(Integer uid, int state) {
        return null;
    }

    @Override
    public List<Post> findByFieldAndStateOrderByCreateTimeDesc(Integer fid, int state) {
        return null;
    }

    @Override
    public List<Post> findByPartitionAndStateOrderByCreateTimeDesc(Integer pid, int state) {
        return null;
    }

}
