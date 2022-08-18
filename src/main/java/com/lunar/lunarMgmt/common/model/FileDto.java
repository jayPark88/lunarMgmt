package com.lunar.lunarMgmt.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.FileEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDto implements ChangableToFromEntity<FileEntity> {

    private Long fileSeq;

    // MultipartFile에서 뽑을 수 있음
    private String originFileNm;

    private String fileNm;

    private String filePath;

    private String fileUri;

    private String fileExtend;

    private Long fileSize;

    // 이 밑으로는 직접 채워야함
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;

    private String createId;

    private String createNm;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDatetime;

    private String modifyId;

    private String modifyNm;

    private Character deleteYn;

    @QueryProjection
    public FileDto(FileEntity entity) {
        if (!ObjectUtils.isEmpty(entity))
            from(entity);
    }

    @Override
    public FileEntity to() {
        return FileEntity.builder()
                .fileSeq(fileSeq)
                .originFileNm(originFileNm)
                .fileNm(fileNm)
                .filePath(filePath)
                .fileUri(fileUri)
                .fileExtend(fileExtend)
                .fileSize(fileSize)
                .deleteYn(deleteYn)

                .createDatetime(createDatetime)
                .createId(createId)
                .createNm(createNm)
                .modifyDatetime(modifyDatetime)
                .modifyId(modifyId)
                .modifyNm(modifyNm)

                .build();
    }

    @Override
    public void from(FileEntity entity) {
        this.fileSeq = entity.getFileSeq();
        this.originFileNm = entity.getOriginFileNm();
        this.fileNm = entity.getFileNm();
        this.filePath = entity.getFilePath();
        this.fileUri = entity.getFileUri();
        this.fileExtend = entity.getFileExtend();
        this.fileSize = entity.getFileSize();
        this.deleteYn = entity.getDeleteYn();

        this.createDatetime = entity.getCreateDatetime();
        this.createId = entity.getCreateId();
        this.createNm = entity.getCreateNm();
        this.modifyDatetime = entity.getModifyDatetime();
        this.modifyId = entity.getModifyId();
        this.modifyNm = entity.getModifyNm();
    }
}
