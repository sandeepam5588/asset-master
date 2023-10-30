package com.assetmaster.controller;

import com.assetmaster.model.AssetTypeModel;
import com.assetmaster.model.LocationModel;
import com.assetmaster.model.request.*;
import com.assetmaster.model.response.AssetResponseModel;
import com.assetmaster.model.response.HistoryResponse;
import com.assetmaster.model.response.LoginResponse;
import com.assetmaster.service.AssetMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AssetMasterController {

    @Autowired
    private AssetMasterService assetMasterService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = assetMasterService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/create-account")
    public ResponseEntity<Map<String, String>> createAccount(@Valid @RequestBody CreateAccountRequest request){
        Map<String, String> response = assetMasterService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/asset-list")
    public ResponseEntity<List<AssetResponseModel>> listAssets(){
        List<AssetResponseModel> response = assetMasterService.listAsset();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/asset-type-list")
    public ResponseEntity<List<AssetTypeModel>> listAssetType(){
        List<AssetTypeModel> response = assetMasterService.listAssetType();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/add-asset")
    public ResponseEntity< Map<String, String>> addAsset(@Valid @RequestBody AssetRequestModel request){
        Map<String, String> response = assetMasterService.addAsset(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/add-asset-type")
    public ResponseEntity< Map<String, String>> addAssetType(@Valid @RequestBody AssetTypeRequestModel request){
        Map<String, String> response = assetMasterService.addAssetType(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-asset")
    public ResponseEntity< Map<String, String>> updateAsset(@Valid @RequestBody AssetRequestModel request){
        Map<String, String> response = assetMasterService.updateAsset(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-asset")
    public ResponseEntity< Map<String, String>> removeAsset(@Valid @RequestBody RemoveAssetRequest request){
        Map<String, String> response = assetMasterService.removeAsset(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/add-location")
    public ResponseEntity< Map<String, String>> addLocation(@Valid @RequestBody LocationRequest request){
        Map<String, String> response = assetMasterService.addLocation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/remove-location")
    public ResponseEntity< Map<String, String>> removeLocation(@Valid @RequestBody RemoveLocationRequest request){
        Map<String, String> response = assetMasterService.removeLocation(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/location-list")
    public ResponseEntity<List<LocationModel>> listAllLocations(){
        List<LocationModel> response = assetMasterService.listAllLocations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/get-asset-history/{assetID}")
    public ResponseEntity<List<HistoryResponse>> getHistory(@PathVariable("assetID") String assetID){
        List<HistoryResponse> response = assetMasterService.getAssetHistory(assetID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
