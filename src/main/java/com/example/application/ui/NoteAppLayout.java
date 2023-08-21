package com.example.application.ui;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tinymce.TinyMce;

@Route("")
public class NoteAppLayout extends VerticalLayout {

    private NoteService noteService;

    private H1 title = new H1("My Note App");;

    private FormLayout formLayout = new FormLayout();

    Grid<Note> grid = new Grid<>(Note.class);

    @Autowired
    public NoteAppLayout(NoteService noteService) {
        this.noteService = noteService;

        TinyMce tinyMce = new TinyMce();

        configureFormLayout();

        configureGrid();

        add(title, formLayout, grid, tinyMce);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void updateList() {
        grid.setItems(noteService.findAll());
    }

    private void configureFormLayout() {
        TextField title = new TextField("Title");
        TextField content = new TextField();
        content.setPlaceholder("Insert text...");
        Button button = getButton(title, content);
        formLayout.add(title, content, button);
        formLayout.setColspan(title, 1);
        formLayout.setColspan(content, 2);
        formLayout.setColspan(button, 2);
        formLayout.setSizeFull();
        formLayout.setMaxWidth(50, Unit.PERCENTAGE);
    }

    private Button getButton(TextField title, TextField content) {
        Button button = new Button("Add note");
        button.addClickListener(event -> {
            String titleText = title.getValue();
            String contentText = content.getValue();
            if (!titleText.isEmpty() && !contentText.isEmpty()) {
                Note newNote = new Note();
                newNote.setTitle(titleText);
                newNote.setContent(contentText);
                noteService.createNote(newNote);
                title.clear();
                content.clear();
                Notification.show("Note created!");
                updateList();
            } else {
                Notification.show("Insert title and text!");
            }
        });
        button.setMaxWidth(3, Unit.CM);
        return button;
    }

    private void configureGrid() {
        grid.setColumns("id", "title", "content");
    }

}


