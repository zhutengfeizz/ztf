package com.bwgl.ztf.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "z_test")
@Entity
public class Test {
    @Id
    private int id;
}
