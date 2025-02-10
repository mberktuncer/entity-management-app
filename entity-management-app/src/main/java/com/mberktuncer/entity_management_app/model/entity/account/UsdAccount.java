package com.mberktuncer.entity_management_app.model.entity.account;

import com.mberktuncer.entity_management_app.model.entity.account.Account;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.couchbase.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
@Setter
@NoArgsConstructor
@TypeAlias("UsdAccount")
public class UsdAccount extends Account {
}
