package com.example.application.ui;

import com.example.application.backend.model.entity.Note;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    Grid<Note> grid = new Grid<>(Note.class);

    public MainView() {

        setSizeFull();
        add(grid);

    }

}
