package com.alejandro.kook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.kook.Model.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
