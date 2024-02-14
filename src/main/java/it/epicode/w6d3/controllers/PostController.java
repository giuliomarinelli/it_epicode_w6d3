package it.epicode.w6d3.controllers;

import it.epicode.w6d3.Models.dto.AutoreDto;
import it.epicode.w6d3.Models.dto.PostDto;
import it.epicode.w6d3.Models.entites.Autore;
import it.epicode.w6d3.Models.entites.Post;
import it.epicode.w6d3.exceptions.BadRequestException;
import it.epicode.w6d3.exceptions.ConstraintReferenceError;
import it.epicode.w6d3.exceptions.NotFoundException;
import it.epicode.w6d3.exceptions.PaginationSearchException;
import it.epicode.w6d3.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class PostController {
    @Autowired
    private PostService postSvc;

    @GetMapping("/post")
    public Page<Post> findAll(Pageable pageable, @RequestParam(required = false) UUID autoreId) {
        try {
            return postSvc.findAll(pageable, autoreId);
        } catch (PaginationSearchException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/{id}")
    public Post findBiYd(@PathVariable UUID id) {
        try {
            return postSvc.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public Post create(@RequestBody PostDto postDto) {
        try {
            return postSvc.create(postDto);
        } catch (ConstraintReferenceError e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("post/{id}")
    public Post put(@PathVariable UUID id, @RequestBody PostDto postDto) {
        try {
            return postSvc.update(id, postDto);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("post/{id}")
    public void delete(@PathVariable UUID id) {
        try {
            postSvc.delete(id);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
