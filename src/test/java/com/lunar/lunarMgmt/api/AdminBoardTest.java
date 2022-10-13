package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.common.jpa.repository.BoardRepository;
import com.lunar.lunarMgmt.common.model.FileDto;
import com.lunar.lunarMgmt.common.model.enums.BoardTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = LunarMgmtApplication.class)
public class AdminBoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("notice create Test")
    public void noticeCreateTest() {
        //given
        BoardDto boardDto = this.makeBoardDto(BoardTypeEnum.NOTICE);

        //when
        boardRepository.save(boardDto.to());

        //then
        Assertions.assertAll(()-> assertTrue(boardRepository.findAll().stream().filter(item->item.getTitle().equals(BoardTypeEnum.NOTICE.getCodeName() + ": title")).findFirst().isPresent()));
    }


    private BoardDto makeBoardDto(BoardTypeEnum boardTypeEnum) {
        return BoardDto.builder()
                .boardSeq(0L)
                .boardType(boardTypeEnum)
                .title(boardTypeEnum.getCodeName() + ": title")
                .content(boardTypeEnum.getCodeName() + ": contents")
                .deleteYn('N')
                .files(this.makeFileDto())
                .createId("jayPark")
                .createNm("박지훈")
                .createDatetime(LocalDateTime.now())
                .modifyId("jayPark")
                .modifyNm("박지훈")
                .modifyDatetime(LocalDateTime.now())
                .build();
    }

    private List<FileDto> makeFileDto() {
        List<FileDto> files = new ArrayList<>();
        File file = new File("C:\\Users\\jaypa\\OneDrive\\사진\\Saved Pictures\\wallpaperbetter.com_1920x1080.jpg");
        Path path = Paths.get("C:\\Users\\jaypa\\OneDrive\\사진\\Saved Pictures\\wallpaperbetter.com_1920x1080.jpg");
        try {
            FileDto fileDto = FileDto.builder().fileSeq(0L).originFileNm(file.getName()).filePath(file.getPath())
                    .fileExtend(this.getExtendName(file.getName())).fileSize(Files.size(path)).createId("jayPark")
                    .createNm("박지훈")
                    .createDatetime(LocalDateTime.now())
                    .modifyId("jayPark")
                    .modifyNm("박지훈")
                    .modifyDatetime(LocalDateTime.now())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    private String getExtendName(String fileName) {
        String[] splitFileName = fileName.split("\\.");
        String extend = splitFileName[splitFileName.length - 1];
        return extend;
    }
}
