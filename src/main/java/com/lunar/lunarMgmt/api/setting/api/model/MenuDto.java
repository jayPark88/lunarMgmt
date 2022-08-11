package com.lunar.lunarMgmt.api.setting.api.model;

import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.FileEntity;
import com.lunar.lunarMgmt.common.model.AdminBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
public class MenuDto extends AdminBaseDto implements ChangableToFromEntity<AdminMenuEntity> {
    private Long menuSeq;
    private String menuNm;
    private String pageUrl;
    private Integer menuLevel;
    private Long parentMenuId;
    private Long topMenuId;
    private String menuDesc;
    private Character useYn;
    private Integer sortNum;
    private Long onImageFileSeq;
    private String onImageUrl;
    private Long offImageFileSeq;
    private String offImageUrl;
    @Builder.Default // 기본값 설정을 위한 어노테이션이다. 값을 설정하면 된다. 아래는 Collection List로 초기화를 해주는 것임.
    private List<MenuDto> children = new ArrayList<>();
    private Character readYn;
    private Character writeYn;

    // NaviMenu를 위한 변수
    @Builder.Default
    private boolean active = false;

    public MenuDto() {
        children = new ArrayList<>();
    }

    public MenuDto(AdminMenuEntity entity) {
        this();
        from(entity);
    }

    public MenuDto(AdminAuthMenuEntity entity) {
        this();
        from(entity.getMenu());
        readYn = entity.getReadYn();
        writeYn = entity.getWriteYn();
    }

    @Override
    public AdminMenuEntity to() {
        return AdminMenuEntity.builder()
                .menuSeq(menuSeq)
                .menuNm(menuNm)
                .pageUrl(pageUrl)
                .menuLevel(menuLevel)
                .parentMenuId(parentMenuId)
                .topMenuId(topMenuId)
                .menuDesc(menuDesc)
                .useYn(useYn)
                .sortNum(sortNum)
                .onImageFile(onImageFileSeq != null ? FileEntity.builder().fileSeq(onImageFileSeq).build() : null)
                .offImageFile(offImageFileSeq != null ? FileEntity.builder().fileSeq(offImageFileSeq).build() : null)
                .createId(super.getCreateId())
                .createNm(super.getCreateNm())
                .createDatetime(super.getCreateDatetime())
                .modifyId(super.getModifyId())
                .modifyNm(super.getModifyNm())
                .modifyDatetime(super.getModifyDatetime())
                .build();
    }

    @Override
    public void from(AdminMenuEntity entity) {
        menuSeq = entity.getMenuSeq();
        menuNm = entity.getMenuNm();
        pageUrl = entity.getPageUrl();
        menuLevel = entity.getMenuLevel();
        parentMenuId = entity.getParentMenuId();
        topMenuId = entity.getTopMenuId();
        menuDesc = entity.getMenuDesc();
        useYn = entity.getUseYn();
        sortNum = entity.getSortNum();

        if (!ObjectUtils.isEmpty(entity.getOnImageFile())) {
            onImageFileSeq = entity.getOnImageFile().getFileSeq();
            onImageUrl = entity.getOnImageFile().getFileUri();
        }

        if (!ObjectUtils.isEmpty(entity.getOffImageFile())) {
            offImageFileSeq = entity.getOffImageFile().getFileSeq();
            offImageUrl = entity.getOffImageFile().getFileUri();
        }
        super.setCreateId(entity.getCreateId());
        super.setCreateNm(entity.getCreateNm());
        super.setCreateDatetime(entity.getCreateDatetime());
        super.setModifyId(entity.getModifyId());
        super.setModifyNm(entity.getModifyNm());
        super.setModifyDatetime(entity.getModifyDatetime());
    }
}