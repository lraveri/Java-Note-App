package com.example.application.ui.view;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.tinymce.TinyMce;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PermitAll
@Route("")
public class MainView extends VerticalLayout {

    private NoteService noteService;

    private final transient AuthenticationContext authContext;

    private Grid<Note> grid = new Grid<>(Note.class);
    private H1 title = new H1("Java Note App");
    private TinyMce tinyMce = new TinyMce();

    @Autowired
    public MainView(NoteService noteService, @Autowired SecurityService securityService, AuthenticationContext authContext) {
        this.noteService = noteService;
        this.authContext = authContext;

        this.setWidthFull();
        this.setHeightFull();

        DataProvider<Note, Void> dataProvider = DataProvider.fromCallbacks(
                query -> noteService.findAll().stream(),
                query -> noteService.count()
        );

        grid.setDataProvider(dataProvider);

        updateList();

        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setWidthFull();
        userLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        Text userText = new Text("Hi, " + authContext.getAuthenticatedUser(UserDetails.class).get().getUsername());

        HorizontalLayout titleLayout = new HorizontalLayout();

        HorizontalLayout bodyLayout = new HorizontalLayout();
        bodyLayout.setWidthFull();
        bodyLayout.setHeightFull();

        configureTinyMce();
        configureGrid();

        selectFirstNote();

        Button logoutButton = new Button("Logout", click ->
                securityService.logout());

        Button newNoteButton = createNewNoteButton(noteService);
        Button saveButton = createSaveButton(noteService, dataProvider);

        userLayout.add(userText, logoutButton);

        titleLayout.add(title);
        titleLayout.addAndExpand(new Div());
        titleLayout.add(saveButton, newNoteButton);

        bodyLayout.add(grid, tinyMce);

        add(userLayout, titleLayout, bodyLayout);
    }

    private Button createSaveButton(NoteService noteService, DataProvider<Note, Void> dataProvider) {
        return new Button("Save", e -> {
            Optional<Note> selectedNoteOpt = grid.getSelectionModel().getFirstSelectedItem();
            if (selectedNoteOpt.isPresent()) {
                Note selectedNote = selectedNoteOpt.get();
                selectedNote.setContent(tinyMce.getCurrentValue());
                noteService.updateNote(selectedNote);

                dataProvider.refreshItem(selectedNote);
                updateList();
                grid.select(selectedNote);
            }
        });
    }

    private Button createNewNoteButton(NoteService noteService) {
        return new Button("New", e -> {
            Note note = new Note();

            Dialog dialog = new Dialog("Insert title");

            TextField titleField = new TextField("Title");
            Button button = new Button("Save", saveEvent -> {
                note.setTitle(titleField.getValue());
                dialog.close();
                noteService.createNote(note);
                grid.getDataProvider().refreshItem(note);
                grid.select(note);
                updateList();
                grid.select(note);
            });

            VerticalLayout dialogLayout = new VerticalLayout(titleField, button);

            dialog.add(dialogLayout);

            dialog.open();
        });
    }

    private void configureGrid() {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        GridSingleSelectionModel<Note> singleSelect =
                (GridSingleSelectionModel<Note>) grid
                        .getSelectionModel();

        singleSelect.setDeselectAllowed(false);
        grid.setColumns("title");
        grid.getColumns().forEach(col -> col.setSortable(false));
        grid.addColumn(new ComponentRenderer<>(this::createEditButton)).setHeader("").setFlexGrow(0);
        grid.addComponentColumn(this::createDeleteButton).setHeader("").setFlexGrow(0);
        grid.setWidth("30%");
        grid.setHeightFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addSelectionListener(e -> {
            Optional<Note> noteOpt = e.getFirstSelectedItem();
            if(noteOpt.isPresent()) {
                Note note = noteOpt.get();
                if(note.getContent() == null) {
                    tinyMce.setEditorContent("");
                } else {
                    tinyMce.setEditorContent(note.getContent());
                }
            }
        });

    }

    private Button createDeleteButton(Note note) {
        Button deleteButton = new Button(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(e -> showDeleteConfirmationPopup(note));
        return deleteButton;
    }

    private void showDeleteConfirmationPopup(Note note) {
        Dialog dialog = new Dialog("Confirm Deletion");

        Text confirmationText = new Text("Are you sure you want to delete this note?");
        Button confirmButton = new Button("Confirm", confirmEvent -> {
            deleteNoteAndRefreshGrid(note);
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", cancelEvent -> dialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmButton, cancelButton);

        dialog.add(confirmationText, buttonLayout);
        dialog.open();
    }

    private void deleteNoteAndRefreshGrid(Note note) {
        noteService.deleteNote(note.getId());
        grid.getDataProvider().refreshAll();
        updateList();
        selectFirstNote();
    }

    private void selectFirstNote() {
        DataProvider<Note, ?> dataProvider2 = grid.getDataProvider();
        if (dataProvider2 != null) {
            List<Note> noteList = new ArrayList<>(dataProvider2.fetch(new Query<>()).collect(Collectors.toList()));
            if (!noteList.isEmpty()) {
                Note firstNote = noteList.get(0);
                grid.select(firstNote);
                if (firstNote.getContent() == null) {
                    tinyMce.setEditorContent("");
                } else {
                    tinyMce.setEditorContent(firstNote.getContent());
                }
            } else {
                tinyMce.setEditorContent("Welcome! Create a new note with the \"New\" button!");
            }
        }
    }

    private Button createEditButton(Note note) {
        Button editButton = new Button(VaadinIcon.EDIT.create());
        editButton.addClickListener(e -> openEditDialog(note));
        return editButton;
    }

    private void openEditDialog(Note note) {
        Dialog dialog = new Dialog("Edit Title");

        TextField titleField = new TextField("Title");
        titleField.setValue(note.getTitle());

        Button saveButton = new Button("Save", saveEvent -> {
            note.setTitle(titleField.getValue());
            dialog.close();
            noteService.updateNote(note);
            grid.getDataProvider().refreshItem(note);
            updateList();
            grid.select(note);
        });

        VerticalLayout dialogLayout = new VerticalLayout(titleField, saveButton);
        dialog.add(dialogLayout);

        dialog.open();
    }

    private void configureTinyMce() {
        tinyMce.setHeightFull();
        tinyMce.setWidthFull();
    }

    private void updateList() {
        List<Note> notes = noteService.findAll();

        notes.sort(Comparator.comparingLong(Note::getId).reversed());

        grid.setItems(notes);
    }

}