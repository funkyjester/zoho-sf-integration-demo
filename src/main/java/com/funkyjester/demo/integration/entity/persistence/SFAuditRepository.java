package com.funkyjester.demo.integration.entity.persistence;

import com.funkyjester.demo.integration.entity.SFAuditEntry;
import com.funkyjester.demo.integration.entity.ZohoWatermark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SFAuditRepository extends CrudRepository<SFAuditEntry, String> {
}
