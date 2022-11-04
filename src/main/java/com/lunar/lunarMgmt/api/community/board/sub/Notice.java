package com.lunar.lunarMgmt.api.community.board.sub;

import com.lunar.lunarMgmt.api.community.board.abst.BoardAbstract;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.common.config.yml.FileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Notice extends BoardAbstract {

    public Notice(FileConfig fileConfig, BoardRepository boardRepository, FileUtil fileUtil) {
        super(fileConfig, boardRepository, fileUtil);
    }

    @Override
    public void boardInfoSave(BoardDto boardDto) {
        boardRepository.save(boardDto.to());
    }

    @Override
    protected PageResponse<BoardEntity, BoardDto> selectBoardList(BoardSearchDto boardSearchDto, PageRequest pageRequest) {
        pageRequest.setSort("boardSeq");
        pageRequest.setDirection("desc");

        Page<BoardDto> list = boardRepository.searchBoardList(boardSearchDto, pageRequest);
        return new PageResponse<BoardEntity, BoardDto>(list);
    }
}
