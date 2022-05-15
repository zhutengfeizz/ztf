package com.bwgl.ztf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户
 */
@Table(name = "z_user")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(name = "username",length = 32,unique = true,nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @ColumnDefault("1")
    private int sex;
    private String phone;
    private String email;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Post> postList;
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date create_time;//创建时间
    private String signature;//个性签名
    @Column(insertable = false,columnDefinition = "int default 1")
    private int vip;//1为普通会员，2为vip,3为管理员
    private String headshot;//头像
    @Column(insertable = false,columnDefinition = "int default 1")
    private int state;//状态
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)

    private List<Collections> collectionsList;//收藏
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)

    private List<PrivateChat> privateChatList;//私信
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Follow> follow;//关注
    @Column(name = "post_size")
    private long postSize=0;//投稿数量

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return uid != null && Objects.equals(uid, user.uid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
