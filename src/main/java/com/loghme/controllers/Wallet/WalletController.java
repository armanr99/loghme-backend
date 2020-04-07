package com.loghme.controllers.Wallet;

import com.loghme.configs.Path;
import com.loghme.models.Wallet.exceptions.WrongAmount;
import com.loghme.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping(Path.Web.WALLET)
public class WalletController {
    @PostMapping("")
    public ResponseEntity chargeUser(@RequestParam("amount") String amountString) throws WrongAmount {
        double amount = Double.parseDouble(Objects.requireNonNull(amountString));
        UserRepository.getInstance().chargeUser(amount);

        return new ResponseEntity(HttpStatus.OK);
    }
}