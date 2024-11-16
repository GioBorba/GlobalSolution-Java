package com.example.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "T_GS_CONTROLE_ENERGETICO")
public class ControleEnergetico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_controleEnergetico")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "consumo", nullable = false)
    @Min(value = 0, message = "Consumo de energia não pode ser negativo")
    private Double consumo;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro; // Data e hora do registro do consumo

    // Método que define automaticamente a data antes de persistir no banco
    @PrePersist
    public void prePersist() {
        if (this.dataRegistro == null) {
            this.dataRegistro = LocalDateTime.now(); // Define o momento atual
        }
    }


}
