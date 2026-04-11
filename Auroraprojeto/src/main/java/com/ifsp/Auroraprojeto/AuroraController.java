package com.ifsp.Auroraprojeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuroraController {

    @Autowired
    private UsuarioService usuarioService;


    // ================= LOGIN =================

    @GetMapping("/login")
    public String telaLogin(HttpSession session) {

        // se já estiver logado, vai direto para início
        if (usuarioLogado(session)) {
            return "redirect:/inicio";
        }

        return "Login";
    }

      @PostMapping("/login")
      public String login(@RequestParam String email,
                    @RequestParam String senha,
                    HttpSession session) {

    Usuario usuario = usuarioService.login(email, senha);

    if (usuario != null) {
        session.setAttribute("usuario", usuario);
        return "redirect:/inicio";
    }

    return "redirect:/login?erro=true";
}


    // ================= CADASTRO =================

    @GetMapping("/cadastro")
    public String telaCadastro(HttpSession session) {

        if (usuarioLogado(session)) {
            return "redirect:/inicio";
        }

        return "Cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@ModelAttribute Usuario usuario,
                           HttpSession session) {

        boolean sucesso = usuarioService.cadastrar(usuario);

        if (sucesso) {

            // cria sessão automaticamente após cadastro
            session.setAttribute("usuario", usuario);

            return "redirect:/inicio";
        }

        return "redirect:/cadastro?erro=email";
    }


    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }


    // ================= DASHBOARD =================

    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaInicio";
    }


    @GetMapping("/disciplinas")
    public String disciplinas(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaDisciplinas";
    }


    @GetMapping("/exercicios")
    public String exercicios(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaExercicios";
    }


    @GetMapping("/provas")
    public String provas(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaProvas";
    }


    @GetMapping("/material")
    public String material(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaMaterialExtra";
    }


    @GetMapping("/perfil")
    public String perfil(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "PerfilAluno";

    }

    private boolean usuarioLogado(HttpSession session) {
    return session.getAttribute("usuario") != null;
   }
   
}