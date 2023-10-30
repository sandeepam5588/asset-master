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
@Table(name = "AM_USER")
@SecondaryTables({
        @SecondaryTable(name = "AM_CREDENTIAL")
})
public class UserModel {
    @Id
    @Column(name = "UserID", length = 8)
    private String userID;

    @Column(name = "UserName", length = 50)
    private String userName;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(table = "AM_CREDENTIAL", name = "Password")
    private String password;

    @Column(table = "AM_CREDENTIAL", name = "LastModified")
    private LocalDateTime lastModified = LocalDateTime.now();
}
