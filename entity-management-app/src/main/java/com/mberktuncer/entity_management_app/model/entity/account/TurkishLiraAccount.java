package com.mberktuncer.entity_management_app.model.entity.account;

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
@TypeAlias("TurkishLiraAccount")
public class TurkishLiraAccount extends Account{
}
