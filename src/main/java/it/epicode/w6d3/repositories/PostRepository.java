package it.epicode.w6d3.repositories;

import it.epicode.w6d3.Models.entites.Autore;
import it.epicode.w6d3.Models.entites.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, PagingAndSortingRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.autore.id = :autoreId")
    public Page<Post> getPostsByAutore(Pageable pageable, UUID autoreId);

}
