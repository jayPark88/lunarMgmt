package com.lunar.lunarMgmt.common.jpa.repository.custom;

import com.lunar.lunarMgmt.api.setting.model.GroupCodeSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;

import java.util.List;

public interface CommonGroupCodeCustomRepository {
    List<CommonGroupCodeEntity> searchGroupCodeList(GroupCodeSearchDto searchDto);
}
