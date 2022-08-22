package com.lunar.lunarMgmt.common.jpa.repository.custom.impl;

import com.lunar.lunarMgmt.api.setting.model.CommonCodeSearchDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CommonCodeCustomRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import static com.lunar.lunarMgmt.common.jpa.entities.QCommonCodeEntity.commonCodeEntity;

@RequiredArgsConstructor
public class CommonCodeCustomRepositoryImpl implements CommonCodeCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<CommonCodeEntity> searchGroupCodeList(CommonCodeSearchDto searchDto, PageRequest pageRequest) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QueryResults<CommonCodeEntity> pageList = queryFactory
                .select(Projections.bean(CommonCodeEntity.class,
                        commonCodeEntity.commonCodePk
                        , commonCodeEntity.commonGroupCode
                        , commonCodeEntity.cdNm
                        , commonCodeEntity.cdDesc
                        , commonCodeEntity.cdVal1
                        , commonCodeEntity.cdVal2
                        , commonCodeEntity.cdVal3
                        , commonCodeEntity.sortNum
                        , commonCodeEntity.useYn
                ))
                .from(commonCodeEntity)
                .where(setCondition(searchDto))
                .offset(pageRequest.of().getOffset())
                .limit(pageRequest.getSize())
                .fetchResults();
        return new PageImpl<CommonCodeEntity>(pageList.getResults(), pageRequest.of(), pageList.getTotal());
    }

    private BooleanBuilder setCondition(CommonCodeSearchDto searchDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 그룹 코드
        if (StringUtils.hasText(searchDto.getGroupCode())) {
            booleanBuilder.and(commonCodeEntity.commonCodePk.grpCd.eq(searchDto.getGroupCode()));
        }

        // 공통코드
        if (StringUtils.hasText(searchDto.getCode())) {
            booleanBuilder.and(commonCodeEntity.commonCodePk.cd.eq(searchDto.getCode()));
        }

        // 코드 명
        if (StringUtils.hasText(searchDto.getCodeName())) {
            booleanBuilder.and(commonCodeEntity.cdNm.contains(searchDto.getCodeName()));
        }

        // 사용 여부
        if (!ObjectUtils.isEmpty(searchDto.getUseYn())) {
            booleanBuilder.and(commonCodeEntity.useYn.eq(searchDto.getUseYn()));
        }

        return booleanBuilder;
    }
}
