package com.example.globalsolution.service;

import com.example.globalsolution.model.Alerta;
import com.example.globalsolution.model.Usuario;
import com.example.globalsolution.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        double mediaBrasileira = 300.0; // Exemplo de média de consumo

        // Verifica se o consumo está acima da média
        if (consumoAtual > mediaBrasileira) {
            // Cria a mensagem de alerta com uma dica aleatória
            String mensagem = "Seu consumo energético está acima do esperado. Tente reduzir seus gastos.";
            String dicaEscolhida = DICAS.get(new Random().nextInt(DICAS.size()));
            mensagem += " Dica: " + dicaEscolhida;

            // Cria o alerta
            Alerta alerta = new Alerta();
            alerta.setUsuario(usuario);
            alerta.setMensagem(mensagem);

            // Salva o alerta no banco de dados
            return alertaRepository.save(alerta);
        }
        return null; // Não gera alerta se o consumo não estiver acima da média
    }

    // Método para obter todos os alertas
    public List<Alerta> getAllAlertas() {
        return alertaRepository.findAll();
    }

    // Método para obter um alerta específico pelo ID
    public Alerta getAlertaById(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alerta não encontrado com ID: " + id));
    }

  // Método para obter Alertas de um usuario
    public List<Alerta> getAlertasPorUsuario(Long usuarioId) {
        return alertaRepository.findByUsuarioId(usuarioId);
    }

}
