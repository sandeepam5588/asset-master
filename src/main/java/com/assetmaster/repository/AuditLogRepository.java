package com.assetmaster.repository;

import com.assetmaster.model.AuditLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogModel, Integer> {
    @Transactional
    void deleteByActor(String assetID);
}
