package com.lunar.lunarMgmt.common.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "COMMON_GRP_CD")
@AllArgsConstructor
@NoArgsConstructor
public class CommonGroupCodeEntity extends AdminBaseEntity {

    @Id
    @Column(name = "GRP_CD", nullable = false, columnDefinition = "varchar(30) comment '그룹 코드'")
    private String grpCd;

    @Column(name = "GRP_CD_NM", nullable = false, length = 100)
    private String grpCdNm;

    @Column(name = "GRP_CD_DESC", length = 255)
    private String grpCdDesc;

    @Column(name = "SORT_NUM")
    private int sortNum;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private Character useYn;

    @OneToMany(mappedBy = "commonCodePk.grpCd", fetch = FetchType.LAZY)
    private List<CommonCodeEntity> commonCodes;
}
