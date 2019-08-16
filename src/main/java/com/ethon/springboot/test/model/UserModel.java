package com.ethon.springboot.test.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Data
public class UserModel {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private String uid;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "username")
    private String username;

    @UpdateTimestamp
    @Column(name = "upddate")
    private LocalDateTime uptDate;

    @CreationTimestamp
    @Column(name = "regdate")
    private LocalDateTime regDate;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<TalkModel> talks;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<SkinModel> skins;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private List<CommentModel> comments;
}
