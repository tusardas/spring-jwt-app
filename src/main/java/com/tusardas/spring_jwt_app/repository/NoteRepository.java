package com.tusardas.spring_jwt_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tusardas.spring_jwt_app.domain.Note;
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
}
