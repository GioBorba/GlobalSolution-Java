package com.example.globalsolution.controller;

import com.example.globalsolution.model.Alerta;
import com.example.globalsolution.DTO.AlertaDTO;
import com.example.globalsolution.service.AlertaService;
import com.example.globalsolution.service.UsuarioService;
import com.example.globalsolution.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Gestão de Alertas", description = "APIs para gerenciamento de alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private UsuarioService usuarioService;

    // Cria um alerta para o usuário
    @Operation(summary = "Criar alerta", description = "Cria um alerta para o usuário baseado no seu consumo de energia.")
    @PostMapping("/gerar")
    public ResponseEntity<EntityModel<AlertaDTO>> gerarAlerta(@RequestParam Long usuarioId, @RequestParam double consumoAtual) {
        Usuario usuario = usuarioService.getUserById(usuarioId);  // Obtém o usuário
        if (usuario == null) {
            return ResponseEntity.badRequest().build();  // Retorna erro caso o usuário não exista
        }

        Alerta alerta = alertaService.gerarAlerta(usuario, consumoAtual);  // Chama o serviço para gerar o alerta

        if (alerta == null) {
            return ResponseEntity.noContent().build();  // Se o alerta não foi gerado (consumo não estava alto), retorna no content
        }

        // Converte o alerta para DTO
        AlertaDTO alertaDTO = new AlertaDTO();
        alertaDTO.setId(alerta.getId());
        alertaDTO.setUsuarioId(alerta.getUsuario().getId());
        alertaDTO.setMensagem(alerta.getMensagem());

        // Cria o recurso com HATEOAS
        EntityModel<AlertaDTO> resource = EntityModel.of(alertaDTO);
        addHateoasLinks(resource, alerta.getId());

        return ResponseEntity.ok(resource);
    }

    // Lista todos os alertas
    @Operation(summary = "Listar alertas", description = "Obtém uma lista de todos os alertas.")
    @GetMapping
    public ResponseEntity<List<EntityModel<AlertaDTO>>> getAllAlertas() {
        List<Alerta> alertas = alertaService.getAllAlertas();  // Obtém todos os alertas

        List<EntityModel<AlertaDTO>> resources = alertas.stream()
                .map(alerta -> {
                    AlertaDTO alertaDTO = new AlertaDTO();
                    alertaDTO.setId(alerta.getId());
                    alertaDTO.setUsuarioId(alerta.getUsuario().getId());
                    alertaDTO.setMensagem(alerta.getMensagem());

                    EntityModel<AlertaDTO> resource = EntityModel.of(alertaDTO);
                    addHateoasLinks(resource, alerta.getId());
                    return resource;
                })
                .toList();

        return ResponseEntity.ok(resources);
    }

    // Adiciona links HATEOAS
    private void addHateoasLinks(EntityModel<AlertaDTO> resource, Long id) {
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(AlertaController.class).gerarAlerta(null, 0)).withSelfRel());  // Link para o próprio alerta
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(AlertaController.class).getAllAlertas()).withRel("all-alertas"));  // Link para todos os alertas
    }
}
