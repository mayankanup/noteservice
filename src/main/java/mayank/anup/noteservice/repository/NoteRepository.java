package mayank.anup.noteservice.repository;

import mayank.anup.noteservice.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {}