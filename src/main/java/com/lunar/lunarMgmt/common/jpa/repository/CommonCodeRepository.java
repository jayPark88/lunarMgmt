package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.pk.CommonCodePK;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CommonCodeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCodeEntity, CommonCodePK>, CommonCodeCustomRepository {
    List<CommonCodeEntity> findAllByCommonCodePkGrpCdOrderBySortNum(String groupCode);
    CommonCodeEntity findByCommonCodePkCd(String cd);
    List<CommonCodeEntity> findByCommonCodePkGrpCdAndCommonCodePkCdNotAndSortNumGreaterThan(String grpCd, String cd,int sortNum);
    List<CommonCodeEntity> findByCommonCodePkGrpCdAndCommonCodePkCdNotAndSortNumBetween(String grpCd, String cd,int start, int end);
}
