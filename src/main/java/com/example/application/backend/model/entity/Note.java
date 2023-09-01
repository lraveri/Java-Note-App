package com.example.application.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
@Table(name = "NOTE")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long isPinned;

    private String tags;

    @Column(name = "ID_PARENT")
    private Long idParent;

//    private LocalDateTime createdWhen;
//
//    private LocalDateTime lastUpdatedWhen;

    private String createdBy;

    public static void update(Note original, Note update) {
        original.setContent(update.getContent());
        original.setCreatedBy(update.getCreatedBy());
//        original.setCreatedWhen(update.getCreatedWhen());
        original.setTags(update.getTags());
        original.setTitle(update.getTitle());
        original.setIdParent(update.getIdParent());
        original.setIsPinned(update.getIsPinned());
//        original.setLastUpdatedWhen(update.getLastUpdatedWhen());
    }
}
