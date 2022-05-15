package com.bwgl.ztf.dao;

import com.bwgl.ztf.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao  extends CrudRepository<User,Integer> {
    User findByUsername(String username);
    User findByPhoneAndUsername(String phone,String username);
    User findByUidAndState(Integer uid,int state);
  User findByPhone(String phone);
  User findByUid(Integer uid);
}
