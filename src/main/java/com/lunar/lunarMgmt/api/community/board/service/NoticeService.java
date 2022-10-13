package com.lunar.lunarMgmt.api.community.board.service;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.sub.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final Notice notice;

    public void saveNotice(BoardDto boardDto){
        notice.boardInfoSave(boardDto);
    }
}
