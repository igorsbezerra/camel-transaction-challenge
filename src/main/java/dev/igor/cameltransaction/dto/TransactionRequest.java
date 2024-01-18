package dev.igor.cameltransaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @JsonProperty("sourceAccount")
    private String sourceAccount;
    @JsonProperty("targetAccount")
    private String targetAccount;
    @JsonProperty("amount")
    private String amount;
}
