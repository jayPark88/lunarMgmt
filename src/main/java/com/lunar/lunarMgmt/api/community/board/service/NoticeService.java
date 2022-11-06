package com.lunar.lunarMgmt.api.community.board.service;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.api.community.board.sub.Notice;
import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final Notice notice;

    public void saveNotice(BoardDto boardDto, List<MultipartFile> files) throws IOException {
        boardDto.setFiles(notice.createFileDtoList(files));
        notice.boardInfoSave(boardDto);
    }

    public PageResponse<BoardEntity, BoardDto> selectBoardList(BoardSearchDto boardSearchDto, PageRequest pageRequest){
        return notice.selectBoardList(boardSearchDto, pageRequest);
    }
}
