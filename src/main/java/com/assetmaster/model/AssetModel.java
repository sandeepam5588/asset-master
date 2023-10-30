package com.assetmaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AM_ASSET")
public class AssetModel {
    @Id
    @Column(name = "AssetID", length = 8)
    private String assetID;

    @Column(name = "AssetName")
    private String assetName;

    @Column(name = "AssetTypeID", length = 8)
    private String assetTypeID;

    @Column(name = "DateCreated")
    private LocalDateTime dateCreated;;

    @Column(name = "Status")
    private String status;

    @Column(name = "LocationID", length = 8)
    private String locationID;

    @Column(name = "Description")
    private String description;
}
