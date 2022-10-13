package com.lunar.lunarMgmt.api.community.board.sub;

import com.lunar.lunarMgmt.api.community.board.abst.BoardAbstract;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import org.springframework.stereotype.Component;

@Component
public class Notice extends BoardAbstract {

    public Notice(BoardRepository boardRepository) {
        super(boardRepository);
    }

    @Override
    public void boardInfoSave(BoardDto boardDto) {
        boardRepository.save(boardDto.to());
    }
}
