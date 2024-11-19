package com.example.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "T_GS_ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Usuário que recebe o alerta

    @Column(name = "mensagem", nullable = false)
    @NotBlank(message = "Mensagem não pode ser vazia")
    private String mensagem;  // A mensagem do alerta. ex.: "Seu consumo energético está acima do esperado. Tente (determinadas ações) para evitar mais gastos."

}
