package com.example.globalsolution.repository;

import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ControleEnergeticoRepository extends JpaRepository<ControleEnergetico, Long> {
    List<ControleEnergetico> findByUsuario(Usuario usuario);
}
