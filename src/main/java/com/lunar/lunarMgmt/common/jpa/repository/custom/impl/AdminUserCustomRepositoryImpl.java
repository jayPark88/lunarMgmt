package com.lunar.lunarMgmt.common.jpa.repository.custom.impl;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.AdminUserCustomRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.lunar.lunarMgmt.common.jpa.entities.QAdminUserEntity.adminUserEntity;
import static com.lunar.lunarMgmt.common.jpa.entities.QAdminAuthEntity.adminAuthEntity;

@RequiredArgsConstructor
public class AdminUserCustomRepositoryImpl implements AdminUserCustomRepository {
    private final EntityManager entityManager;

    @Override
    public List<AdminUserDto> findByAdminUserList(AdminUserListSearchDto searchDto) {
        JPAQuery<AdminUserEntity> query  = new JPAQuery<>(entityManager);
        List<AdminUserEntity> adminUserList = query.from(adminUserEntity).fetch();
        return adminUserList.stream().map(AdminUserDto::new).collect(Collectors.toList());
    }

    @Override
    public Page<AdminUserDto> findByAdminUserPage(AdminUserListSearchDto searchDto, PageRequest pageRequest) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QueryResults<AdminUserDto> pageList = queryFactory
                .select(Projections.bean(AdminUserDto.class,
                        adminUserEntity.adminUserSeq
                        , adminUserEntity.adminUserId
                        , adminUserEntity.adminUserNm
                        , adminUserEntity.dept
                        , adminUserEntity.position
                        , adminAuthEntity.authNm
                        , adminUserEntity.deleteYn
                        , adminUserEntity.createDatetime
                ))
                .from(adminUserEntity)
                .leftJoin(adminAuthEntity).on(adminUserEntity.auth.authSeq.eq(adminAuthEntity.authSeq))
                .where(setCondition(searchDto))
                .offset(pageRequest.of().getOffset())
                .limit(pageRequest.getSize())
                .fetchResults();

        return new PageImpl<AdminUserDto>(pageList.getResults(), pageRequest.of(), pageList.getTotal());
    }

    // where절에 있는 조건 check method
    private BooleanBuilder setCondition(AdminUserListSearchDto searchDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(searchDto.getSearchWord())){
            if(!ObjectUtils.isEmpty(adminUserEntity.adminUserId.contains(searchDto.getSearchWord()))){
                booleanBuilder.and(adminUserEntity.adminUserId.contains(searchDto.getSearchWord()));
            }else{
                booleanBuilder.and(adminUserEntity.adminUserNm.contains(searchDto.getSearchWord()));
            }
        }
        if (StringUtils.hasText(searchDto.getAdminUserId())) {
            booleanBuilder.and(adminUserEntity.adminUserId.contains(searchDto.getAdminUserId()));
        }

        if (StringUtils.hasText(searchDto.getAdminUserNm())) {
            booleanBuilder.and(adminUserEntity.adminUserNm.contains(searchDto.getAdminUserNm()));
        }

        if (StringUtils.hasText(searchDto.getDept())) {
            booleanBuilder.and(adminUserEntity.dept.eq(searchDto.getDept()));
        }

        if (StringUtils.hasText(searchDto.getPosition())) {
            booleanBuilder.and(adminUserEntity.position.eq(searchDto.getPosition()));
        }

        if(!ObjectUtils.isEmpty(searchDto.getUseYn())){
            booleanBuilder.and(adminUserEntity.useYn.eq(searchDto.getUseYn()));
        }

        return booleanBuilder;
    }
}
