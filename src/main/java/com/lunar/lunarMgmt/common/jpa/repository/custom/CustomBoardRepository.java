package com.lunar.lunarMgmt.common.jpa.repository.custom;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.common.model.PageRequest;
import org.springframework.data.domain.Page;

public interface CustomBoardRepository {
    Page<BoardDto> searchBoardList(BoardSearchDto boardSearchDto, PageRequest pageRequest);
}
