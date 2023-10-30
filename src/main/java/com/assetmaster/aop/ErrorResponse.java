package com.assetmaster.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private List<String> code;
    private List<String> messages;
}
