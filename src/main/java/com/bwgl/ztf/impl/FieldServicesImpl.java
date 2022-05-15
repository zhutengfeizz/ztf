package com.bwgl.ztf.impl;

import com.bwgl.ztf.dao.FieldDao;
import com.bwgl.ztf.entity.Field;
import com.bwgl.ztf.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FieldServicesImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;

    @Override
    public List<Field> partitionFieldAll() {
        return fieldDao.findByStateOrderByCreateTimeDesc(1);
    }

    @Override
    public Field save(Field field) {
        return fieldDao.save(field);
    }

    @Override
    public void delete(Integer id) {
        Field field=fieldDao.findById(id).get();
        field.setState(0);
        fieldDao.save(field);

    }

    @Override
    public Field findById(Integer id) {
        return fieldDao.findById(id).get();
    }

    @Override
    public List<Field> findByPartitionId(int id) {
        return fieldDao.findByPartitionId(id);
    }

    @Override
    public List<Field> findByStateOrderByCreateTimeDesc(int state) {
        return fieldDao.findByStateOrderByCreateTimeDesc(1);
    }

    @Override
    public List<Field> findByNameLikeAndStateOrderByCreateTimeDesc(String name, int state) {
        return null;
    }

    @Override
    public Field findByName(String name) {
        return null;
    }

    @Override
    public Field findByNameLike(String name) {
        return null;
    }


    @Override
    public Field findByIdAndState(Integer fid, int state) {
        return fieldDao.findByIdAndState(fid,state);
    }


}
