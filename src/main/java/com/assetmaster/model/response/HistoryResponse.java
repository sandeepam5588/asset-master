package com.assetmaster.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
    private int transactionID;
    private String assetName;
    private String assetType;
    private LocalDateTime transactionDate;
    private String fromLocationName;
    private String toLocationName;
    private String updateStatus;
    private String description;
    private String userName;
}
