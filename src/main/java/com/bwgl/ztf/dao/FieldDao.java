package com.bwgl.ztf.dao;

import com.bwgl.ztf.entity.Field;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldDao extends CrudRepository<Field, Integer> {
    Field findByIdAndState(Integer fid,int state);
    List<Field> findByStateOrderByCreateTimeDesc(int state);
    List<Field>findByPartitionId(int id);


}
