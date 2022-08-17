package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminAuthMenuRepository extends JpaRepository<AdminAuthMenuEntity, Long> {
    List<AdminAuthMenuEntity> findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(Long authSeq, Character useYn);
    List<AdminAuthMenuEntity> findAllByMenuMenuSeq(Long menuSeq);
    void deleteByAuthAuthSeq(Long authSeq);

    @Query(value = """
        SELECT
            *
        FROM
            ADMIN_AUTH_MENU AS aam 
        LEFT JOIN
            ADMIN_AUTH AS aa
                on aam.AUTH_SEQ = aa.AUTH_SEQ 
        LEFT JOIN
            ADMIN_MENU AS am
                on aam.MENU_SEQ = am.MENU_SEQ 
        WHERE
            aa.AUTH_SEQ = :auth
            AND 
            am.USE_YN = 'Y'
        GROUP BY 
            am.MENU_SEQ
        ORDER BY
            am.SORT_NUM ASC
          """, nativeQuery = true)
    List<AdminAuthMenuEntity> selectAllByAuth(@Param(value="auth") Long auth);
}
