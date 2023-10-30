package com.assetmaster.service;

import com.assetmaster.aop.ValidationException;
import com.assetmaster.aop.Error;
import com.assetmaster.model.*;
import com.assetmaster.model.request.*;
import com.assetmaster.model.response.AssetResponseModel;
import com.assetmaster.model.response.HistoryResponse;
import com.assetmaster.model.response.LoginResponse;
import com.assetmaster.repository.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.uuid.Generators;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class AssetMasterServiceImpl implements AssetMasterService{

    @Value("${spring.mail.username}")
    public String SENDER_MAIL_ID;


    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final DatabaseManager databaseManager;
    private final LocationRepository locationRepository;
    private final AssetHistoryRepository assetHistoryRepository;
    private final AuditLogRepository auditLogRepository;
    private final EmailTemplateService emailTemplateService;
    private final Logger logger;

    public AssetMasterServiceImpl(JavaMailSender javaMailSender, UserRepository userRepository, AssetRepository assetRepository, AssetTypeRepository assetTypeRepository, DatabaseManager databaseManager, LocationRepository locationRepository, AssetHistoryRepository assetHistoryRepository, AuditLogRepository auditLogRepository, EmailTemplateService emailTemplateService, Logger logger) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.assetTypeRepository = assetTypeRepository;
        this.databaseManager = databaseManager;
        this.locationRepository = locationRepository;
        this.assetHistoryRepository = assetHistoryRepository;
        this.auditLogRepository = auditLogRepository;
        this.emailTemplateService = emailTemplateService;
        this.logger = logger;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(request.getUserName());
        if (!optionalUser.isPresent())
            throw new ValidationException(Error.U_002);

        UserModel user = optionalUser.get();
        AuditLogModel log = AuditLogModel.builder()
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(user.getEmail())
                .build();

        if (!user.getPassword().equals(request.getPassword())){
            log.setAction("Login failed");
            saveAuditLog(log);
            throw new ValidationException(Error.U_006);
        }

        log.setAction("Login successful");
        saveAuditLog(log);

        LoginResponse response = new LoginResponse();
        response.setUserID(user.getUserID());
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        return response;
    }

    @Override
    public Map<String, String> createAccount(CreateAccountRequest request) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent())
            throw new ValidationException(Error.U_007);

        String uid = null;
        Optional<UserModel> optionalUser2 = null;
        do {
            uid = generateUUID();
            optionalUser2 = userRepository.findById(uid);
        }while (optionalUser2.isPresent());
        logger.info("generated uid " + uid);

        UserModel user = new UserModel();
        user.setUserID(uid);
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreatedDate(request.getCreatedDate());
        user.setLastModified(request.getLastModified());
        userRepository.save(user);

        AuditLogModel log = AuditLogModel.builder()
                .action("User Created")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(user.getEmail())
                .build();
        saveAuditLog(log);

        Map<String, String> emailInput = new HashMap<>();
        emailInput.put("UserName", user.getUserName());
        sendEmail(new EmailRequest(request.getEmail(), "Account Created", emailTemplateService.getCreateAccountHtmlTemplate(emailInput)));

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "User created");
        return response;
    }

    @Override
    public Map<String, String> addAsset(AssetRequestModel request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);

        List<AssetModel> assetModels = assetRepository.findByAssetName(request.getAssetName());
        if (assetModels.size()>0)
            throw new ValidationException(Error.U_022);

        Optional<LocationModel> optionalLocationModel = locationRepository.findById(request.getLocationID());
        if (optionalLocationModel.isEmpty()){
            throw new ValidationException(Error.U_012);
        }
        LocationModel locationModel = optionalLocationModel.get();

        Optional<AssetTypeModel> optionalAssetTypeModel = assetTypeRepository.findById(request.getAssetTypeID());
        if (optionalAssetTypeModel.isEmpty()){
            throw new ValidationException(Error.U_013);
        }

        UserModel user = optionalUser.get();

        String uid = null;
        Optional<AssetModel> optionalAsset = null;
        do {
            uid = generateUUID();
            optionalAsset = assetRepository.findById(uid);
        }while (optionalAsset.isPresent());
        logger.info("generated uid " + uid);

        AssetModel model = new AssetModel();
        model.setAssetID(uid);
        model.setAssetTypeID(request.getAssetTypeID());
        model.setAssetName(request.getAssetName());
        model.setStatus(request.getStatus());
        model.setDescription(request.getDescription());
        model.setLocationID(request.getLocationID());
        model.setDateCreated(LocalDateTime.now());
        AssetModel savedAsset = assetRepository.save(model);

        AuditLogModel log = AuditLogModel.builder()
                .action("Asset Added")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(savedAsset.getAssetID())
                .build();
        saveAuditLog(log);

        AssetHistoryModel assetHistory = AssetHistoryModel.builder()
                .assetID(savedAsset.getAssetID())
                .fromLocationID("EXTERNAL")
                .toLocationID(savedAsset.getLocationID())
                .transactionDate(LocalDateTime.now())
                .updateStatus(savedAsset.getStatus())
                .description(savedAsset.getDescription())
                .userID(request.getUserID())
                .build();
        saveAssetHistory(assetHistory);

        Map<String, String> emailInput = new HashMap<>();
        emailInput.put("UserName", user.getUserName());
        emailInput.put("AssetID" , savedAsset.getAssetID());
        emailInput.put("AssetName", savedAsset.getAssetName());
        emailInput.put("CreatedDate", savedAsset.getDateCreated().toString().substring(0,16));
        emailInput.put("Location", locationModel.getLocation());
        emailInput.put("Status", savedAsset.getStatus());
        sendEmail(new EmailRequest(user.getEmail(), "Asset Added", emailTemplateService.getAddAssetHtmlTemplate(emailInput)));

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Asset created successfully");
        return response;
    }

    @Override
    public Map<String, String> updateAsset(AssetRequestModel request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);

        Optional<AssetModel> optionalAssetModel = assetRepository.findById(request.getAssetID());
        if (optionalAssetModel.isEmpty()){
            throw new ValidationException(Error.U_014);
        }
        Optional<LocationModel> optionalLocationModel = locationRepository.findById(request.getLocationID());
        if (optionalLocationModel.isEmpty()){
            throw new ValidationException(Error.U_012);
        }
        LocationModel locationModel = optionalLocationModel.get();

        Optional<AssetTypeModel> optionalAssetTypeModel = assetTypeRepository.findById(request.getAssetTypeID());
        if (optionalAssetTypeModel.isEmpty()){
            throw new ValidationException(Error.U_013);
        }
        UserModel user = optionalUser.get();

        AssetModel model = optionalAssetModel.get();
        String assetCurrentLocation = model.getLocationID();
        model.setAssetTypeID(request.getAssetTypeID());
        model.setAssetName(request.getAssetName());
        model.setStatus(request.getStatus());
        model.setDescription(request.getDescription());
        model.setLocationID(request.getLocationID());
        AssetModel savedAsset = assetRepository.save(model);

        AuditLogModel log = AuditLogModel.builder()
                .action("Asset Updated")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(savedAsset.getAssetID())
                .build();
        saveAuditLog(log);

        AssetHistoryModel assetHistory = AssetHistoryModel.builder()
                .assetID(savedAsset.getAssetID())
                .fromLocationID(assetCurrentLocation)
                .toLocationID(savedAsset.getLocationID())
                .transactionDate(LocalDateTime.now())
                .updateStatus(savedAsset.getStatus())
                .userID(request.getUserID())
                .description(savedAsset.getDescription())
                .build();
        saveAssetHistory(assetHistory);

        Map<String, String> emailInput = new HashMap<>();
        emailInput.put("UserName", user.getUserName());
        emailInput.put("AssetID" , savedAsset.getAssetID());
        emailInput.put("AssetName", savedAsset.getAssetName());
        emailInput.put("CreatedDate", savedAsset.getDateCreated().toString().substring(0,16));
        emailInput.put("UpdatedDate", LocalDateTime.now().toString().substring(0,16));
        emailInput.put("Location", locationModel.getLocation());
        emailInput.put("Status", savedAsset.getStatus());
        sendEmail(new EmailRequest(user.getEmail(), "Asset Updated", emailTemplateService.getUpdateAssetHtmlTemplate(emailInput)));

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Asset Updated successfully");
        return response;
    }

    @Override
    public Map<String, String> removeAsset(RemoveAssetRequest request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);

        Optional<AssetModel> optionalAssetModel = assetRepository.findById(request.getAssetID());
        if (optionalAssetModel.isEmpty()){
            throw new ValidationException(Error.U_014);
        }
        UserModel user = optionalUser.get();
        AssetModel asset = optionalAssetModel.get();
        Map<String, String> emailInput = new HashMap<>();
        emailInput.put("UserName", user.getUserName());
        emailInput.put("AssetID" , asset.getAssetID());
        emailInput.put("AssetName", asset.getAssetName());
        emailInput.put("CreatedDate", asset.getDateCreated().toString());
        emailInput.put("RemovedDate", LocalDateTime.now().toString().substring(0,16));
        emailInput.put("Status", "REMOVED");
        sendEmail(new EmailRequest(user.getEmail(), "Asset Removed", emailTemplateService.getRemoveAssetHtmlTemplate(emailInput)));

        assetRepository.deleteById(request.getAssetID());
        assetHistoryRepository.deleteByAssetID(request.getAssetID());
        auditLogRepository.deleteByActor(request.getAssetID());


        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Asset Deleted successfully");
        return response;
    }

    @Override
    public List<AssetResponseModel> listAsset() {
        return databaseManager.fetchAssetsWithDetails();
    }

    @Override
    public List<AssetTypeModel> listAssetType() {
        return assetTypeRepository.findAll();
    }

    @Override
    public List<AssetModel> listAssetByAssetType(String assetTypeID) {
        return assetRepository.findAllByAssetTypeID(assetTypeID);
    }

    @Override
    public List<LocationModel> listAllLocations() {
        return databaseManager.fetchLocations();
    }

    @Override
    public Map<String, String> addLocation(LocationRequest request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);
        UserModel user = optionalUser.get();

        String uid = null;
        Optional<LocationModel> optionalLocation = null;
        do {
            uid = generateUUID();
            optionalLocation = locationRepository.findById(uid);
        }while (optionalLocation.isPresent());
        logger.info("generated uid " + uid);

        LocationModel locationModel = new LocationModel();
        locationModel.setLocationID(uid);
        locationModel.setLocation(request.getLocation());
        locationRepository.save(locationModel);

        AuditLogModel log = AuditLogModel.builder()
                .action("new location added")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(uid)
                .build();
        saveAuditLog(log);

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Location added");
        return response;
    }

    @Override
    public Map<String, String> removeLocation(RemoveLocationRequest request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);

        Optional<LocationModel> optionalLocation = locationRepository.findById(request.getLocationID());
        if (optionalLocation.isEmpty())
            throw new ValidationException(Error.U_021);

        UserModel user = optionalUser.get();
        locationRepository.deleteById(request.getLocationID());

        AuditLogModel log = AuditLogModel.builder()
                .action("location removed")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(optionalLocation.get().getLocation())
                .build();
        saveAuditLog(log);

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Location removed");
        return response;
    }

    @Override
    public Map<String, String> addAssetType(AssetTypeRequestModel request) {
        Optional<UserModel> optionalUser = userRepository.findById(request.getUserID());
        if (optionalUser.isEmpty())
            throw new ValidationException(Error.U_002);
        UserModel user = optionalUser.get();

        Optional<String> optionalAssetType = assetTypeRepository.findByAssetType(request.getAssetType());
        if (optionalAssetType.isPresent()){
            throw new ValidationException(Error.U_017);
        }

        String uid = null;
        Optional<AssetTypeModel> optionalAssetType2 = null;
        do {
            uid = generateUUID();
            optionalAssetType2 = assetTypeRepository.findById(uid);
        }while (optionalAssetType2.isPresent());
        logger.info("generated uid " + uid);

        AssetTypeModel model = new AssetTypeModel();
        model.setAssetTypeID(uid);
        model.setAssetType(request.getAssetType());
        assetTypeRepository.save(model);

        AuditLogModel log = AuditLogModel.builder()
                .action("new Asset Type added")
                .actor(user.getUserID())
                .auditDateTime(LocalDateTime.now())
                .info(uid)
                .build();
        saveAuditLog(log);

        Map<String, String> response = new HashMap<>();
        response.put("result" , "OK");
        response.put("message" , "Asset-type created successfully");
        return response;
    }

    @Override
    public void saveAssetHistory(AssetHistoryModel request) {
        assetHistoryRepository.save(request);
    }

    @Override
    public List<HistoryResponse> getAssetHistory(String assetID) {
        Optional<AssetModel> optionalAssetModel = assetRepository.findById(assetID);
        if (optionalAssetModel.isEmpty()){
            throw new ValidationException(Error.U_014);
        }
       return databaseManager.getAssetHistory(assetID);
    }

    @Override
    public void saveAuditLog(AuditLogModel request) {
        auditLogRepository.save(request);
    }

    @Override
    public void sendEmail(EmailRequest request) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            mimeMessage.setContent(request.getBody(), "text/html");
            helper.setTo(request.getRecipient());
            helper.setSubject(request.getSubject());
            helper.setFrom(SENDER_MAIL_ID,"Smart Asset Master");
            javaMailSender.send(mimeMessage);

            logger.info("Mail sent to User "+request.getRecipient());
            AuditLogModel log = AuditLogModel.builder()
                    .action("Email sent")
                    .actor(request.getRecipient())
                    .auditDateTime(LocalDateTime.now())
                    .info(request.getSubject())
                    .build();
            saveAuditLog(log);

        } catch (MessagingException e) {
            logger.info("MessagingException occurs "+e);
        } catch (Exception e) {
            logger.info("Exception occurs "+e);
            e.printStackTrace();
        }
    }

    private String generateUUID(){
        return Generators.timeBasedGenerator().generate().toString().substring(0,8);
    }

}
