package com.mberktuncer.entity_management_app.repository.account;

import com.mberktuncer.entity_management_app.model.entity.account.Account;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountRepository<T extends Account,S> extends CouchbaseRepository<T, S> {
}

