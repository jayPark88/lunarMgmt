package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuRepository extends JpaRepository<AdminMenuEntity, Long> {
    List<AdminMenuEntity> findAllByUseYn(Character useYn, Sort sort);;
}
