package com.ifsp.Auroraprojeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuroraController {

    @Autowired
    private UsuarioService usuarioService; // serviço que contém cadastro e login

    // ================= LOGIN =================
    @GetMapping("/login")
    public String telaLogin() {
        return "Login"; // Login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session) {

        Usuario usuario = usuarioService.login(email, senha);

        if (usuario != null) {
            session.setAttribute("usuario", usuario); // salva na sessão
            return "redirect:/inicio"; // usuário logado
        } else {
            return "redirect:/login?erro=true"; // login inválido
        }
    }

    // ================= CADASTRO =================
    @GetMapping("/cadastro")
    public String telaCadastro() {
        return "Cadastro"; // Cadastro.html
    }

    @PostMapping("/cadastro")
    public String cadastro(@ModelAttribute Usuario usuario) {
        boolean sucesso = usuarioService.cadastrar(usuario);

        if (sucesso) {
            return "redirect:/login"; // cadastro feito com sucesso
        } else {
            return "redirect:/cadastro?erro=email"; // email já existe
        }
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // remove sessão
        return "redirect:/login";
    }

    // ================= DASHBOARD =================
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login"; // protege a tela
        }
        return "TelaInicio";
    }

    @GetMapping("/disciplinas")
    public String disciplinas(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "TelaDisciplinas";
    }

    @GetMapping("/exercicios")
    public String exercicios(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "TelaExercicios";
    }

    @GetMapping("/provas")
    public String provas(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "TelaProvas";
    }

    @GetMapping("/material")
    public String material(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "TelaMaterialExtra";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "PerfilAluno";
    }
}