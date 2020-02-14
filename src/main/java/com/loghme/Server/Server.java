package com.loghme.Server;

import io.javalin.Javalin;
import com.loghme.Constants.ServerConfigs;

public class Server {
    private Javalin app;

    public Server() {
        app = Javalin.create();
    }

    public void start() {
        app.start(ServerConfigs.PORT);
    }
}
