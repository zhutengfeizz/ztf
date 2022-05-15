package com.bwgl.ztf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "z_post")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="user_id")//作者
    private User user;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="partition_id")
    private Partition partition;//分类
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="field_id")
    private Field field;//分区
    private String type;//投稿类型 （原创or转载）
    private String content;//内容
    private String Introduction;//投稿简介
    private String img;//投稿封面
    @Column(insertable = false,columnDefinition = "int default 1")
    private int state;//状态
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> commentList; //评论的集合（一个帖子有多个评论）
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @ToString.Exclude
    private List<Collections> collectionsList;//收藏
    private Integer views;//访问量
    private Integer awesome;//收藏
    @Column(name = "cunt", nullable = false)
    private Long cunt=0L;//评论的数量
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
