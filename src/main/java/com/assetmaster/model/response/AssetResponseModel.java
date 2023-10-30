package com.assetmaster.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseModel {
    private String assetID;
    private String assetName;
    private String assetTypeID;
    private LocalDateTime dateCreated;
    private String status;
    private String locationID;
    private String description;
    private String assetType;
    private String location;
}
