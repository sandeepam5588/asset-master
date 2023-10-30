package com.assetmaster.model.request;

import com.assetmaster.aop.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestModel {

    private String assetID;

    @NotNull(message = Error.U_018)
    private String userID;

    @Size(min = 2, max = 200, message = Error.U_008)
    private String assetName;

    @NotNull(message = Error.U_008)
    private String assetTypeID;

    @NotBlank(message = Error.U_009)
    private String status;

    @NotNull(message = Error.U_010)
    private String locationID;

    @Size(max = 255, message = Error.U_011)
    private String description;
}
