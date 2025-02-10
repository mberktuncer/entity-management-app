package com.mberktuncer.entity_management_app.model.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrieveGoldClientResponse {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Kod")
    private String code;
    @JsonProperty("Alis")
    private String purchase;
    @JsonProperty("Satis")
    private String sale;
    @JsonProperty("Change")
    private Double change;
    @JsonProperty("MobilAciklama")
    private String description;
}
