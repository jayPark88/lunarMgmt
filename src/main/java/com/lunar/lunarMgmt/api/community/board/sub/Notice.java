package com.lunar.lunarMgmt.api.community.board.sub;

import com.lunar.lunarMgmt.api.community.board.abst.BoardAbstract;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.common.config.yml.FileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import org.springframework.stereotype.Component;

@Component
public class Notice extends BoardAbstract {

    public Notice(FileConfig fileConfig, BoardRepository boardRepository, FileUtil fileUtil) {
        super(fileConfig, boardRepository, fileUtil);
    }

    @Override
    public void boardInfoSave(BoardDto boardDto) {
        boardRepository.save(boardDto.to());
    }
}
