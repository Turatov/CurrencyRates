package yt.vibe.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import yt.vibe.entities.Authority;

@Repository
public interface AuthorRepository extends JpaRepository<Authority, Long> {
}
