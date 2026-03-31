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

// --- ROTAS DO ADMINISTRADOR ---

    @GetMapping("/admin")
    public String adminPrincipal() {
        return "admin-dashboard"; // Abre o arquivo admin.html
    }

    @GetMapping("/admin/conteudo")
    public String adminConteudo() {
        return "gerenciar-aulas"; // Abre o arquivo gerenciar-aulas.html
    }

    @GetMapping("/admin/provas")
    public String adminProvas() {
        return "upload-provas"; // Abre o arquivo upload-provas.html
    }

    @GetMapping("/admin/materiais")
    public String adminMateriais() {
        return "materiais-extras"; // Abre o arquivo materiais-extras.html
    }
}