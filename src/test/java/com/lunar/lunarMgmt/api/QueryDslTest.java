package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

@SpringBootTest(classes = LunarMgmtApplication.class)
@DisplayName("Admin User의 이름이 p를 포함한 데이터를 조회한다.")
class QueryDslTest {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void searchTest(){
        // given
        String adminUserId = "jayPark";

        // when
        AdminUserDto adminUserDto = new AdminUserDto(adminUserRepository.findByAdminUserId(adminUserId));

        // then
        Assertions.assertThat(adminUserDto.getAdminUserId()).isEqualTo(adminUserRepository.findByAdminUserId(adminUserId).getAdminUserId());
    }
}
