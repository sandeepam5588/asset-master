package com.assetmaster.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.assetmaster.aop.Error;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = Error.U_003)
    @Size(min = 6, max = 50, message = Error.U_004)
    private String userName;

    private String password;
}
