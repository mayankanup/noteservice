package mayank.anup.noteservice.controller;

import mayank.anup.noteservice.model.Note;
import mayank.anup.noteservice.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NoteControllerTest {

    private NoteRepository noteRepository;
    private NoteController noteController;

    @BeforeEach
    void setUp() {
        noteRepository = mock(NoteRepository.class);
        noteController = new NoteController(noteRepository);
    }

    @Test
    void testCreateNote() {
        Note input = new Note();
        input.setTitle("Test");
        input.setContent("This is a test");

        Note saved = new Note();
        saved.setId(1L);
        saved.setTitle("Test");
        saved.setContent("This is a test");

        when(noteRepository.save(input)).thenReturn(saved);

        ResponseEntity<Note> response = noteController.createNote(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getTitle());
        verify(noteRepository, times(1)).save(input);
    }

    @Test
    void testUpdateNote_Success() {
        Long noteId = 1L;
        Note existingNote = new Note();
        existingNote.setId(noteId);
        existingNote.setTitle("Old Title");
        existingNote.setContent("Old Content");

        Note updatedInput = new Note();
        updatedInput.setTitle("New Title");
        updatedInput.setContent("New Content");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(ArgumentMatchers.any(Note.class))).thenReturn(existingNote);

        ResponseEntity<?> response = noteController.updateNote(noteId, updatedInput);

        assertEquals(200, response.getStatusCodeValue());
        Note updatedNote = (Note) response.getBody();
        assertEquals("New Title", updatedNote.getTitle());
        assertEquals("New Content", updatedNote.getContent());
    }

    @Test
    void testUpdateNote_NotFound() {
        Long noteId = 999L;
        Note updatedInput = new Note();
        updatedInput.setTitle("New Title");
        updatedInput.setContent("New Content");

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = noteController.updateNote(noteId, updatedInput);

        assertEquals(404, response.getStatusCodeValue());
        verify(noteRepository, never()).save(any());
    }

    @Test
    void testDeleteNote_Success() {
        Long noteId = 1L;
        when(noteRepository.existsById(noteId)).thenReturn(true);

        ResponseEntity<Void> response = noteController.deleteNote(noteId);

        assertEquals(204, response.getStatusCodeValue());
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void testDeleteNote_NotFound() {
        Long noteId = 999L;
        when(noteRepository.existsById(noteId)).thenReturn(false);

        ResponseEntity<Void> response = noteController.deleteNote(noteId);

        assertEquals(404, response.getStatusCodeValue());
        verify(noteRepository, never()).deleteById(noteId);
    }

    @Test
void testGetAllNotes() {
    Note note1 = new Note();
    note1.setId(1L);
    note1.setTitle("Note 1");
    note1.setContent("Content 1");

    Note note2 = new Note();
    note2.setId(2L);
    note2.setTitle("Note 2");
    note2.setContent("Content 2");

    List<Note> noteList = List.of(note1, note2);

    when(noteRepository.findAll()).thenReturn(noteList);

    ResponseEntity<?> response = noteController.getAllNotes();

    assertEquals(200, response.getStatusCodeValue());
    List<?> returnedNotes = (List<?>) response.getBody();
    assertEquals(2, returnedNotes.size());

    verify(noteRepository, times(1)).findAll();
}

}
