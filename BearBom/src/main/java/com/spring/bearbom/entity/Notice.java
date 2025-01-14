package com.spring.bearbom.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="T_NOTICE")
@Data
@DynamicInsert
@DynamicUpdate
public class Notice {

    @Id
    private int noticeIdx;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false, columnDefinition = "varchar(1000)")
    private String noticeContent;

    @Column(nullable = false)
    private LocalDateTime noticeRegdate = LocalDateTime.now().plusHours(9);

    @Column
    private LocalDateTime noticeMdfdate;

    @Column(nullable = false,columnDefinition = "char(1)")
    private char noticeUseYn = 'Y';

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;
	
}
