package it.epicode.w6d3.controllers;

import it.epicode.w6d3.Models.dto.AutoreDto;
import it.epicode.w6d3.Models.entites.Autore;
import it.epicode.w6d3.exceptions.BadRequestException;
import it.epicode.w6d3.exceptions.NotFoundException;
import it.epicode.w6d3.exceptions.PaginationSearchException;
import it.epicode.w6d3.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class AutoreController {
    @Autowired
    private AutoreService autoreSvc;


    @GetMapping("/autori")
    public Page<Autore> findAll(Pageable pageable) {
        try {
            return autoreSvc.findAll(pageable);
        } catch (PaginationSearchException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/autori/{id}")
    public Autore findBiYd(@PathVariable UUID id) {
        try {
            return autoreSvc.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/autori")
    public Autore create(@RequestBody AutoreDto autoreDto) {
        try {
            return autoreSvc.create(autoreDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("autori/{id}")
    public Autore put(@PathVariable UUID id, @RequestBody AutoreDto autoreDto) {
        try {
            return autoreSvc.update(id, autoreDto);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("autori/{id}")
    public void delete(@PathVariable UUID id) {
        try {
            autoreSvc.delete(id);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This author is referenced by one ore more post, cannot delete." +
                    " You have to delete all referencing posts before deleting author)", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

