package com.hit.server;


import com.hit.services.CacheUnitController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

public class Server implements Observer {
    private final int port = 12345;
    private ServerSocket serverSocket;
    private final String start;
    private final String stop;
    private boolean continueLoop;

    public Server() {
        this.start = "start";
        this.stop = "stop";
    }

    @Override
    public void update(Observable o, Object arg) {
        String command = arg.toString();

        if (command.equals(this.start)) {
            startServer();
        } else if (command.equals(this.stop)) {
            this.stopServer();
        }
    }

    private void startServer() {
        this.serverSocket = null;
        Socket socket;

        try {
            this.continueLoop = true;
            this.serverSocket = new ServerSocket(this.port);
            HandleRequest.countRequests = 0;
            CacheUnitController<String> cacheUnitController = new CacheUnitController<String>();
            while (this.continueLoop) {
                socket = this.serverSocket.accept();
                new Thread(new HandleRequest<String>(cacheUnitController, socket)).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void stopServer() {
        try {
            this.continueLoop = false;
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
