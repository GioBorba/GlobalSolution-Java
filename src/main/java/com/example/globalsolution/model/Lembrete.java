package com.example.globalsolution.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_GS_LEMBRETE")
@Data
public class Lembrete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acao", nullable = false)
    private String acao; // Mensagem da ação que deverá ser realizada no horário programado pelo usuario

    @Column(name= "horario", nullable = false)
    private LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Um usuario pode estar atrelado a varios lembretes

}
