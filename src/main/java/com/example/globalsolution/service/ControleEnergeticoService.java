package com.example.globalsolution.service;

import com.example.globalsolution.DTO.ControleEnergeticoDTO;
import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.model.Usuario;
import com.example.globalsolution.repository.ControleEnergeticoRepository;
import com.example.globalsolution.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControleEnergeticoService {

    @Autowired
    private ControleEnergeticoRepository controleEnergeticoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cria novo registro de consumo energético
    @Transactional
    public ControleEnergetico createControleEnergetico(Long usuarioId, ControleEnergeticoDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        ControleEnergetico controleEnergetico = new ControleEnergetico();
        controleEnergetico.setUsuario(usuario);
        controleEnergetico.setConsumo(dto.getConsumo());

        return controleEnergeticoRepository.save(controleEnergetico);
    }

    // Lista todos os consumos com paginação
    public Page<ControleEnergeticoDTO> getAllConsumos(Pageable pageable) {
        return controleEnergeticoRepository.findAll(pageable).map(this::convertToDTO);
    }

    // Lista consumos de um usuário específico
    public Page<ControleEnergeticoDTO> getConsumosByUsuario(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        return controleEnergeticoRepository.findByUsuario(usuario, pageable).map(this::convertToDTO);
    }

    // Atualiza um registro de consumo
    @Transactional
    public ControleEnergetico updateControleEnergetico(Long id, ControleEnergeticoDTO dto) {
        ControleEnergetico controleEnergetico = controleEnergeticoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de consumo não encontrado."));

        controleEnergetico.setConsumo(dto.getConsumo());
        return controleEnergeticoRepository.save(controleEnergetico);
    }

    // Exclui um registro de consumo
    @Transactional
    public void deleteControleEnergetico(Long id) {
        ControleEnergetico controleEnergetico = controleEnergeticoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de consumo não encontrado."));

        controleEnergeticoRepository.delete(controleEnergetico);
    }

    // Conversão ControleEnergetico -> DTO
    private ControleEnergeticoDTO convertToDTO(ControleEnergetico controleEnergetico) {
        ControleEnergeticoDTO dto = new ControleEnergeticoDTO();
        dto.setId(controleEnergetico.getId());
        dto.setUsuario(controleEnergetico.getUsuario());
        dto.setConsumo(controleEnergetico.getConsumo());
        dto.setDataRegistro(controleEnergetico.getDataRegistro());
        return dto;
    }
}
