package it.epicode.w6d3.services;

import it.epicode.w6d3.Models.dto.AutoreDto;
import it.epicode.w6d3.Models.entites.Autore;
import it.epicode.w6d3.Models.entites.Post;
import it.epicode.w6d3.exceptions.BadRequestException;
import it.epicode.w6d3.exceptions.ConstraintReferenceError;
import it.epicode.w6d3.exceptions.NotFoundException;
import it.epicode.w6d3.exceptions.PaginationSearchException;
import it.epicode.w6d3.repositories.AutoreRepository;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class AutoreService {

    @Autowired
    AutoreRepository autoreRp;

    public Page<Autore> findAll(Pageable pageable) throws PaginationSearchException {
        try {
            return autoreRp.findAll(pageable);
        } catch (Exception e) {
            throw new PaginationSearchException("Error while searching authors");
        }
    }

    public Autore findById(UUID id) throws NotFoundException {
        return autoreRp.findById(id).orElseThrow(() -> new NotFoundException("Author with id = " + id + " not found"));
    }

    public Autore create(AutoreDto autoreDto) {
        Autore a = new Autore(autoreDto.getNome(), autoreDto.getCognome(), autoreDto.getEmail(),
                autoreDto.getDataDiNascita());
        return autoreRp.save(a);
    }

    public Autore update(UUID id, AutoreDto autoreDto) throws BadRequestException {
        Autore autoreDaAggiornare = autoreRp.findById(id).orElseThrow(
                () -> new BadRequestException("Author you're trying to update doesn't exist (provided id = " + id + ")"));
        autoreDaAggiornare.setNome(autoreDto.getNome());
        autoreDaAggiornare.setCognome(autoreDto.getCognome());
        autoreDaAggiornare.generateAvatar();
        autoreDaAggiornare.setEmail(autoreDto.getEmail());
        autoreDaAggiornare.setDataDiNascita(autoreDto.getDataDiNascita());
        return autoreRp.save(autoreDaAggiornare);
    }

    public void delete(UUID id) throws BadRequestException, DataIntegrityViolationException {
        Autore a = autoreRp.findById(id).orElseThrow(
                () -> new BadRequestException("Author you're trying to delete doesn't exist (provided id = " + id + ")"));

        autoreRp.delete(a);

    }

}
