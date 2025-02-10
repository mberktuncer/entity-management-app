package com.mberktuncer.entity_management_app.repository.currency;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.mberktuncer.entity_management_app.model.entity.currency.UsdData;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope("Currency")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
@Collection("Usd")
public interface UsdCurrencyRepository extends CouchbaseRepository<UsdData, String> {
    Optional<UsdData> findFirstByCodeOrderByIdDesc(String code);
}
