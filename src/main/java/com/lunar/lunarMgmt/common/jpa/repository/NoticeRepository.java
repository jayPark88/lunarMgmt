package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
