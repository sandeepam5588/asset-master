package com.assetmaster.service;

import com.assetmaster.model.*;
import com.assetmaster.model.request.*;
import com.assetmaster.model.response.AssetResponseModel;
import com.assetmaster.model.response.HistoryResponse;
import com.assetmaster.model.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AssetMasterService {
    LoginResponse login(LoginRequest request);
    Map<String, String> createAccount(CreateAccountRequest request);
    //String updateUserProfile(UserModel request);

    Map<String, String> addAsset(AssetRequestModel request);

    Map<String, String> updateAsset(AssetRequestModel request);
    Map<String, String> removeAsset(RemoveAssetRequest request);
    List<AssetResponseModel> listAsset();
    List<AssetTypeModel> listAssetType();
    List<AssetModel> listAssetByAssetType(String assetID);

    List<LocationModel> listAllLocations();
    Map<String, String> addLocation(LocationRequest request);
    Map<String, String> removeLocation(RemoveLocationRequest request);

    Map<String, String> addAssetType(AssetTypeRequestModel request);

    void saveAssetHistory(AssetHistoryModel request);
    List<HistoryResponse> getAssetHistory(String assetID);

    void saveAuditLog(AuditLogModel request);

    void sendEmail(EmailRequest request);

}
