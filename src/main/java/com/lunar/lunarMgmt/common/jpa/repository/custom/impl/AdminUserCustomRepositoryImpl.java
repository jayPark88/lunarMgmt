package com.lunar.lunarMgmt.common.jpa.repository.custom.impl;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.AdminUserCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.lunar.lunarMgmt.common.jpa.entities.QAdminUserEntity.adminUserEntity;

@RequiredArgsConstructor
public class AdminUserCustomRepositoryImpl implements AdminUserCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<AdminUserDto> findByAdminUserList() {
        JPAQuery<AdminUserEntity> query  = new JPAQuery<>(entityManager);
        List<AdminUserEntity> adminUserList = query.from(adminUserEntity).fetch();
        return adminUserList.stream().map(AdminUserDto::new).collect(Collectors.toList());
    }
}
