package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository <AdminUserEntity, Long> {
  AdminUserEntity findByAdminUserId(String adminUserId);
}
