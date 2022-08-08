package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import static com.lunar.lunarMgmt.common.jpa.entities.QAdminUserEntity.adminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LunarMgmtApplication.class)
@DisplayName("Admin User의 이름이 p를 포함한 데이터를 조회한다.")
class QueryDslTest {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void searchTest(){
        // given
        String adminUserId = "jayPark";

        // when
        AdminUserDto adminUserDto = new AdminUserDto(adminUserRepository.findByAdminUserId(adminUserId));

        // then
        Assertions.assertThat(adminUserDto.getAdminUserId()).isEqualTo(adminUserRepository.findByAdminUserId(adminUserId).getAdminUserId());
    }

    @Test
    public void queryDslTest(){
        //given
        String adminUserId = "jayPark";

        //when
        JPAQuery<AdminUserEntity> query  = new JPAQuery<>(em);

        List<AdminUserEntity> adminUserList = query.from(adminUserEntity)
                .where(adminUserEntity.adminUserId.contains("Park")).fetch();
        Assertions.assertThat(adminUserList).hasSize(2);
    }

    @Test
    public void queryDslTestCustomRepository(){
        // given
        List<AdminUserDto> adminUserDtoList = new ArrayList<AdminUserDto>();

        // when
        adminUserDtoList = adminUserRepository.findByAdminUserList();

        // then
        Assertions.assertThat(adminUserDtoList).hasSize(2);
    }
}
