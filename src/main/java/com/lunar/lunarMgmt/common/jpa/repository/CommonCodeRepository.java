package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.pk.CommonCodePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCodeEntity, CommonCodePK> {
}
