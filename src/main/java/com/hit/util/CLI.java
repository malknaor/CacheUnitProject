package com.hit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Scanner;

public class CLI extends Observable implements Runnable {
    private Scanner inputStream;
    private OutputStream outputStream;
    private String lastCommand;

    public CLI(InputStream in, OutputStream out) {
        this.inputStream = new Scanner(in);
        this.outputStream = out;
        this.lastCommand = "";
    }

    @Override
    public void run() {
        String command = "";
        
        this.write("Starting the main system..." + "\n");
        this.write("Ready to receive a command: " + "\n");

        while (!command.toLowerCase().equals("exit")) {
            try {
                command = this.inputStream.next();

                if (command.toLowerCase().equals("start") && !this.lastCommand.equals(command.toLowerCase())) {
                    this.lastCommand = command.toLowerCase();
                    this.write("Starting server..." + "\n");
                    runInNewThread(command);
                } else if (command.toLowerCase().equals("stop") && !this.lastCommand.equals(command.toLowerCase())) {
                    this.lastCommand = command.toLowerCase();
                    this.write("Server shutdown..." + "\n");
                    runInNewThread(command);
                } else {
                    if (command.toLowerCase().equals("start")) {
                        this.write("Server is already running! can't run start command." + "\n");
                    } else if (command.toLowerCase().equals("stop")) {
                        this.write("Server is not running! can't run stop command." + "\n");
                    } else {
                        this.write("INVALID command, Please try again!" + "\n");
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String string) {
        try {
            this.outputStream.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runInNewThread(String command) {
        String finalCommand = command;
        new Thread(){
            public void run() {
                setChanged();
                notifyObservers(finalCommand);
            }
        }.start();
    }
}
