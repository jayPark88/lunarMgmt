package com.lunar.lunarMgmt.api.system.abst;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.intf.AdminbaseIdNmSetUtilInterface;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AdminUserAbstract {

  protected final AdminUserRepository adminUserRepository;
  protected final PasswordEncoder passwordEncoder;

  public AdminbaseIdNmSetUtilInterface adminbaseIdNmSetUtil;

  public void setAdminbaseIdNmSetUtilInterface (AdminbaseIdNmSetUtilInterface ai) {
    adminbaseIdNmSetUtil = ai;
  }

  // HQ-ADMIN user 등록 및 업데이트
  public abstract void saveAdminUser(AdminUserDto adminUserDto);
}
