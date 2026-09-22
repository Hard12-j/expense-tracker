package org.example.respository;

import org.example.entities.RefreshTokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokens, Integer> {
    Optional<RefreshTokens> findByToken(String token);

}
