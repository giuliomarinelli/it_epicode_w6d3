package it.epicode.w6d3.services;

import it.epicode.w6d3.Models.dto.PostDto;
import it.epicode.w6d3.Models.entites.Autore;
import it.epicode.w6d3.Models.entites.Post;
import it.epicode.w6d3.exceptions.BadRequestException;
import it.epicode.w6d3.exceptions.ConstraintReferenceError;
import it.epicode.w6d3.exceptions.NotFoundException;
import it.epicode.w6d3.exceptions.PaginationSearchException;
import it.epicode.w6d3.repositories.AutoreRepository;
import it.epicode.w6d3.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRp;

    @Autowired
    private AutoreRepository autoreRp;

    public Page<Post> findAll(Pageable pageable, UUID autoreId) throws PaginationSearchException {
        try {
            if (autoreId == null || Objects.equals(autoreId.toString(), ""))
                return postRp.findAll(pageable).map(p -> {
                    p.setAutoreId(p.getAutore().getId());
                    return p;
                });
            return postRp.getPostsByAutore(pageable, autoreId).map(p -> {
                p.setAutoreId(p.getAutore().getId());
                return p;
            });
        } catch (Exception e) {
            throw new PaginationSearchException();
        }
    }

    public Post findById(UUID id) throws NotFoundException {
        Post post = postRp.findById(id).orElseThrow(() -> new NotFoundException("Post with id = " + id + " not found"));
        post.setAutoreId(post.getAutore().getId());
        return post;
    }

    public Post create(PostDto postDto) throws ConstraintReferenceError {
        Autore a = autoreRp.findById(postDto.getAutoreId())
                .orElseThrow(() -> new ConstraintReferenceError("userId "
                        + postDto.getAutoreId() + " doesn't refer to any author"));
        Post p = new Post(postDto.getAutoreId(), a, postDto.getCategoria(), postDto.getTitolo(),
                postDto.getCover(), postDto.getContenuto(), postDto.getTempoDiLettura());
        return postRp.save(p);

    }

    public Post update(UUID id, PostDto postDto) throws BadRequestException {
        Autore a = autoreRp.findById(postDto.getAutoreId())
                .orElseThrow(() -> new ConstraintReferenceError("userId "
                        + postDto.getAutoreId() + " doesn't refer to any author"));
        Post p = postRp.findById(id).orElseThrow(() -> new BadRequestException("Post with id = " + id + " doesn't exist"));
        p.setAutore(a);
        p.setCategoria(postDto.getCategoria());
        p.setCover(postDto.getCover());
        p.setContenuto(postDto.getContenuto());
        p.setTitolo(postDto.getTitolo());
        p.setTempoDiLettura(postDto.getTempoDiLettura());
        return postRp.save(p);
    }

    public void delete(UUID id) throws BadRequestException {
        Post post = postRp.findById(id).orElseThrow(() -> new BadRequestException("The post you asked to delete doesn't exist"));
        postRp.delete(post);
    }

}
