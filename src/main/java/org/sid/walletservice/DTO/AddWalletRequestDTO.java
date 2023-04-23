package org.sid.walletservice.DTO;




public record AddWalletRequestDTO (
        Double balance,
        String currencyCode
){}
