package com.example.globalsolution.controller;

import com.example.globalsolution.DTO.ControleEnergeticoDTO;
import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.service.ControleEnergeticoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Tag(name = "ControleEnergetico", description = "Endpoints para gerenciar o consumo energético dos usuários.")
@RestController
@RequestMapping("/controle-energetico")
public class ControleEnergeticoController {

    @Autowired
    private ControleEnergeticoService controleEnergeticoService;

    // Cria um novo registro de consumo para um usuário
    @Operation(summary = "Criar um novo registro de consumo energético", description = "Registra o consumo energético de um usuário.")
    @PostMapping("/{usuarioId}")
    public ResponseEntity<ControleEnergeticoDTO> createControleEnergetico(
            @Parameter(description = "ID do usuário que será associado ao consumo energético") @PathVariable Long usuarioId,
            @RequestBody ControleEnergeticoDTO dto) {

        ControleEnergetico controleEnergetico = controleEnergeticoService.createControleEnergetico(usuarioId, dto);
        ControleEnergeticoDTO controleEnergeticoDTO = new ControleEnergeticoDTO();
        controleEnergeticoDTO.setId(controleEnergetico.getId());
        controleEnergeticoDTO.setUsuario(controleEnergetico.getUsuario());
        controleEnergeticoDTO.setConsumo(controleEnergetico.getConsumo());
        controleEnergeticoDTO.setDataRegistro(controleEnergetico.getDataRegistro());

        return ResponseEntity.status(HttpStatus.CREATED).body(controleEnergeticoDTO);
    }

    // Lista todos os registros de consumo com paginação
    @Operation(summary = "Listar todos os registros de consumo energético", description = "Obtém uma lista paginada de todos os registros de consumo.")
    @GetMapping
    public ResponseEntity<Page<EntityModel<ControleEnergeticoDTO>>> getAllConsumos(Pageable pageable) {
        Page<ControleEnergeticoDTO> consumos = controleEnergeticoService.getAllConsumos(pageable);

        Page<EntityModel<ControleEnergeticoDTO>> resources = consumos.map(consumoDTO -> {
            EntityModel<ControleEnergeticoDTO> resource = EntityModel.of(consumoDTO);
            addHateoasLinks(resource, consumoDTO.getId());
            return resource;
        });

        return ResponseEntity.ok(resources);
    }

    // Lista os registros de consumo de um usuário específico com paginação
    @Operation(summary = "Listar registros de consumo de um usuário", description = "Obtém uma lista paginada de registros de consumo para um usuário específico.")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<EntityModel<ControleEnergeticoDTO>>> getConsumosByUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId,
            Pageable pageable) {

        Page<ControleEnergeticoDTO> consumos = controleEnergeticoService.getConsumosByUsuario(usuarioId, pageable);

        Page<EntityModel<ControleEnergeticoDTO>> resources = consumos.map(consumoDTO -> {
            EntityModel<ControleEnergeticoDTO> resource = EntityModel.of(consumoDTO);
            addHateoasLinks(resource, consumoDTO.getId());
            return resource;
        });

        return ResponseEntity.ok(resources);
    }

    // Atualiza o consumo energético de um registro
    @Operation(summary = "Atualizar um registro de consumo energético", description = "Atualiza os dados de consumo energético de um registro existente.")
    @PutMapping("/{id}")
    public ResponseEntity<ControleEnergeticoDTO> updateControleEnergetico(
            @Parameter(description = "ID do registro de consumo a ser atualizado") @PathVariable Long id,
            @RequestBody ControleEnergeticoDTO dto) {

        ControleEnergetico controleEnergetico = controleEnergeticoService.updateControleEnergetico(id, dto);
        ControleEnergeticoDTO controleEnergeticoDTO = new ControleEnergeticoDTO();
        controleEnergeticoDTO.setId(controleEnergetico.getId());
        controleEnergeticoDTO.setUsuario(controleEnergetico.getUsuario());
        controleEnergeticoDTO.setConsumo(controleEnergetico.getConsumo());
        controleEnergeticoDTO.setDataRegistro(controleEnergetico.getDataRegistro());

        return ResponseEntity.ok(controleEnergeticoDTO);
    }

    // Exclui um registro de consumo energético
    @Operation(summary = "Excluir um registro de consumo energético", description = "Exclui um registro de consumo energético existente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleEnergetico(
            @Parameter(description = "ID do registro de consumo a ser excluído") @PathVariable Long id) {

        controleEnergeticoService.deleteControleEnergetico(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar HATEOAS links
    private void addHateoasLinks(EntityModel<ControleEnergeticoDTO> resource, Long id) {
        resource.add(WebMvcLinkBuilder.linkTo(ControleEnergeticoController.class).slash(id).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(ControleEnergeticoController.class).withRel("all-consumos"));
    }
}
