package com.assetmaster.model.request;

import com.assetmaster.aop.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @NotBlank(message = Error.U_018)
    private String userID;

    @Size(min = 2, max = 50, message = Error.U_016)
    private String location;
}
