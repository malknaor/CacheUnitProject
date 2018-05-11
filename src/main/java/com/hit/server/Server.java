package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.Observer;

public class Server implements Observer {
    private ServerSocket serverSocket;

    public Server() {
    }

    public void start(){
        try {
            this.serverSocket = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
