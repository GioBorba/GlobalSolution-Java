package com.example.globalsolution.repository;

import com.example.globalsolution.model.Alerta;
import com.example.globalsolution.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    // Busca alertas relacionados a um usuário
    List<Alerta> findByUsuario(Usuario usuario);
}
