package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
    public static int countRequests = 0;

    public HandleRequest(CacheUnitController<T> cacheUnitController, Socket socket) {
        this.cacheUnitController = cacheUnitController;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
             PrintWriter write = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String reqJson = reader.nextLine();
            Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
            Request<DataModel<T>[]> request = new Gson().fromJson(reqJson, ref);
            String action = request.getHeaders().get("action");
            String response;

            switch (action.toLowerCase()) {
                case "update":
                    countRequests++;
                    if (cacheUnitController.update(request.getBody())) {
                        write.println("Updated the requested content...");
                    } else {
                        write.println("Couldn't Update the requested content...");
                    }

                    break;
                case "delete":
                    countRequests++;
                    if (cacheUnitController.delete(request.getBody())) {
                        write.println("Deleted the requested content...");
                    } else {
                        write.println("Couldn't Delete the requested content...");
                    }

                    break;
                case "get":
                    countRequests++;
                    DataModel<T>[] dms = cacheUnitController.get(request.getBody());
                    if (dms != null) {
                        response = "Retrieved the requested content...\n";
                        for (DataModel<T> dm : dms) {
                            response += dm.toString() + "\n";
                        }

                        response = response.replaceAll("\n", ".EndLine.");
                        write.println(response);
                    } else {
                        write.println("Couldn't Retrieve the requested content...");
                    }

                    break;
                case "statistics":
                    response = cacheUnitController.getCacheUnitStatistics();
                    response = response.replaceAll("\n", ".EndLine.");
                    write.println(response);
                    break;
                default:
                    write.println("Unknown Command!");
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}