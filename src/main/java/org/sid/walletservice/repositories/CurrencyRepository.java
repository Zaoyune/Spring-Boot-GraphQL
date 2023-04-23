package org.sid.walletservice.repositories;

import org.sid.walletservice.entities.Currency;
import org.sid.walletservice.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {
}
