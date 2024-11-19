package com.example.globalsolution.repository;

import com.example.globalsolution.model.Lembrete;
import com.example.globalsolution.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
    // Buscar todos os lembretes de um usuário
    List<Lembrete> findByUsuario(Usuario usuario);
}
