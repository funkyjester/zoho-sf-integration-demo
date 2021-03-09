package com.funkyjester.demo.integration.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter
public class ZohoWatermark {
    @Id
    String module;

    OffsetDateTime lastPoll;
}
