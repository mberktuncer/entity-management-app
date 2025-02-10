package com.mberktuncer.entity_management_app.model.entity.currency;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@Document
@NoArgsConstructor
@TypeAlias("Currency")
public class UsdData {


    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    @Field
    private String code;
    @Field
    private String purchase;
    @Field
    private String sale;
    @Field
    private Double change;
    @Field
    private String description;

}
