package com.example.globalsolution.controller;

import com.example.globalsolution.model.Lembrete;
import com.example.globalsolution.DTO.LembreteDTO;
import com.example.globalsolution.service.LembreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/lembretes")
@Tag(name = "Gestão de Lembretes", description = "APIs para gerenciamento de lembretes")
public class LembreteController {

    @Autowired
    private LembreteService lembreteService;

    // Cria um novo lembrete
    @Operation(summary = "Criar lembrete", description = "Cria um novo lembrete com os dados fornecidos.")
    @PostMapping
    public ResponseEntity<EntityModel<LembreteDTO>> createLembrete(@RequestBody Lembrete lembrete) {
        Lembrete savedLembrete = lembreteService.createLembrete(lembrete);
        LembreteDTO lembreteDTO = convertToDto(savedLembrete);

        EntityModel<LembreteDTO> resource = EntityModel.of(lembreteDTO);
        addHateoasLinks(resource, savedLembrete.getId());

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(
                        methodOn(LembreteController.class).getLembreteById(savedLembrete.getId())).toUri())
                .body(resource);
    }

    // Busca um lembrete pelo ID
    @Operation(summary = "Buscar lembrete por ID", description = "Obtém um lembrete com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LembreteDTO>> getLembreteById(@PathVariable Long id) {
        Lembrete lembrete = lembreteService.getLembreteById(id);
        LembreteDTO lembreteDTO = convertToDto(lembrete);

        EntityModel<LembreteDTO> resource = EntityModel.of(lembreteDTO);
        addHateoasLinks(resource, id);

        return ResponseEntity.ok(resource);
    }

    // Atualiza um lembrete existente
    @Operation(summary = "Atualizar lembrete", description = "Atualiza um lembrete com base no ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<LembreteDTO>> updateLembrete(
            @PathVariable Long id,
            @RequestBody Lembrete updatedLembrete) {
        Lembrete updatedLembreteEntity = lembreteService.updateLembrete(id, updatedLembrete);
        LembreteDTO lembreteDTO = convertToDto(updatedLembreteEntity);

        EntityModel<LembreteDTO> resource = EntityModel.of(lembreteDTO);
        addHateoasLinks(resource, id);

        return ResponseEntity.ok(resource);
    }

    // Exclui um lembrete com base no ID fornecido
    @Operation(summary = "Excluir lembrete", description = "Exclui um lembrete com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLembrete(@PathVariable Long id) {
        lembreteService.deleteLembrete(id);
        return ResponseEntity.noContent().build();
    }

    // Adiciona links HATEOAS dinamicamente
    private void addHateoasLinks(EntityModel<LembreteDTO> resource, Long id) {
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(LembreteController.class).getLembreteById(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(LembreteController.class).getAllLembretes()).withRel("all-lembretes"));
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(LembreteController.class).deleteLembrete(id)).withRel("delete-lembrete"));
    }

    // Converte um objeto Lembrete para LembreteDTO
    private LembreteDTO convertToDto(Lembrete lembrete) {
        LembreteDTO lembreteDTO = new LembreteDTO();
        lembreteDTO.setId(lembrete.getId());
        lembreteDTO.setAcao(lembrete.getAcao());
        lembreteDTO.setHorario(lembrete.getHorario());
        return lembreteDTO;
    }

    // Lista todos os lembretes
    @Operation(summary = "Listar lembretes", description = "Obtém uma lista de todos os lembretes.")
    @GetMapping
    public ResponseEntity<List<EntityModel<LembreteDTO>>> getAllLembretes() {
        // Obtém todos os lembretes do serviço
        List<Lembrete> lembretes = lembreteService.getAllLembretes();

        // Converte para DTO e mapeia para EntityModel com HATEOAS
        List<EntityModel<LembreteDTO>> resources = lembretes.stream()
                .map(lembrete -> {
                    LembreteDTO lembreteDTO = convertToDto(lembrete);  // Converte Lembrete para LembreteDTO
                    EntityModel<LembreteDTO> resource = EntityModel.of(lembreteDTO);
                    addHateoasLinks(resource, lembrete.getId());  // Adiciona links HATEOAS
                    return resource;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);  // Retorna os lembretes com links HATEOAS
    }


}
