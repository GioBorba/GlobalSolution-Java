package com.example.globalsolution.service;

import com.example.globalsolution.model.Alerta;
import com.example.globalsolution.model.Usuario;
import com.example.globalsolution.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    // Lista de dicas predefinidas
    private static final List<String> DICAS = Arrays.asList(
            "Desligue aparelhos eletrônicos quando não estiverem em uso.",
            "Use lâmpadas LED, que consomem menos energia.",
            "Evite deixar aparelhos em stand-by.",
            "Mantenha os eletrodomésticos limpos e bem conservados.",
            "Considere trocar por eletrodomésticos mais eficientes."
    );

    // Método para gerar alerta baseado no consumo do usuário
    public Alerta gerarAlerta(Usuario usuario, double consumoAtual) {
        double mediaBrasileira = 300.0;  // Exemplo da média do consumo de energia

        // Verifica se o consumo do usuário está acima da média
        if (consumoAtual > mediaBrasileira) {
            // Gera a mensagem de alerta
            String mensagem = "Seu consumo energético está acima do esperado. Tente reduzir seus gastos.";

            // Escolhe uma dica aleatória da lista predefinida
            String dicaEscolhida = DICAS.get(new Random().nextInt(DICAS.size()));

            // Complementa a mensagem com a dica escolhida
            mensagem += " Dica: " + dicaEscolhida;

            // Cria o alerta
            Alerta alerta = new Alerta();
            alerta.setUsuario(usuario);
            alerta.setMensagem(mensagem);

            // Salva o alerta no banco de dados
            return alertaRepository.save(alerta);
        }
        return null;  // Se o consumo não estiver acima da média, não gera alerta
    }

    // Método para obter todos os alertas
    public List<Alerta> getAllAlertas() {
        return alertaRepository.findAll();
    }
}
