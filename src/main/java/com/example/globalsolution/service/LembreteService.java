package com.example.globalsolution.service;

import com.example.globalsolution.model.Lembrete;
import com.example.globalsolution.repository.LembreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LembreteService {

    @Autowired
    private LembreteRepository lembreteRepository;

    // Cria um novo lembrete
    public Lembrete createLembrete(Lembrete lembrete) {
        return lembreteRepository.save(lembrete);
    }

    // Busca um lembrete pelo ID
    public Lembrete getLembreteById(Long id) {
        Optional<Lembrete> lembrete = lembreteRepository.findById(id);
        if (lembrete.isEmpty()) {
            throw new RuntimeException("Lembrete não encontrado com o ID: " + id);
        }
        return lembrete.get();
    }

    // Lista todos os lembretes de um usuário
    public List<Lembrete> getAllLembretes() {
        return lembreteRepository.findAll();
    }

    // Atualiza um lembrete existente
    public Lembrete updateLembrete(Long id, Lembrete lembrete) {
        if (!lembreteRepository.existsById(id)) {
            throw new RuntimeException("Lembrete não encontrado com o ID: " + id);
        }
        lembrete.setId(id);
        return lembreteRepository.save(lembrete);
    }

    // Exclui um lembrete
    public void deleteLembrete(Long id) {
        if (!lembreteRepository.existsById(id)) {
            throw new RuntimeException("Lembrete não encontrado com o ID: " + id);
        }
        lembreteRepository.deleteById(id);
    }
}
