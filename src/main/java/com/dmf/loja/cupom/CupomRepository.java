package com.dmf.loja.cupom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CupomRepository extends JpaRepository<Cupom, Long> {
    Optional<Cupom> findByCodigo(String codigo);
    Optional<Cupom> findByCodigoAndDataValidadeAfter(String codigo, LocalDate dataAtual);

    boolean existsByCodigo(String codigo);
    boolean existsByCodigoAndDataValidadeAfter(String codigo, LocalDate dataAtual);

    // Alternativamente, usando uma query explícita
    @Query("SELECT COUNT(c) > 0 FROM Cupom c WHERE c.codigo = :codigo AND c.dataValidade > :dataAtual")
    boolean isCupomValido(@Param("codigo") String codigo, @Param("dataAtual") LocalDate dataAtual);
}