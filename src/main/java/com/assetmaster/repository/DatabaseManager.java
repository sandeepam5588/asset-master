package com.assetmaster.repository;

import com.assetmaster.model.LocationModel;
import com.assetmaster.model.response.AssetResponseModel;
import com.assetmaster.model.response.HistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class DatabaseManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AssetResponseModel> fetchAssetsWithDetails() {
        String sql = "SELECT a.AssetID, a.AssetName, a.AssetTypeID, a.DateCreated, a.Status, a.LocationID, a.Description, " +
                "at.AssetType, l.Location " +
                "FROM AM_ASSET a " +
                "JOIN AM_ASSET_TYPE at ON a.AssetTypeID = at.AssetTypeID " +
                "JOIN AM_LOCATION l ON a.LocationID = l.LocationID";

        return jdbcTemplate.query(sql, new AssetResponseModelRowMapper());
    }

    private static class AssetResponseModelRowMapper implements RowMapper<AssetResponseModel> {
        @Override
        public AssetResponseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AssetResponseModel assetResponseModel = new AssetResponseModel();
            assetResponseModel.setAssetID(rs.getString("AssetID"));
            assetResponseModel.setAssetName(rs.getString("AssetName"));
            assetResponseModel.setAssetTypeID(rs.getString("AssetTypeID"));
            assetResponseModel.setDateCreated(rs.getTimestamp("DateCreated").toLocalDateTime());
            assetResponseModel.setStatus(rs.getString("Status"));
            assetResponseModel.setLocationID(rs.getString("LocationID"));
            assetResponseModel.setDescription(rs.getString("Description"));
            assetResponseModel.setAssetType(rs.getString("AssetType"));
            assetResponseModel.setLocation(rs.getString("Location"));
            return assetResponseModel;
        }
    }

    public List<LocationModel> fetchLocations() {
        String sql = "SELECT * from AM_LOCATION";

        return jdbcTemplate.query(sql, new LocationResponseModelRowMapper());
    }

    private static class LocationResponseModelRowMapper implements RowMapper<LocationModel> {
        @Override
        public LocationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocationModel locationModel = new LocationModel();
            locationModel.setLocationID(rs.getString("LocationID"));
            locationModel.setLocation(rs.getString("Location"));
            return locationModel;
        }
    }

    public List<HistoryResponse> getAssetHistory(String assetID) {
        String sql = "SELECT " +
                "    AH.TransactionID, " +
                "    AH.TransactionDate, " +
                "    AH.UpdateStatus, " +
                "    AH.Description, " +
                "    U.UserName, "+
                "    A.AssetID, " +
                "    A.AssetName, " +
                "    AT.AssetType, " +
                "    FL.Location AS FromLocation, " +
                "    TL.Location AS ToLocation " +
                "FROM " +
                "    AM_ASSET_HISTORY AS AH " +
                "INNER JOIN " +
                "    AM_ASSET AS A " +
                "ON " +
                "    AH.AssetID = A.AssetID " +

                "INNER JOIN " +
                "    AM_USER AS U " +
                "ON " +
                "    AH.UserID = U.UserID " +

                "INNER JOIN " +
                "    AM_ASSET_TYPE AS AT " +
                "ON " +
                "    A.AssetTypeID = AT.AssetTypeID " +
                "LEFT JOIN " +
                "    AM_LOCATION AS FL " +
                "ON " +
                "    AH.FromLocationID = FL.LocationID " +
                "LEFT JOIN " +
                "    AM_LOCATION AS TL " +
                "ON " +
                "    AH.ToLocationID = TL.LocationID " +
                "WHERE " +
                "    A.AssetID = '"+assetID+"'";

        return jdbcTemplate.query(sql, new HistoryResponseRowMapper());
    }

    private static class HistoryResponseRowMapper implements RowMapper<HistoryResponse> {
        @Override
        public HistoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            HistoryResponse historyResponse = new HistoryResponse();
            historyResponse.setTransactionID(rs.getInt("TransactionID"));
            historyResponse.setAssetName(rs.getString("AssetName"));
            historyResponse.setAssetType(rs.getString("AssetType"));
            java.sql.Date sqlDate = rs.getDate("TransactionDate");
            if (sqlDate != null) {
                Instant instant = sqlDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
                historyResponse.setTransactionDate(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
            }

            historyResponse.setFromLocationName(rs.getString("FromLocation"));
            historyResponse.setToLocationName(rs.getString("ToLocation"));
            historyResponse.setUpdateStatus(rs.getString("UpdateStatus"));
            historyResponse.setDescription(rs.getString("Description"));
            historyResponse.setUserName(rs.getString("UserName"));
            return historyResponse;
        }
    }

}
