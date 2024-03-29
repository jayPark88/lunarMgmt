package com.lunar.lunarMgmt.api.community.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.api.community.board.service.NoticeService;
import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @SneakyThrows
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void saveNoticeInfo(@RequestPart("boardDto") String boardString,
                               @RequestPart(value="files", required = false)List<MultipartFile> files){
        ObjectMapper om = new ObjectMapper();
        BoardDto boardDto = om.readValue(new String(boardString.getBytes("8859_1"),"UTF-8"), BoardDto.class);
        noticeService.saveNotice(boardDto, files);
    }

    @GetMapping("/list")
    public PageResponse<BoardEntity, BoardDto> getList(@ModelAttribute BoardSearchDto boardSearchDto, @ModelAttribute PageRequest pageRequest){
        return noticeService.selectBoardList(boardSearchDto, pageRequest);
    }
}
