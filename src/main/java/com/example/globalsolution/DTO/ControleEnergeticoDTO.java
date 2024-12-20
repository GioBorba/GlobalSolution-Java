package com.example.globalsolution.DTO;

import com.example.globalsolution.model.Usuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ControleEnergeticoDTO {
    private Long id;
    private Long usuarioId;
    private Double consumo;
    private LocalDateTime dataRegistro;
}
