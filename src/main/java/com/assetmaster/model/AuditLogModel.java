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
@Table(name = "AM_AUDIT_LOG")
public class AuditLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AuditID")
    private int auditID;

    @Column(name = "Actor")
    private String actor;

    @Column(name = "Action")
    private String action;

    @Column(name = "AuditDateTime")
    private LocalDateTime auditDateTime;

    @Column(name = "Info")
    private String info;
}
