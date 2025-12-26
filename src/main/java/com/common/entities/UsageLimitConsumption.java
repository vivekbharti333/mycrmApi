package com.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "usage_limit_consumption")
public class UsageLimitConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "consumption_type")
    private String consumptionType;

    @Column(name = "limit_count")
    private int limit;

    @Column(name = "consume_count")
    private int consume;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "superadmin_id")
    private String superadminId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
