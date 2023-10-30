package com.assetmaster.model.request;

import com.assetmaster.aop.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveAssetRequest {
    @NotBlank(message = Error.U_018)
    private String userID;

    @NotBlank(message = Error.U_019)
    private String assetID;
}

