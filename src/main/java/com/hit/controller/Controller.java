package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

public interface Controller extends Observer {
    @Override
    void update(Observable o, Object arg);
}
