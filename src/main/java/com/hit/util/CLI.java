package com.hit.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

public class CLI extends Observable implements Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;

    public CLI(InputStream in, OutputStream out) {
        this.inputStream = in;
        this.outputStream = out;
    }

    @Override
    public void run() {

    }

    public void write(String string){

    }
}
