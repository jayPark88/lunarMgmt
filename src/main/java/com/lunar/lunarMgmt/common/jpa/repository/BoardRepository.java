package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.BoardEntity;
import com.lunar.lunarMgmt.common.jpa.repository.custom.CustomBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>, CustomBoardRepository {
}
