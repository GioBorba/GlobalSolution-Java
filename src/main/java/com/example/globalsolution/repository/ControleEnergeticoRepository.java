package com.example.globalsolution.repository;

import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControleEnergeticoRepository extends JpaRepository<ControleEnergetico, Long> {

    List<ControleEnergetico> findByUsuario(Usuario usuario);
    Page<ControleEnergetico> findByUsuario(Usuario usuario, Pageable pageable); // Com paginação
}
