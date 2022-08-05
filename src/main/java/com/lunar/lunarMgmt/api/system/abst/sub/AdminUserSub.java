package com.lunar.lunarMgmt.api.system.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.abst.AdminUserAbstract;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class AdminUserSub extends AdminUserAbstract {

  public AdminUserSub(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
    super(adminUserRepository, passwordEncoder);
  }

  @Override
  public void saveAdminUser(AdminUserDto adminUserDto) {
    AdminUserEntity adminUserEntity = new AdminUserEntity();
    if(this.checkAdminUserExist(adminUserDto)){
      adminUserEntity = adminUserRepository.findById(adminUserDto.getAdminUserSeq()).get();
    }

    // adminUserDto의 adminUserPwd의 값이 존재한다면(신규등록 절차에서만 해당 필드에 값이 set되서 data가 전송된다.)
    if (StringUtils.hasLength(adminUserDto.getAdminUserPwd())) {
      adminUserDto.setAdminUserPwd(passwordEncoder.encode(adminUserDto.getAdminUserPwd()));
    } else {
      // 그게 아니면 AdminUserDto의 adminUserPwd의 값을 adminUserEntity에서 조회 한 값을 set 해준다.
      adminUserDto.setAdminUserPwd(adminUserEntity.getAdminUserPwd());
    }

    // 해당 setting된 값을 save해준다.
    adminUserRepository.save(adminUserDto.to());
  }

  // checkAdminUser가 존재하는지 check
  private Boolean checkAdminUserExist(AdminUserDto adminUserDto){
    // 만약에 adminUserDto에 seq가 있다면(신규 등록이 아닌 수정 알고리즘으로 진행을 해야 한다.)
    if (!ObjectUtils.isEmpty(adminUserDto.getAdminUserSeq())) {
      // adminUserSeq로 db에서 조회를 한다.
      Optional<AdminUserEntity> optionalAdminUserEntity = adminUserRepository.findById(adminUserDto.getAdminUserSeq());
      if(optionalAdminUserEntity.isPresent()){
        // 조회 결과 값이 존재 하면 true
        return true;
      }else{
        // 조회 결과 값이 없으면 false
        return false;
      }
    }else{
      // adminUserDto의 adminUserSeq가 없다면 신규등록 알고리즘으로 retrun false
      return false;
    }
  }
}
