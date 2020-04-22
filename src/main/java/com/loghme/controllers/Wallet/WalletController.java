package com.loghme.controllers.Wallet;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.requests.Wallet.WalletRequest;
import com.loghme.controllers.wrappers.responses.Wallet.WalletResponse;
import com.loghme.models.domain.Wallet.exceptions.WrongAmount;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Path.Web.WALLET)
public class WalletController {
    @PostMapping("")
    public WalletResponse chargeUser(@RequestBody WalletRequest request) throws WrongAmount {
        UserRepository.getInstance().chargeUser(request.getAmount());

        double credit = UserRepository.getInstance().getUser().getCredit();

        return new WalletResponse(credit);
    }
}