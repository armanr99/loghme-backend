package com.loghme.controllers.Wallet;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.requests.Wallet.WalletRequest;
import com.loghme.controllers.wrappers.responses.User.UserResponse;
import com.loghme.models.User.User;
import com.loghme.models.Wallet.exceptions.WrongAmount;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Path.Web.WALLET)
public class WalletController {
    @PostMapping("")
    public UserResponse chargeUser(@RequestBody WalletRequest request) throws WrongAmount {
        UserRepository.getInstance().chargeUser(request.getAmount());
        User user = UserRepository.getInstance().getUser();
        return new UserResponse(user);
    }
}