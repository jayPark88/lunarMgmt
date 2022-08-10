package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import static com.lunar.lunarMgmt.common.jpa.entities.QAdminUserEntity.adminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import org.springframework.data.domain.Page;

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
    public void queryDslTestCustomRepositoryPage(){
        // given
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(15);
        pageRequest.setSort("adminUserId");
        pageRequest.setDirection("asc");

        AdminUserListSearchDto adminUserListSearchDto = new AdminUserListSearchDto();
        adminUserListSearchDto.setAdminUserId("sub");
        adminUserListSearchDto.setAdminUserNm("지");
        adminUserListSearchDto.setDept("개발부서");
        adminUserListSearchDto.setPosition("책임");
        adminUserListSearchDto.setUseYn('Y');

        // when
        Page<AdminUserDto> adminUserDtoList = adminUserRepository.findByAdminUserPage(adminUserListSearchDto, pageRequest);

        // then
        Assertions.assertThat(adminUserDtoList.getContent()).hasSize(1);
    }

    @Test
    public void queryDslTestCustomRepositoryList(){
        // given
        List<AdminUserDto> adminUserDtoList = new ArrayList<AdminUserDto>();
        AdminUserListSearchDto adminUserListSearchDto = new AdminUserListSearchDto();
        adminUserListSearchDto.setAdminUserId("jay");

        // when
        adminUserDtoList = adminUserRepository.findByAdminUserList(adminUserListSearchDto);

        //then
        Assertions.assertThat(adminUserDtoList).hasSize(2);
    }
}
