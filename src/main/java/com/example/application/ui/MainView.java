package com.example.application.ui;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

public class MainView extends VerticalLayout {

    private NoteService noteService;

    Grid<Note> grid = new Grid<>(Note.class);

    public MainView(NoteService noteService) {

        addClassName("list-view");

        configureGrid();

        setSizeFull();
        add(grid);

        grid.setItems(noteService.findAll());

    }

    private void configureGrid() {
        grid.setClassName("note-grid");
        grid.setSizeFull();
        grid.setColumns("id", "title", "content");
    }

}
