package com.lunar.lunarMgmt.api.community.board.abst;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class BoardAbstract {

    protected final BoardRepository boardRepository;

    // board 정보 save method
    public abstract void boardInfoSave(BoardDto boardDto);
}
