package com.bwgl.ztf.service;

import com.bwgl.ztf.entity.Field;

import java.util.List;

public interface FieldService {
    List<Field> partitionFieldAll();
    Field save(Field Field);
    void delete(Integer id);
    Field findById(Integer id);
    List<Field>findByPartitionId(int id);
    List<Field> findByStateOrderByCreateTimeDesc(int state);
    List<Field>findByNameLikeAndStateOrderByCreateTimeDesc(String name, int state);
    Field findByName(String name);
    Field findByNameLike(String name);
    Field findByIdAndState(Integer fid,int state);
}
