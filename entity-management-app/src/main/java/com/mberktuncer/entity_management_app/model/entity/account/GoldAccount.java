package com.mberktuncer.entity_management_app.model.entity.account;

import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.couchbase.core.mapping.Document;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
@Setter
@NoArgsConstructor
@TypeAlias("GoldAccount")
public class GoldAccount extends Account{
}
