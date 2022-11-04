package com.lunar.lunarMgmt.common.jpa.repository.custom.impl;

import com.lunar.lunarMgmt.api.community.board.model.BoardDto;
import com.lunar.lunarMgmt.api.community.board.model.BoardSearchDto;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CustomBoardRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.enums.BoardTypeEnum;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static com.lunar.lunarMgmt.common.jpa.entities.QBoardEntity.boardEntity;

@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final EntityManager em;
    @Override
    public Page<BoardDto> searchBoardList(BoardSearchDto boardSearchDto, PageRequest pageRequest) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<BoardDto> boardList = queryFactory
                .select(
                        Projections.bean(
                                BoardDto.class,
                                boardEntity.boardSeq,
                                boardEntity.boardType,
                                boardEntity.title,
                                boardEntity.content

                        )).from(boardEntity)
                .where(setCondition(boardSearchDto))
                .orderBy(boardEntity.boardSeq.desc());

        long totalCount = boardList.fetchCount();

        List<BoardDto> pageList = boardList.offset(pageRequest.of().getOffset())
                .limit(pageRequest.getSize())
                .fetch();

        return new PageImpl<>(pageList, pageRequest.of(), totalCount);
    }

    BooleanBuilder setCondition(BoardSearchDto boardSearchDto){
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(!Objects.isNull(boardSearchDto.getBoardTypeEnum())){
            if(!boardSearchDto.getBoardTypeEnum().equals(BoardTypeEnum.ALL)){
                booleanBuilder.and(boardEntity.boardType.eq(boardSearchDto.getBoardTypeEnum()));
            }
        }

        if(!Objects.isNull(boardSearchDto.getTitle())){
            booleanBuilder.and(boardEntity.title.eq(boardSearchDto.getTitle()));
        }

        if(!Objects.isNull(boardSearchDto.getContent())){
            booleanBuilder.and(boardEntity.content.eq(boardSearchDto.getContent()));
        }

        return booleanBuilder;
    }
}
