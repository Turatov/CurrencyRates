package yt.vibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yt.vibe.entities.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Query(value = "SELECT * FROM currency WHERE code = :code", nativeQuery = true)
    Optional<Currency> findByCode(@Param("code") String code);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO currency (code, rate) VALUES (:code, :rate) " +
            "ON CONFLICT (code) DO UPDATE SET rate = :rate", nativeQuery = true)
    void saveOrUpdateCurrency(@Param("code") String code, @Param("rate") double rate);

}