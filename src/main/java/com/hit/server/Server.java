package com.hit.server;

import com.hit.services.CacheUnitController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Server implements Observer {
    private final int port = 12345;
    private ServerSocket serverSocket;
    private final String start;
    private final String stop;

    public Server() {
        this.start = "start";
        this.stop = "stop";
    }

    public void start() {
        this.serverSocket = null;
        Socket socket = null;
        try {
            this.serverSocket = new ServerSocket(this.port);

            while (true) {
                try {
                    socket = this.serverSocket.accept();
                    new Thread(new HandleRequest<String>(new CacheUnitController<String>(), socket)).run();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String command = arg.toString();

        if (command.equals(this.start)) {
            this.start();
        } else if (command.equals(this.stop)) {
            this.stop();
        }
    }

    private void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
