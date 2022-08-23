package com.lunar.lunarMgmt.common.jpa.repository.custom.impl;

import com.lunar.lunarMgmt.api.setting.model.GroupCodeSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CommonGroupCodeCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.lunar.lunarMgmt.common.jpa.entities.QCommonGroupCodeEntity.commonGroupCodeEntity;

@RequiredArgsConstructor
public class CommonGroupCodeCustomRepositoryImpl implements CommonGroupCodeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CommonGroupCodeEntity> searchGroupCodeList(GroupCodeSearchDto searchDto) {
        JPAQuery<CommonGroupCodeEntity> query  = new JPAQuery<>(entityManager);
        List<CommonGroupCodeEntity> commonGroupCodeEntities = query.from(commonGroupCodeEntity).fetch();
        return commonGroupCodeEntities;
    }


    private BooleanBuilder setCondition(GroupCodeSearchDto searchDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 그룹 코드
        if(StringUtils.hasText(searchDto.getGroupCode())){
            booleanBuilder.and(commonGroupCodeEntity.grpCd.eq(searchDto.getGroupCode()));
        }

        // 그룹 코드 명
        if(StringUtils.hasText(searchDto.getGroupCodeName())){
            booleanBuilder.and(commonGroupCodeEntity.grpCdNm.contains(searchDto.getGroupCodeName()));
        }

        // 사용 여부
        if (!ObjectUtils.isEmpty(searchDto.getUseYn())) {
            booleanBuilder.and(commonGroupCodeEntity.useYn.eq(searchDto.getUseYn()));
        }

        return booleanBuilder;
    }


}
