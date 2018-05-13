package com.hit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Scanner;

public class CLI extends Observable implements Runnable {
    private Scanner inputStream;
    private OutputStream outputStream;

    public CLI(InputStream in, OutputStream out) {
        this.inputStream = new Scanner(in);
        this.outputStream = out;
    }

    @Override
    public void run() {
        String command = "";

        while (!command.toLowerCase().equals("exit")) {
            try {
                command = this.inputStream.next();

                if (command.toLowerCase().equals("start") || command.toLowerCase().equals("stop")) {
                    setChanged();
                    notifyObservers(command);
                } else {
                    this.write("INVALID command, Please try again!");
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
}
