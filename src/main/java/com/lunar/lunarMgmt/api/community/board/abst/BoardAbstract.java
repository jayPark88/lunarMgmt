package com.lunar.lunarMgmt.api.community.board.abst;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.common.config.yml.FileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import com.lunar.lunarMgmt.common.model.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class BoardAbstract {

    protected final FileConfig fileConfig;
    protected final BoardRepository boardRepository;
    protected final FileUtil fileUtil;

    // board 정보 save method
    protected abstract void boardInfoSave(BoardDto boardDto);

    // fileDto생성 method
    public List<FileDto> createFileDtoList(List<MultipartFile> files){
        List<FileDto> fileDtoList = new ArrayList<>();
        FileDto fileDto;
        if (!ObjectUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                fileDto = fileUtil.createFileDto(file, fileConfig.getUploadPath());
                fileDtoList.add(fileDto);
            }
        }
        return fileDtoList;
    }
}
