package com.minkook.section01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity(name = "test")
public class Test2 {
    @Id
    @Column(name = "test_no")
    private int testNo;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_date")
    private Date testDate;
}
