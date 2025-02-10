package com.mberktuncer.entity_management_app.repository.account;

import com.couchbase.client.java.query.QueryScanConsistency;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("Account")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
@Collection("UsdAccount")
public interface UsdAccountRepository extends AccountRepository<UsdAccount, String>{
}
