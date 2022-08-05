package com.lunar.lunarMgmt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<E, D> {

    private List<D> contents;
    private PageInfo pageInfo;

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
    public PageResponse(PageImpl<D> page) {
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
