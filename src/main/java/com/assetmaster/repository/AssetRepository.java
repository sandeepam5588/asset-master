package com.assetmaster.repository;

import com.assetmaster.model.AssetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<AssetModel, String> {

    List<AssetModel> findAllByAssetTypeID(String assetTypeID);

    @Query("SELECT e FROM AssetModel e WHERE LOWER(e.assetName) = LOWER(?1)")
    List<AssetModel> findByAssetName(String assetName);
}
