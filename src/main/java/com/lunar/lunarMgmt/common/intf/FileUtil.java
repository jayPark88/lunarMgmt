package com.lunar.lunarMgmt.common.intf;

import com.lunar.lunarMgmt.common.model.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUtil {
    public void uploadFile(MultipartFile file, String uploadPath) throws IOException;
    public FileDto createFileDto(MultipartFile file, String uploadPath);
}
