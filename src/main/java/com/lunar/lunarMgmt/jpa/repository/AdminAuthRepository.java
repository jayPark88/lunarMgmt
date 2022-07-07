package com.lunar.lunarMgmt.jpa.repository;

import com.lunar.lunarMgmt.jpa.entity.AdminAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminAuthRepository extends JpaRepository<AdminAuthEntity, Long> {
    @Query(value = """
          SELECT
            PAGE_URL
          FROM
            ADMIN_AUTH_MENU aam
          INNER JOIN
            ADMIN_MENU am
          ON
            aam.MENU_SEQ = am.MENU_SEQ
          INNER JOIN
            ADMIN_AUTH aa
          ON
            aam.AUTH_SEQ = aa.AUTH_SEQ
            AND
            aa.USE_YN = 'Y'
          WHERE
            aam.AUTH_SEQ = :auth
            AND
            (SELECT COUNT(*) FROM ADMIN_MENU WHERE PARENT_MENU_ID = aam.MENU_SEQ GROUP BY PARENT_MENU_ID) IS NULL /* 최종 뎁스만 봅아내기 위해 */
          GROUP BY
            PAGE_URL
          """, nativeQuery = true)
    List<String> selectMenuPageURListLByAuth(@Param("auth")Long auth);
}
