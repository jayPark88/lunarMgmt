package com.lunar.lunarMgmt.common.jpa.entities;

import com.lunar.lunarMgmt.common.jpa.entities.pk.CommonCodePK;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

public class CommonCodeEntity {

    @EmbeddedId
    private CommonCodePK commonCodePk;

    @Column(name = "CD_NM", nullable = false, length = 100)
    private String cdNm;

    @Column(name = "CD_DESC")
    private String cdDesc;

    @Column(name = "CD_VAL1")
    private String cdVal1;

    @Column(name = "CD_VAL2")
    private String cdVal2;

    @Column(name = "CD_VAL3")
    private String cdVal3;

    @Column(name = "SORT_NUM")
    private int sortNum;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private Character useYn;

    @MapsId("grpCd")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRP_CD")
    private CommonGroupCodeEntity commonGroupCode;
}
