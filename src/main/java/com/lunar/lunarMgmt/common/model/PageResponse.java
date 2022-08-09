package com.lunar.lunarMgmt.common.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package : kr.co.wiseai.admin.common.dto
 * @FileName : PageResponse.java
 * @CreateDate : 2021. 7. 6.
 * @author : Morian
 * @param <E> : Entity
 * @param <D> : Dto
 * @Description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<E, D>  {

    private List<D> contents;
    private PageInfo pageInfo;
    /**
     * @param page
     * @param func : Entity -> Dto 변환 Function 작성
     * Page<Entity> -> PageResponse<Dto> 바로 변경하는 생성자
     */
    public PageResponse(Page<E> page, Function<E, D> func) {
        this(page.getContent().stream().map(func).collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }


    /**
     * @param page
     * @param contents
     */

    public PageResponse(Page<E> page, List<D> contents) {
        this(contents, page.getPageable(), page.getTotalElements());
    }


    /**
     * @param contents
     * @param pageable
     * @param total
     * Dto로 변경된 List를 직접 입력하여 PageResponse를 생성하는 생성자
     */
    public PageResponse(List<D> contents, Pageable pageable, long total) {
        this(new PageImpl<D>(contents, pageable, total));
    }


    /**
     * @param page
     * Page를 직접적으로 넣어서 생성하는 생성자
     */
    public PageResponse(Page<D> page) {
        this.contents = page.getContent();
        this.pageInfo = PageInfo.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }

}
