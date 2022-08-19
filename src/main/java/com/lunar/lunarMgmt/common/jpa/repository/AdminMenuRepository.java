package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminMenuRepository extends JpaRepository<AdminMenuEntity, Long> {
    List<AdminMenuEntity> findAllByUseYn(Character useYn, Sort sort);

    List<AdminMenuEntity> findByParentMenuIdAndMenuSeqNotAndSortNumBetween(Long parentMenuId, Long menuSeq,int start, int end);
    @Query(value = """
          SELECT
              SORT_NUM
          FROM
              ADMIN_MENU am 
          WHERE
              am.PARENT_MENU_ID = :parentMeunId
          ORDER BY 
              am.SORT_NUM DESC
          LIMIT 1
          """, nativeQuery = true)
    int selectLastSortNumByParentMenuId(@Param("parentMeunId") Long parentMenuId);
}
