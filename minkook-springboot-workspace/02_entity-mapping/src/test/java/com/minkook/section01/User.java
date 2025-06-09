package com.minkook.section01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @Column
    private String userId;

    @Column
    private String userPwd;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private Date enrollDate;


}
