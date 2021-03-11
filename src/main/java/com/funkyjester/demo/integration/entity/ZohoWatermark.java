package com.funkyjester.demo.integration.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.OffsetDateTime;

/**
 * used to track high-water level of delta calls to zoho tables
 */
@Entity
@Getter @Setter
public class ZohoWatermark {
    @Id
    String module;

    OffsetDateTime lastPoll;

    OffsetDateTime previousValue;

    public ZohoWatermark mark() {
        return mark(OffsetDateTime.now());
    }

    public ZohoWatermark mark(OffsetDateTime newODT) {
        previousValue = lastPoll;
        lastPoll = newODT;
        return this;
    }

    public ZohoWatermark rollback() {
        lastPoll = previousValue;
        previousValue = null;
        return this;
    }
}
