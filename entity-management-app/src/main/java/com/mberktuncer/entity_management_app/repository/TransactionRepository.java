package com.mberktuncer.entity_management_app.repository;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.mberktuncer.entity_management_app.model.entity.Transaction;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("Transaction")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
@Collection("Transaction")
public interface TransactionRepository extends CouchbaseRepository<Transaction, String> {
}
