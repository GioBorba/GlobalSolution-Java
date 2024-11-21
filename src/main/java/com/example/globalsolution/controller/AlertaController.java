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
        Usuario usuario = usuarioService.getUserById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        Alerta alerta = alertaService.gerarAlerta(usuario, consumoAtual);

        if (alerta == null) {
            return ResponseEntity.noContent().build();
        }

        AlertaDTO alertaDTO = new AlertaDTO();
        alertaDTO.setId(alerta.getId());
        alertaDTO.setUsuarioId(alerta.getUsuario().getId());
        alertaDTO.setMensagem(alerta.getMensagem());

        EntityModel<AlertaDTO> resource = EntityModel.of(alertaDTO);
        addHateoasLinks(resource, alerta.getId());

        return ResponseEntity.ok(resource);
    }

    // Obtém todos os alertas
    @Operation(summary = "Listar alertas", description = "Obtém uma lista de todos os alertas.")
    @GetMapping
    public ResponseEntity<List<EntityModel<AlertaDTO>>> getAllAlertas() {
        List<Alerta> alertas = alertaService.getAllAlertas();

        if (alertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

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

    // Obtém um alerta específico por ID
    @Operation(summary = "Obter alerta por ID", description = "Obtém os detalhes de um alerta específico pelo seu ID.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlertaDTO>> getAlertaById(@PathVariable Long id) {
        Alerta alerta = alertaService.getAlertaById(id);

        AlertaDTO alertaDTO = new AlertaDTO();
        alertaDTO.setId(alerta.getId());
        alertaDTO.setUsuarioId(alerta.getUsuario().getId());
        alertaDTO.setMensagem(alerta.getMensagem());

        EntityModel<AlertaDTO> resource = EntityModel.of(alertaDTO);
        addHateoasLinks(resource, id);

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Listar alertas de um usuário", description = "Obtém todos os alertas de um usuário específico. Retorna uma mensagem caso o usuário não tenha alertas.")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getAlertasPorUsuario(@PathVariable Long usuarioId) {
        // Busca todos os alertas do usuário
        List<Alerta> alertas = alertaService.getAlertasPorUsuario(usuarioId);

        if (alertas.isEmpty()) {
            // Caso o usuário não tenha alertas, retorna a mensagem
            return ResponseEntity.ok("Esse usuário não recebeu nenhum alerta");
        }

        // Mapeia os alertas para DTOs
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

        // Retorna os alertas do usuário
        return ResponseEntity.ok(resources);
    }


    // Adiciona links HATEOAS
    private void addHateoasLinks(EntityModel<AlertaDTO> resource, Long id) {
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(AlertaController.class).getAllAlertas()).withRel("all-alertas"));
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(AlertaController.class).gerarAlerta(null, 0)).withRel("create-alerta"));
        resource.add(WebMvcLinkBuilder.linkTo(
                methodOn(AlertaController.class).getAlertaById(id)).withSelfRel());
    }
}
