package api.notes.note.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.ApiNotesApplication;
import api.notes.note.application.NoteService;
import api.notes.note.application.NoteServiceImp;
import api.notes.note.infrastructure.persistence.NoteRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = ApiNotesApplication.class)
public class NoteConfiguration {

    @Bean
    NoteService noteService(NoteRepositoryImplSql repo) {

        return NoteServiceImp.build(repo);
    }
}
