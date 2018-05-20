package com.hit.controller;

import com.hit.model.Model;
import com.hit.view.View;

import java.util.Observable;

public class CacheUnitController implements Controller {
    private View view;

    public CacheUnitController(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    private Model model;

    @Override
    public void update(Observable o, Object arg) {

    }
}
