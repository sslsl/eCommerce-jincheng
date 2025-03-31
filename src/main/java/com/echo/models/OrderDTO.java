package com.echo.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDTO {

    @NotNull
    @Embedded
    private CreditCardInfo cardNumber;
    @NotNull
    private String addressType;

    // Getters and Setters

    public CreditCardInfo getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CreditCardInfo cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
