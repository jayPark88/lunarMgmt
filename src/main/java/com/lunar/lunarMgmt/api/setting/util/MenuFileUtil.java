package com.lunar.lunarMgmt.api.setting.util;

import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.model.FileDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MenuFileUtil implements FileUtil {

    public static final String FILE_PREFIX = "yyyyMMddHHmmssSSS";

    @Override
    public void uploadFile(MultipartFile file, String uploadPath) throws IOException {
        File menuImageFile = new File(uploadPath);
        if(!menuImageFile.exists()){
            menuImageFile.mkdirs();
        }
        menuImageFile = new File(uploadPath);
        file.transferTo(menuImageFile);
    }

    @Override
    public FileDto createFileDto(MultipartFile file, String uploadPath) {
        String originFileName = file.getOriginalFilename();
        String fileExtend = getExtendName(originFileName);
        String fileNm = createFormattedFileName(originFileName);
        String filePath = String.format("%s", uploadPath);

        return FileDto.builder()
                .originFileNm(originFileName)
                .fileNm(fileNm)
                .filePath(filePath)
                .fileExtend(fileExtend)
                .fileSize(file.getSize())
                .build();
    }

    // 확장자 뽑아내기
    public String getExtendName(String fileName) {
        String[] splitFileName = fileName.split("\\.");
        String extend = splitFileName[splitFileName.length - 1];

        return extend;
    }

    // 파일 이름 포맷에 맞게 생성
    public String createFormattedFileName(String fileName) {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FILE_PREFIX)).toString();
        return String.format("%s%s", localDateTime, fileName);
    }
}
