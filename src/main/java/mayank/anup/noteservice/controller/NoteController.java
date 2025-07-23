package mayank.anup.noteservice.controller;

import mayank.anup.noteservice.model.Note;
import mayank.anup.noteservice.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Create Note
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note saved = noteRepository.save(note);
        return ResponseEntity.ok(saved);
    }

    // Update Note
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Note note = optionalNote.get();
        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        noteRepository.save(note);
        return ResponseEntity.ok(note);
    }

    // Delete Note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        return ResponseEntity.ok(noteRepository.findAll());
    }
}