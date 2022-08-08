package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.AdminUserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository <AdminUserEntity, Long>, AdminUserCustomRepository {
  AdminUserEntity findByAdminUserId(String adminUserId);
}
