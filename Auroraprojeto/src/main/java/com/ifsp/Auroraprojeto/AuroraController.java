package com.ifsp.Auroraprojeto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuroraController {

    // Login e Cadastro
    @GetMapping("/login")
    public String login() {
        return "Login"; // Verifique se o arquivo se chama Login.html
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "Cadastro"; // Se o erro 500 continuar, mude para "cadastro" e renomeie o arquivo
    }

    // Telas do Dashboard (Roxas)
    @GetMapping("/inicio")
    public String inicio() {
        return "TelaInicio"; // Ajustado para o nome que aparece na sua pasta de templates
    }

    @GetMapping("/disciplinas")
    public String disciplinas() {
        return "TelaDisciplinas";
    }

    @GetMapping("/exercicios")
    public String exercicios() {
        return "TelaExercicios";
    }

    @GetMapping("/provas")
    public String provas() {
        return "TelaProvas";
    }

    @GetMapping("/material")
    public String material() {
        return "TelaMaterialExtra";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "PerfilAluno";
    }
    @GetMapping("/aulas")
    public String paginaAulas() {
        // Retorna o nome do arquivo HTML (sem o .html)
        return "TelaAulas";
        
}

@GetMapping("/assistir")
public String assistir() {
    return "TelaAssistir"; // Certifique-se de que o arquivo seja Assistir.html
}
}