package com.minkook.section01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Test1 {

    @Id
    @Column
    private int testNo;

    @Column
    private String testName;

    @Column
    private Date testDate;
}
