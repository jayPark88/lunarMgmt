package com.lunar.lunarMgmt.api.community.board.abst;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.common.config.yml.FileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import com.lunar.lunarMgmt.common.model.FileDto;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    // board 목록 조회
    protected abstract PageResponse<BoardEntity,BoardDto> selectBoardList(BoardSearchDto boardSearchDto, PageRequest pageRequest);

    // fileDto생성 method
    public List<FileDto> createFileDtoList(List<MultipartFile> files) throws IOException {
        List<FileDto> fileDtoList = new ArrayList<>();
        FileDto fileDto;
        if (!ObjectUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                fileUtil.uploadFile(file, fileConfig.getUploadPath()+file.getOriginalFilename());
                fileDto = fileUtil.createFileDto(file, fileConfig.getUploadPath()+file.getOriginalFilename());
                fileDtoList.add(fileDto);
            }
        }
        return fileDtoList;
    }
}
