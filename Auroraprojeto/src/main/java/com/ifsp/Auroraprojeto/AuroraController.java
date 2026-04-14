package com.ifsp.Auroraprojeto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuroraController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConteudoRepository conteudoRepository;


    // ================= LOGIN =================

    @GetMapping("/login")
    public String telaLogin(HttpSession session) {

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


    // ================= PROVAS =================

    @GetMapping("/provas")
    public String provas(HttpSession session, Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> provas = conteudoRepository.findByTipo(TipoConteudo.PROVA);

        model.addAttribute("provas", provas);

        return "TelaProvas";
    }


    // ================= MATERIAL EXTRA =================

    @GetMapping("/material")
    public String material(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "TelaMaterialExtra";
    }


    // ================= PERFIL =================

    @GetMapping("/perfil")
    public String perfil(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "PerfilAluno";
    }

    // ================= ADMIN =================

@GetMapping("/admin")
public String admin(HttpSession session) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    return "admin-dashboard";
}


// ================= GERENCIAR AULAS =================

@GetMapping("/admin/conteudo")
public String gerenciarAulas(HttpSession session, Model model) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    List<Conteudo> aulas = conteudoRepository.findByTipo(TipoConteudo.VIDEO);

    model.addAttribute("aulas", aulas);

    return "gerenciar-aulas";
}


// ================= NOVO CONTEÚDO =================

@GetMapping("/admin/conteudo/novo")
public String novoConteudo(HttpSession session) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    return "admin-dashboard";
}


// ================= EXCLUIR AULA =================

@GetMapping("/admin/excluir/{id}")
public String excluirAula(@PathVariable Long id, HttpSession session) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    conteudoRepository.deleteById(id);

    return "redirect:/admin/conteudo";
}


    // ================= VERIFICA SESSÃO =================

    private boolean usuarioLogado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }

}