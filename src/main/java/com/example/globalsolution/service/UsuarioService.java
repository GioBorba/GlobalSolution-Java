package com.example.globalsolution.service;

import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.model.Usuario;
import com.example.globalsolution.DTO.UsuarioDTO;
import com.example.globalsolution.repository.ControleEnergeticoRepository;
import com.example.globalsolution.repository.UsuarioRepository;

import com.example.globalsolution.util.ConsumoEnergeticoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ControleEnergeticoRepository consumoRepository;

    @Autowired
    private ConsumoEnergeticoGenerator consumoGenerator;

    // Cria novo usuario
    public Usuario createUser(Usuario usuario) {
        // Verifica se o e-mail já está cadastrado
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered.");
        }

        // Salva o usuário no banco de dados
        Usuario savedUser = usuarioRepository.save(usuario);

        // Gera um consumo aleatório para o usuário criado
        ControleEnergetico consumo = consumoGenerator.generateRandomConsumo(savedUser);

        // Salva o consumo gerado no banco de dados
        consumoRepository.save(consumo);

        return savedUser;
    }

    // Atualiza usuario
    public Usuario updateUser(Long id, Usuario updatedUsuario) {
        Usuario usuario = getUserById(id);
        usuario.setNome(updatedUsuario.getNome());
        usuario.setEmail(updatedUsuario.getEmail());
        usuario.setSenha(updatedUsuario.getSenha());
        usuario.setUnidade(updatedUsuario.getUnidade());
        return usuarioRepository.save(usuario);
    }

    // Lista usuarios com paginação
    public Page<UsuarioDTO> getAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::convertToDto);
    }

    // Acha usuario por id
    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    // Acha usuario por email
    public UsuarioDTO getUserByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        return convertToDto(usuario);
    }

    // Deleta usuario
    public void deleteUser(Long id) {
        Usuario usuario = getUserById(id);
        usuarioRepository.delete(usuario);
    }

    // Conversão Usuario -> UsuarioDTO
    private UsuarioDTO convertToDto(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
}
