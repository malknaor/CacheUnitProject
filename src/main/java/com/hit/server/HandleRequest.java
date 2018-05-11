package com.hit.server;

import com.google.gson.Gson;
import com.hit.services.CacheUnitController;
import com.sun.deploy.nativesandbox.comm.Response;

import java.io.IOException;
import java.net.Socket;

public class HandleRequest<T> implements Runnable {
    private CacheUnitController<T> cacheUnitController;
    private Socket socket;
    private Gson gson;

    public HandleRequest(CacheUnitController<T> cacheUnitController, Socket socket) {
        this.cacheUnitController = cacheUnitController;
        this.socket = socket;
        this.gson = new Gson();
    }

    @Override
    public void run() {
       /* try {
            Response response = this.gson.fromJson(this.socket.getInputStream().toString(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String action;*/

    }
}
