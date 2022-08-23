package com.lunar.lunarMgmt.common.jpa.repository;


import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CommonGroupCodeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonGroupCodeRepository extends JpaRepository<CommonGroupCodeEntity, String>, CommonGroupCodeCustomRepository {
}
