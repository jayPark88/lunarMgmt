package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminAuthMenuRepository extends JpaRepository<AdminAuthMenuEntity, Long> {
    List<AdminAuthMenuEntity> findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(Long authSeq, Character useYn);
}
