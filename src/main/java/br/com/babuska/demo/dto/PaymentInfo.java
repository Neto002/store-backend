package br.com.babuska.demo.dto;

import lombok.Data;

@Data
public class PaymentInfo {
    private int amount; // order total * 100
    private String currency;
}
