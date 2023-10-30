package com.assetmaster.repository;

import com.assetmaster.model.AssetHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistoryModel, Integer> {
    @Transactional
    void deleteByAssetID(String assetID);
}
