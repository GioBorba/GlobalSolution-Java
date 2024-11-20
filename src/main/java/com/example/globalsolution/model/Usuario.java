package com.example.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "T_GS_USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private Long id;

    @Column(name = "nome")
    @NotBlank
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email inválido")
    @NotBlank
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank
    @Size(min= 4)
    private String senha;

    @Column(name = "unidade")
    private String unidade; // Exemplo: 'Apartamento 103'

    @OneToMany(mappedBy = "usuario")
    private List<Lembrete> lembretes;  // Lista de lembretes associados a este usuário

    @OneToMany(mappedBy = "usuario")
    private List<ControleEnergetico> consumosEnergeticos;  // Lista de consumos energéticos do usuário


}
