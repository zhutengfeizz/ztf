package com.bwgl.ztf.impl;


import com.bwgl.ztf.dao.PartitionDao;
import com.bwgl.ztf.entity.Partition;
import com.bwgl.ztf.service.PartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class PartitionServicesImpl implements PartitionService {
    @Autowired
    private PartitionDao partitionDao;

    @Override
    public List<Partition> partitionAll() {
       List<Partition>  partitions =partitionDao.findByStateOrderByCreateTimeDesc(1);
        return partitions;
    }

    @Override
    public List<Partition> findByStateOrderByCreateTimeDesc(int state) {
        List<Partition>  partitions =partitionDao.findByStateOrderByCreateTimeDesc(1);
        return partitions;
    }

    @Override
    public Partition save(Partition partition) {
        return partitionDao.save(partition);
    }

    @Override
    public Partition findById(Integer id) {
        return partitionDao.findById(id).get();
    }

}
