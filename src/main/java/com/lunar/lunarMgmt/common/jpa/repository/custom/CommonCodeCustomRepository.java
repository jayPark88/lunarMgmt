package com.lunar.lunarMgmt.common.jpa.repository.custom;

import com.lunar.lunarMgmt.api.setting.model.CommonCodeSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import org.springframework.data.domain.Page;

public interface CommonCodeCustomRepository {
    Page<CommonCodeEntity> searchGroupCodeList(CommonCodeSearchDto searchDto, PageRequest pageRequest);
}
