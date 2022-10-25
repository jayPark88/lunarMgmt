package com.lunar.lunarMgmt.api.community.board.service;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.sub.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final Notice notice;

    public void saveNotice(BoardDto boardDto, List<MultipartFile> files){
        boardDto.setFiles(notice.createFileDtoList(files));
        notice.boardInfoSave(boardDto);
    }
}
