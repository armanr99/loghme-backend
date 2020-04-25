package com.loghme.controllers.Wallet;

import com.loghme.configs.Path;
import com.loghme.configs.UserConfigs;
import com.loghme.controllers.DTOs.requests.Wallet.WalletRequest;
import com.loghme.controllers.DTOs.responses.Wallet.WalletResponse;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.exceptions.WrongAmount;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(Path.Web.WALLET)
public class WalletController {
    @PostMapping("")
    public WalletResponse chargeUser(@RequestBody WalletRequest request) throws WrongAmount, UserDoesntExist, SQLException {
        int userId = UserConfigs.DEFAULT_ID;
        double amount = request.getAmount();

        UserService.getInstance().chargeUser(userId, amount);

        double credit = UserService.getInstance().getUser(userId).getCredit();
        return new WalletResponse(credit);
    }
}