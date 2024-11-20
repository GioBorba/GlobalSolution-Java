package com.example.globalsolution.controller;

import com.example.globalsolution.model.Usuario;
import com.example.globalsolution.DTO.UsuarioDTO;
import com.example.globalsolution.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuarios")
@Validated
@Tag(name = "Gestão de Usuários", description = "APIs para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Cria um novo usuário
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> createUser(@Valid @RequestBody Usuario usuario) {
        Usuario savedUser = usuarioService.createUser(usuario);
        UsuarioDTO usuarioDTO = convertToDto(savedUser);

        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioDTO);
        addHateoasLinks(resource, savedUser.getId());

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(
                        methodOn(UsuarioController.class).getUserById(savedUser.getId())).toUri())
                .body(resource);
    }

    //Busca um usuário pelo ID
    @Operation(summary = "Buscar usuário por ID", description = "Obtém um usuário com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        UsuarioDTO usuarioDTO = convertToDto(usuario);

        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioDTO);
        addHateoasLinks(resource, id);

        return ResponseEntity.ok(resource);
    }

    // Lista todos os usuários com paginação
    @Operation(summary = "Listar usuários", description = "Obtém uma lista paginada de usuários.")
    @GetMapping
    public ResponseEntity<Page<EntityModel<UsuarioDTO>>> getAllUsers(Pageable pageable) {
        Page<UsuarioDTO> users = usuarioService.getAllUsers(pageable);

        Page<EntityModel<UsuarioDTO>> resources = users.map(userDTO -> {
            EntityModel<UsuarioDTO> resource = EntityModel.of(userDTO);
            addHateoasLinks(resource, userDTO.getId());
            return resource;
        });

        return ResponseEntity.ok(resources);
    }

    // Atualiza os dados de um usuário existente.
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário com base no ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody Usuario updatedUsuario) {
        Usuario updatedUser = usuarioService.updateUser(id, updatedUsuario);
        UsuarioDTO usuarioDTO = convertToDto(updatedUser);

        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioDTO);
        addHateoasLinks(resource, id);

        return ResponseEntity.ok(resource);
    }


     //Exclui um usuário com base no ID fornecido.

    @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    //Adiciona links HATEOAS dinamicamente.
    private void addHateoasLinks(EntityModel<UsuarioDTO> resource, Long id) {
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(UsuarioController.class).getUserById(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(UsuarioController.class).getAllUsers(Pageable.unpaged())).withRel("all-users"));
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(UsuarioController.class).deleteUser(id)).withRel("delete-user"));
    }

    // Converte um objeto Usuario para UsuarioDTO
    private UsuarioDTO convertToDto(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
}
