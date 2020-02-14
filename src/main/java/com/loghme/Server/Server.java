package com.loghme.Server;

import com.loghme.Constants.Path;
import com.loghme.User.UserController;
import io.javalin.Javalin;
import com.loghme.Constants.ServerConfigs;

import static io.javalin.apibuilder.ApiBuilder.get;

public class Server {
    private Javalin app;

    public Server() {
        app = Javalin.create();
    }

    public void start() {
        app.start(ServerConfigs.PORT);

        app.routes(() -> {
            get(Path.Web.USER, UserController.fetchUser);
        });
    }
}
