package com.bwgl.ztf.dao;

import com.bwgl.ztf.entity.Partition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartitionDao  extends CrudRepository<Partition,Integer> {
    List<Partition> findByStateOrderByCreateTimeDesc(int state);
    List<Partition>findByNameLikeAndStateOrderByCreateTimeDesc(String name, int state);
    Partition findByName(String tname);
    Partition findByNameLike(String tname);
}
