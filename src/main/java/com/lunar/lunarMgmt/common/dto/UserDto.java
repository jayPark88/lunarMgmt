package com.lunar.lunarMgmt.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lunar.lunarMgmt.common.interfacing.ChangableToFromEntity;
import com.lunar.lunarMgmt.jpa.entity.AdminAuthEntity;
import com.lunar.lunarMgmt.jpa.entity.AdminUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements ChangableToFromEntity<AdminUserEntity> {

    private Long userSeq;

    private String userNm;

    private String userId;

    private String userPasswd;

    private String cellphoneNum;

    private String email;

    private String nickname;

    private Character useYn;

    private Character deleteYn;

    private String createId;

    private String createNm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;

    private String modifyId;

    private String modifyNm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDatetime;

    private Long authSeq;

    private String authNm;

    // front 용
    private String firstMenuUri;

    public UserDto(AdminUserEntity entity) {
        from(entity);
    }

    @Override
    public AdminUserEntity to() {
        return AdminUserEntity.builder()
                .userSeq(userSeq)
                .userId(userId)
                .userNm(userNm)
                .userPasswd(userPasswd)
                .cellphoneNum(cellphoneNum)
                .email(email)
                .nickname(nickname)
                .useYn(useYn)
                .deleteYn(deleteYn)
                .createId(createId)
                .createNm(createNm)
                .createDatetime(createDatetime)
                .modifyId(modifyId)
                .modifyNm(modifyNm)
                .modifyDatetime(modifyDatetime)
                .auth(authSeq != null ? AdminAuthEntity.builder().authSeq(authSeq).build() : null)
                .build();
    }

    @Override
    public void from(AdminUserEntity entity) {
        userSeq = entity.getUserSeq();
        userId = entity.getUserId();
        userNm = entity.getUserNm();
        userPasswd = entity.getUserPasswd();
        cellphoneNum = entity.getCellphoneNum();
        email = entity.getEmail();
        nickname = entity.getNickname();
        useYn = entity.getUseYn();
        deleteYn = entity.getDeleteYn();
        createId = entity.getCreateId();
        createNm = entity.getCreateNm();
        createDatetime = entity.getCreateDatetime();
        modifyId = entity.getModifyId();
        modifyNm = entity.getModifyNm();
        modifyDatetime = entity.getModifyDatetime();
        AdminAuthEntity aae = entity.getAuth();
        if (!ObjectUtils.isEmpty(aae)) {
            authSeq =  aae.getAuthSeq();
            authNm = aae.getAuthNm();
        }
    }

    public UserDto withoutPasswd() {
        this.userPasswd = null;
        return this;
    }
}
