package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.api.community.board.sub.Notice;
import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import com.lunar.lunarMgmt.common.model.enums.BoardTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LunarMgmtApplication.class)
public class NoticeTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private Notice notice;

    @Test
    @DisplayName("공지사항 목록 조회 테스트")
    public void searchNoticeList(){
        // given
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(15);
        pageRequest.setSort("boardSeq");
        pageRequest.setDirection("desc");

        BoardSearchDto boardSearchDto = BoardSearchDto.builder()
                .title("공지사항: title1")
                .content("공지사항: contents1")
                .boardTypeEnum(BoardTypeEnum.NOTICE).build();

        // when
        PageResponse<BoardEntity, BoardDto> boardList = notice.selectBoardList(boardSearchDto, pageRequest);

        // then
       assertAll(
               () -> assertTrue(boardList.getContents().size()>0),
               () -> assertTrue(boardList.getContents().stream().filter(item -> item.getTitle().equals("공지사항: title1")).findFirst().isPresent()),
               () -> assertTrue(boardList.getContents().stream().filter(item -> item.getContent().equals("공지사항: contents1")).findFirst().isPresent()),
               () -> assertTrue(boardList.getContents().stream().filter(item -> item.getBoardType().equals(BoardTypeEnum.NOTICE)).findFirst().isPresent())
       );

    }
}
