package com.assetmaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AM_ASSET_TYPE")
public class AssetTypeModel {
    @Id
    @Column(name = "AssetTypeID", length = 8)
    private String assetTypeID;

    @Column(name = "AssetType")
    private String assetType;
}
