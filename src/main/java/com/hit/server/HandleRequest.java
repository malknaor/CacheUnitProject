package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest<T> implements Runnable {
    private CacheUnitController<T> cacheUnitController;
    private Socket socket;

    public HandleRequest(CacheUnitController<T> cacheUnitController, Socket socket) {
        this.cacheUnitController = cacheUnitController;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(this.socket.getInputStream()));
        PrintWriter write = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));) {
            String reqJson = reader.next();
            Type ref = new TypeToken<Request<T>[]>(){}.getType();
            Request<DataModel<T>[]> request = new Gson().fromJson(reqJson, ref);
            System.out.println(request.toString());
            String action = request.getHeaders().get("action");
            switch (action.toLowerCase()) {
                case "update":
                    if (this.cacheUnitController.update(request.getBody())) {
                        write.print(request);
                    }
                    break;
                case "delete":
                    if (this.cacheUnitController.delete(request.getBody())){
                        write.print(request);
                    }
                    break;
                case "get":
                    this.cacheUnitController.get(request.getBody());
                    break;
                    default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
