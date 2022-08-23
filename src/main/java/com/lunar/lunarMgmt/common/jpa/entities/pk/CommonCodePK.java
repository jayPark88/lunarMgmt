package com.lunar.lunarMgmt.common.jpa.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodePK implements Serializable {

    // CommonCodePk를 세션에서 객체가 로드 될 때 키의 index를 id로 사용하기 위해서는 객체가 Serializable 상태여야 한다고 에러가 발생한다.
    // implements Serializable 안해주면 에러 남.
    @Column(name = "GRP_CD", nullable = false, length = 30, columnDefinition = "varchar(30) comment '그룹 코드'")
    private String grpCd;

    @Column(name = "CD", nullable = false, length = 30, columnDefinition = "varchar(30) comment '공통 코드'")
    private String cd;

}
