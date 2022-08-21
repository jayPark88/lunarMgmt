package com.lunar.lunarMgmt.common.jpa.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodePK {

    @Column(name = "GRP_CD", nullable = false, length = 30, columnDefinition = "varchar(30) comment '그룹 코드'")
    private String grpCd;

    @Column(name = "CD", nullable = false, length = 30, columnDefinition = "varchar(30) comment '공통 코드'")
    private String cd;

}
