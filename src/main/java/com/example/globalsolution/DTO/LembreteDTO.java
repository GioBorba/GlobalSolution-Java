package com.example.globalsolution.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LembreteDTO {
    private Long id;
    private String acao;
    private LocalDateTime horario;
}
