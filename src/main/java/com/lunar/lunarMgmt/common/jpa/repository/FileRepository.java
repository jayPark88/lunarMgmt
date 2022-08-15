package com.lunar.lunarMgmt.common.jpa.repository;

import com.lunar.lunarMgmt.common.jpa.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
