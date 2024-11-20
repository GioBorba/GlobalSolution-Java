package com.example.globalsolution.util;

import com.example.globalsolution.model.ControleEnergetico;
import com.example.globalsolution.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ConsumoEnergeticoGenerator {

    // Método que gera o consumo energético aleatório dentro de uma faixa (100 a 400 kWh)
    public ControleEnergetico generateRandomConsumo(Usuario usuario) {
        Random random = new Random();
        // Gera um consumo aleatório entre 100 kWh e 400 kWh
        double consumo = 100 + (400 - 100) * random.nextDouble();

        // Cria o objeto ControleEnergetico e associa ao usuário
        ControleEnergetico consumoEnergetico = new ControleEnergetico();
        consumoEnergetico.setUsuario(usuario);  // Associa o usuário ao consumo gerado
        consumoEnergetico.setConsumo(consumo);  // Define o consumo gerado

        return consumoEnergetico;  // Retorna o objeto ControleEnergetico pronto
    }

}
