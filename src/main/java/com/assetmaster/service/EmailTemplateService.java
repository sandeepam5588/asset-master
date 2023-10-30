package com.assetmaster.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailTemplateService {

    public String getAddAssetHtmlTemplate(Map<String, String> input){
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        /* Define some basic styles for the email */\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin-bottom: 20px;\n" +
                "            color: #666;\n" +
                "        }\n" +
                "        .details {\n" +
                "            background-color: #f4f4f4;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .details p {\n" +
                "            margin: 0; /* Remove the default margin for paragraphs */\n" +
                "        }\n" +
                "        .details p span {\n" +
                "            display: inline-block;\n" +
                "            width: 120px; /* Fixed width for the colons */\n" +
                "            font-weight: bold;\n" +
                "            margin-right: 10px; /* Margin to separate the colon from the value */\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class='container'>\n" +
                "        <h1>New Asset Added</h1>\n" +
                "        <p>A new asset has been added to the Smart Asset Master application. Here are the details:</p>\n" +
                "        \n" +
                "        <div class='details'>\n" +
                "            <p><span>Created by:</span> AM_USER_NAME</p>\n" +
                "            <p><span>Asset ID:</span> AM_ASSET_ID</p>\n" +
                "            <p><span>Asset Name:</span> AM_ASSET_NAME</p>\n" +
                "            <p><span>Created Date:</span> AM_CREATED_DATE</p>\n" +
                "            <p><span>Location:</span> AM_LOCATION</p>\n" +
                "            <p><span>Status:</span> AM_STATUS</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Please review this information in the Smart Asset Master application for auditing purposes.</p>\n" +
                "\n" +
                "        <p>Thank you,</p>\n" +
                "        <p>Your Smart Asset Master System</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

        html = html.replace("AM_USER_NAME", input.get("UserName"))
                .replace("AM_ASSET_ID" , input.get("AssetID"))
                .replace("AM_ASSET_NAME" , input.get("AssetName"))
                .replace("AM_CREATED_DATE" , input.get("CreatedDate"))
                .replace("AM_LOCATION" , input.get("Location"))
                .replace("AM_STATUS" , input.get("Status"));
        return html;
    }

    public String getUpdateAssetHtmlTemplate(Map<String, String> input){
        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        /* Define some basic styles for the email */" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background-color: #f4f4f4;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "        }" +
                "        .container {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            padding: 20px;" +
                "            background-color: #ffffff;" +
                "            border-radius: 10px;" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        h1 {" +
                "            color: #333;" +
                "        }" +
                "        p {" +
                "            margin-bottom: 20px;" +
                "            color: #666;" +
                "        }" +
                "        .details {" +
                "            font-weight: bold;" +
                "            background-color: #f4f4f4;" +
                "            padding: 10px;" +
                "            border-radius: 5px;" +
                "        }" +
                "        .details p {\n" +
                "            margin: 0; /* Remove the default margin for paragraphs */\n" +
                "        }\n" +
                "        .details p span {\n" +
                "            display: inline-block;\n" +
                "            width: 120px; /* Fixed width for the colons */\n" +
                "            font-weight: bold;\n" +
                "            margin-right: 10px; /* Margin to separate the colon from the value */\n" +
                "        }\n" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>Asset Updated</h1>" +
                "        <p>An asset has been updated in the Smart Asset Master application. Here are the details:</p>" +
                "        " +
                "        <div class='details'>" +
                "            <p><span>Updated by:</span> AM_USER_NAME</p>" +
                "            <p><span>Asset ID:</span> AM_ASSET_ID</p>" +
                "            <p><span>Asset Name:</span> AM_ASSET_NAME</p>" +
                "            <p><span>Created Date:</span> AM_CREATED_DATE</p>" +
                "            <p><span>Created Date:</span> AM_UPDATED_DATE</p>" +
                "            <p><span>Current Location:</span> AM_LOCATION</p>" +
                "            <p><span>Status:</span> AM_STATUS</p>" +
                "        </div>" +
                "        <p>Please review this information in the Smart Asset Master application.</p>" +
                "        <p>Thank you,</p>" +
                "        <p>Your Smart Asset Master System</p>" +
                "    </div>" +
                "</body>" +
                "</html>";

        html = html.replace("AM_USER_NAME", input.get("UserName"))
                .replace("AM_ASSET_ID" , input.get("AssetID"))
                .replace("AM_ASSET_NAME" , input.get("AssetName"))
                .replace("AM_CREATED_DATE" , input.get("CreatedDate"))
                .replace("AM_UPDATED_DATE" , input.get("UpdatedDate"))
                .replace("AM_LOCATION" , input.get("Location"))
                .replace("AM_STATUS" , input.get("Status"));
        return html;
    }

    public String getRemoveAssetHtmlTemplate(Map<String, String> input){
        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        /* Define some basic styles for the email */" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background-color: #f4f4f4;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "        }" +
                "        .container {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            padding: 20px;" +
                "            background-color: #ffffff;" +
                "            border-radius: 10px;" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        h1 {" +
                "            color: #333;" +
                "        }" +
                "        p {" +
                "            margin-bottom: 20px;" +
                "            color: #666;" +
                "        }" +
                "        .details {" +
                "            font-weight: bold;" +
                "            background-color: #f4f4f4;" +
                "            padding: 10px;" +
                "            border-radius: 5px;" +
                "        }" +
                "        .details p {\n" +
                "            margin: 0; /* Remove the default margin for paragraphs */\n" +
                "        }\n" +
                "        .details p span {\n" +
                "            display: inline-block;\n" +
                "            width: 120px; /* Fixed width for the colons */\n" +
                "            font-weight: bold;\n" +
                "            margin-right: 10px; /* Margin to separate the colon from the value */\n" +
                "        }\n" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>Asset Updated</h1>" +
                "        <p>An asset has been removed from the Smart Asset Master application. Here are the details:</p>" +
                "        " +
                "        <div class='details'>" +
                "            <p><span>Removed by:</span> AM_USER_NAME</p>" +
                "            <p><span>Asset ID:</span> AM_ASSET_ID</p>" +
                "            <p><span>Asset Name:</span> AM_ASSET_NAME</p>" +
                "            <p><span>Created Date:</span> AM_CREATED_DATE</p>" +
                "            <p><span>Removed Date:</span> AM_REMOVED_DATE</p>" +
                "            <p><span>Status:</span> AM_STATUS</p>" +
                "        </div>" +
                "        <p>Please review this information in the Smart Asset Master application.</p>" +
                "        <p>Thank you,</p>" +
                "        <p>Your Smart Asset Master System</p>" +
                "    </div>" +
                "</body>" +
                "</html>";

        html = html.replace("AM_USER_NAME", input.get("UserName"))
                .replace("AM_ASSET_ID" , input.get("AssetID"))
                .replace("AM_ASSET_NAME" , input.get("AssetName"))
                .replace("AM_CREATED_DATE" , input.get("CreatedDate"))
                .replace("AM_REMOVED_DATE" , input.get("RemovedDate"))
                .replace("AM_STATUS" , input.get("Status"));
        return html;
    }

    public String getCreateAccountHtmlTemplate(Map<String, String> input){
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f5f5f5;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #666;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007BFF;\n" +
                "            color: #fff;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Welcome to AssetMaster</h1>\n" +
                "        <p>Dear AM_USER_NAME,</p>\n" +
                "        <p>Thank you for joining AssetMaster. Your account has been successfully created.</p>\n" +
                "        <p>You are now part of our community, where you can manage your assets with ease.</p>\n" +
                "        <a class=\"button\" href=\"AM_APP_URL\">Get Started</a>\n" +
                "        <p>Best regards,</p>\n" +
                "        <p>The AssetMaster Team</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return html.replace("AM_USER_NAME", input.get("UserName"))
                .replace("AM_APP_URL" , "http://127.0.0.1:8080/login.html");

    }
}
