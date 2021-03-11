package com.funkyjester.demo.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter
public class SFAuditEntry {
    @Id
    String time;

    String salesforceId;

    Boolean success;

    String errorCode;
    String message;
    String fields;

}
