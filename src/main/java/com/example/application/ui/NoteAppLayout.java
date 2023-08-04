package com.example.application.ui;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class NoteAppLayout extends VerticalLayout {

    @Autowired
    private NoteService noteService;

    private H1 title;

    private FormLayout formLayout;

    public NoteAppLayout() {

        title = new H1("My Note App");
        configureTitle();

        formLayout = new FormLayout();
        configureFormLayout();

        add(title, formLayout);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    }

    private void configureFormLayout() {
        TextField title = new TextField("Title");
        TextField content = new TextField();
        content.setPlaceholder("Insert text...");
        //content.setHeight(3, Unit.CM);
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
            } else {
                Notification.show("Insert title and text!");
            }
        });
        formLayout.add(title, content, button);
        formLayout.setColspan(title, 1);
        formLayout.setColspan(content, 2);
        formLayout.setColspan(button, 2);
        formLayout.setSizeFull();
    }

    private void configureTitle() {
    }
}


