package com.lunar.lunarMgmt.common.jpa.entities;

import com.lunar.lunarMgmt.common.model.enums.NoticeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="NOTICE")
public class NoticeEntity extends AdminBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_SEQ")
    private Long noticeSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOTICE_TYPE", columnDefinition = "varchar(30) not null comment '서비스 타입 코드'")
    @Setter
    private NoticeTypeEnum noticeType;

    @Column(name ="TITLE", columnDefinition = "varchar(100) not null comment '제목'")
    private String title;

    @Column(name ="CONTENT", columnDefinition = "mediumtext not null comment '본문 내용'")
    private String content;

    @Column(name = "DEL_YN", columnDefinition = "varchar(10) comment '게시물 삭제 여부'")
    @Setter
    private Character deleteYn;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "NOTICE_FILE",
            joinColumns = @JoinColumn(name = "NOTICE_SEQ"),
            inverseJoinColumns = @JoinColumn(name = "FILE_SEQ"))
    private List<FileEntity> files;

    public void setDeleteYn() {this.deleteYn = 'Y';}
}
