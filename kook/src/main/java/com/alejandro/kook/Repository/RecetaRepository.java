package com.alejandro.kook.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.kook.Model.Receta;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
	List<Receta> findByTitulo(String titulo);
    Optional<Receta> findById(Long id);
}
