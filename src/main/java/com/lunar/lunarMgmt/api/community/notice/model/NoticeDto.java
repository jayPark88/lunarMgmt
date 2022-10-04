package com.lunar.lunarMgmt.api.community.notice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.FileEntity;
import com.lunar.lunarMgmt.common.jpa.entities.NoticeEntity;
import com.lunar.lunarMgmt.common.model.FileDto;
import com.lunar.lunarMgmt.common.model.enums.NoticeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto implements ChangableToFromEntity<NoticeEntity> {

    private Long noticeSeq;
    private NoticeTypeEnum noticeType;
    private String title;
    private String content;
    private Character deleteYn;
    private List<FileDto> files;

    @JsonIgnore(value = true)
    private String createNm;

    @JsonIgnore(value = true)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;

    @JsonIgnore(value = true)
    private String modifyId;

    @JsonIgnore(value = true)
    private String modifyNm;

    @JsonIgnore(value = true)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDatetime;

    @Override
    public NoticeEntity to() {
        List<FileEntity> fileEntities = new ArrayList<FileEntity>();
        if (!ObjectUtils.isEmpty(files)) {
            for(FileDto file : files) {
                fileEntities.add(file.to());
            }
        }

        return NoticeEntity.builder()
                .noticeSeq(noticeSeq)
                .noticeType(noticeType)
                .title(title)
                .content(content)
                .deleteYn(deleteYn)
                .files(fileEntities)
                .createNm(createNm)
                .createDatetime(createDatetime)
                .modifyId(modifyId)
                .modifyNm(modifyNm)
                .modifyDatetime(modifyDatetime)
                .build();
    }

    @Override
    public void from(NoticeEntity entity) {
        List<FileDto> fileDtoList = new ArrayList<FileDto>();
        if (!ObjectUtils.isEmpty(entity.getFiles())) {
            for(int i = 0; i < entity.getFiles().size(); i ++) {
                FileDto fileDto = new FileDto();
                fileDto.from(entity.getFiles().get(i));
                fileDtoList.add(fileDto);
            }
        }

        this.noticeSeq = entity.getNoticeSeq();
        this.noticeType = entity.getNoticeType();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.deleteYn = entity.getDeleteYn();
        this.files = fileDtoList;
        this.createNm = entity.getCreateNm();
        this.modifyNm = entity.getModifyNm();
        this.modifyId = entity.getModifyId();
        this.modifyDatetime = entity.getModifyDatetime();
        this.createDatetime = entity.getCreateDatetime();
    }
}
