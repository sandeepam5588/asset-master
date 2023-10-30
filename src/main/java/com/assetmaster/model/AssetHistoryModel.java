package com.assetmaster.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "AM_ASSET_HISTORY")
public class AssetHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "AssetID", length = 8)
    private String assetID;

    @Column(name = "UserID", length = 8)
    private String userID;

    @Column(name = "TransactionDate")
    private LocalDateTime transactionDate;

    @Column(name = "FromLocationID", length = 8)
    private String fromLocationID;

    @Column(name = "ToLocationID", length = 8)
    private String toLocationID;

    @Column(name = "UpdateStatus")
    private String updateStatus;

    @Column(name = "Description")
    private String description;
}
