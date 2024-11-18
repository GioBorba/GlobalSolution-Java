package com.example.globalsolution.DTO;

import lombok.Data;

@Data
public class AlertaDTO {
    private Long id;
    private Long usuarioId;
    private String mensagem;
}
