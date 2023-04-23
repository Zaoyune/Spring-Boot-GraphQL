package org.sid.walletservice.web;

import org.sid.walletservice.DTO.AddWalletRequestDTO;
import org.sid.walletservice.entities.Currency;
import org.sid.walletservice.entities.Wallet;
import org.sid.walletservice.entities.WalletTransaction;
import org.sid.walletservice.repositories.CurrencyRepository;
import org.sid.walletservice.repositories.WalletRepository;
import org.sid.walletservice.service.WalletService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletGraphQlController {
    private WalletRepository walletRepository;
    private WalletService walletService;
    private CurrencyRepository currencyRepository;

    public WalletGraphQlController(WalletRepository walletRepository, WalletService walletService, CurrencyRepository currencyRepository) {
        this.walletRepository = walletRepository;
        this.walletService = walletService;
        this.currencyRepository = currencyRepository;
    }


    @QueryMapping
    public List<Wallet> userWallets(){
        return walletRepository.findAll();
    }
    /*
query {
userWallets{
id,
balance,
currency{
  code
},
__typename,
walletTransactions{
  id,
  amount
}
}
}
* */
    @QueryMapping
    public Wallet walletById(@Argument String id){
        return walletRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Wallet id %s not found",id)));
    }

    /*
    * query {
walletById(id:"3bbed9ab-631c-49f4-85a2-a3a7539ab4f3"){
id,
  balance,
  currency{code},
  walletTransactions{
    type,
    amount
  }
}
}*/
    //Mutation pour modifier/ajouter les données et query pour récuperer les données
    @MutationMapping
    public Wallet addWallet(@Argument AddWalletRequestDTO wallet){
        return walletService.save(wallet);
    }

    /*
    * mutation{
  addWallet(walletDTO :
  {
    currencyCode:"EUR",
    balance:1000000

  }){
    id,balance
  }
}*/
    @MutationMapping
    public List<WalletTransaction> walletTransfer(@Argument String sourceWalletId,@Argument String destinationWalletId,@Argument Double amount){
        return walletService.walletTransfert(sourceWalletId, destinationWalletId, amount);
    }
    /*
    * mutation{
  walletTransfer(
    sourceWalletId : "ccca52a9-2d30-42d5-b94e-f4671d858047",
    destinationWalletId : "2fa34733-b67c-4901-8b8a-0fa98095f542",
    amount : 2000){
  id,amount,wallet{currency{code}}
}
}*/

    @QueryMapping
    public List<Currency> currencies(){
        return currencyRepository.findAll();
    }


}
