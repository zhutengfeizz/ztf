package com.bwgl.ztf.service;

import com.bwgl.ztf.entity.Partition;

import java.util.List;

public interface PartitionService {
    List<Partition> partitionAll();
    List<Partition> findByStateOrderByCreateTimeDesc(int state);
    Partition save(Partition partition);
    Partition findById(Integer id);

}
