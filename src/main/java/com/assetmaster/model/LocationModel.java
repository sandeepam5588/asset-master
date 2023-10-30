package com.assetmaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AM_LOCATION")
public class LocationModel {
    @Id
    @Column(name = "LocationID", length = 8)
    private String locationID;

    @Column(name = "Location")
    private String location;
}
