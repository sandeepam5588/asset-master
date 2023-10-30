package com.assetmaster.repository;

import com.assetmaster.model.AssetTypeModel;
import com.assetmaster.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetTypeModel, String> {
    @Query("SELECT a.assetType FROM AssetTypeModel a WHERE LOWER(a.assetType) = LOWER(:assetType)")
    Optional<String> findByAssetType(@Param("assetType") String assetType);
}
