package com.loghme.User;

import com.loghme.Constants.Path;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static Handler fetchUser = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("user", UserRepository.getInstance().getUser());
        ctx.render(Path.Template.USER, model);
    };
}
