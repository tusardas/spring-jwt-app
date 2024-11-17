package com.tusardas.spring_jwt_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tusardas.spring_jwt_app.domain.Note;
import com.tusardas.spring_jwt_app.repository.NoteRepository;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAllNotes() {
        List<Note> noteList = noteRepository.findAll();
        return noteList;
    }
}
