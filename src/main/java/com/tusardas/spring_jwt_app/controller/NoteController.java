package com.tusardas.spring_jwt_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tusardas.spring_jwt_app.domain.Note;
import com.tusardas.spring_jwt_app.service.NoteService;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;
    
    @GetMapping("/about")
    public String getLoginPage(Model model) {
        return "about";
    }

    @GetMapping("/notes")
    public String getNotes(Model model) {
        List<Note> notes = noteService.findAllNotes();
        model.addAttribute("notes", notes);
        return "noteListing";
    }
}
