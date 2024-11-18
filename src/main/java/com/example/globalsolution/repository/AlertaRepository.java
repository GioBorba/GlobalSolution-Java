package com.example.globalsolution.repository;

import com.example.globalsolution.model.Alerta;
import com.example.globalsolution.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    // Buscar alertas relacionados a um usuário
    List<Alerta> findByUsuario(Usuario usuario);
}
