package com.assetmaster.model.request;

import com.assetmaster.aop.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotBlank
    private String userName;

    @Email(message = Error.U_003)
    @Size(min = 6, max = 50, message = Error.U_004)
    private String email;

    private LocalDateTime createdDate = LocalDateTime.now();

    @Size(min = 6, max = 50, message = Error.U_005)
    private String password;

    private LocalDateTime lastModified = LocalDateTime.now();
}
