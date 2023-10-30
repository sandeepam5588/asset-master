package com.assetmaster.aop;


import java.util.HashMap;
import java.util.Map;

public class Error {

    public static Map<String, String> messages;
    /* Server Errors */
    public static final String S_001 = "S_001";

    /* User login/register errors */
    public static final String U_002 = "U_002";
    public static final String U_003 = "U_003";
    public static final String U_004 = "U_004";
    public static final String U_005 = "U_005";
    public static final String U_006 = "U_006";
    public static final String U_007 = "U_007";
    public static final String U_008 = "U_008";
    public static final String U_009 = "U_009";
    public static final String U_010 = "U_010";
    public static final String U_011 = "U_011";
    public static final String U_012 = "U_012";
    public static final String U_013 = "U_013";
    public static final String U_014 = "U_014";
    public static final String U_015 = "U_015";
    public static final String U_016 = "U_016";
    public static final String U_017 = "U_017";
    public static final String U_018 = "U_018";
    public static final String U_019 = "U_019";
    public static final String U_020 = "U_020";
    public static final String U_021 = "U_021";
    public static final String U_022 = "U_022";

    static {
        messages = new HashMap<>();
        messages.put(S_001, "Internal Server Error");

        messages.put(U_002, "User does not exist");
        messages.put(U_003, "Email is not valid");
        messages.put(U_004, "Email must be greater than or equal to 6 characters and less than or equal to 50 characters");
        messages.put(U_005, "Password must be greater than or equal to 6 characters and less than or equal to 50 characters");
        messages.put(U_006, "Wrong Password");
        messages.put(U_007, "User with this emailID already exists");
        messages.put(U_008, "Asset Type cannot be empty");
        messages.put(U_009, "Status cannot be empty");
        messages.put(U_010, "Location cannot be empty");
        messages.put(U_011, "Description is exceeding the character limit");
        messages.put(U_012, "Location does not exist");
        messages.put(U_013, "AssetType does not exist");
        messages.put(U_014, "Asset not found");
        messages.put(U_015, "Asset Name should be in the rage of 2 to 200 characters");
        messages.put(U_016, "Location name length should be in the range of 2 to 50 characters");
        messages.put(U_017, "AssetType already exists");
        messages.put(U_018, "UserID attribute is required");
        messages.put(U_019, "AssetID attribute is required");
        messages.put(U_020, "AssetTypeID attribute is required");
        messages.put(U_021, "Location does not exist");
        messages.put(U_022, "The Asset with the given AssetName already exists. Use unique AssetNames");

    }
}
