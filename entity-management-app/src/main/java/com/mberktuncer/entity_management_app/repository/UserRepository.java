package com.mberktuncer.entity_management_app.repository;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.mberktuncer.entity_management_app.model.entity.User;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope("User")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
@Collection("User")
public interface UserRepository extends CouchbaseRepository<User, String> {
    Optional<User> findByEmail(String email);
}
