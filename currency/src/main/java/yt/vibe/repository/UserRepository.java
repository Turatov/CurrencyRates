package yt.vibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yt.vibe.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
