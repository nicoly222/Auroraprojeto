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

    // CREDENCIAIS DO ADMIN ÚNICO
    private final String ADMIN_USER = "admin_aurora";
    private final String ADMIN_PASS = "aurora123";

    // ================= LOGIN / LOGOUT GERAL =================

    @GetMapping("/login")
    public String telaLogin(HttpSession session) {
        if (usuarioLogado(session)) return "redirect:/inicio";
        return "Login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Usuario usuario = usuarioService.login(email, senha);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/inicio";
        }
        return "redirect:/login?erro=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }

    // ================= ÁREA DO ALUNO (DASHBOARD E PERFIL) =================

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "TelaInicio";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "PerfilAluno";
    }

    @PostMapping("/salvarPerfil")
    public String salvarPerfil(@ModelAttribute Usuario usuarioAtualizado, HttpSession session) {
        if (!usuarioLogado(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setCurso(usuarioAtualizado.getCurso());
        usuario.setCidade(usuarioAtualizado.getCidade());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        usuarioService.salvar(usuario);
        session.setAttribute("usuario", usuario);
        return "redirect:/perfil";
    }

    // ================= NAVEGAÇÃO DO ALUNO (PÁGINAS QUE FALTAVAM) =================

    @GetMapping("/disciplinas")
    public String disciplinas(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "TelaDisciplinas";
    }

    @GetMapping("/exercicios")
    public String exercicios(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "TelaExercicios";
    }

    @GetMapping("/provas")
    public String provas(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        List<Conteudo> provas = conteudoRepository.findByTipo(TipoConteudo.PROVA);
        model.addAttribute("provas", provas);
        return "TelaProvas";
    }

    @GetMapping("/material")
    public String material(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "TelaMaterialExtra";
    }

    // ================= SISTEMA DO ADMIN ÚNICO =================

    @GetMapping("/login-admin")
    public String loginAdmin(HttpSession session) {
        if (adminLogado(session)) return "redirect:/admin";
        return "login-admin";
    }

    @PostMapping("/admin/autenticar")
    public String autenticarAdmin(@RequestParam String usuario, @RequestParam String senha, HttpSession session, Model model) {
        if (ADMIN_USER.equals(usuario) && ADMIN_PASS.equals(senha)) {
            session.setAttribute("isAdmin", true);
            return "redirect:/admin";
        }
        model.addAttribute("erro", "Credenciais de administrador inválidas!");
        return "login-admin";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        return "admin-dashboard";
    }

    @GetMapping("/admin/conteudo")
    public String gerenciarAulas(HttpSession session, Model model) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        List<Conteudo> aulas = conteudoRepository.findByTipo(TipoConteudo.VIDEO);
        model.addAttribute("aulas", aulas);
        return "gerenciar-aulas";
    }

    // ================= MÉTODOS AUXILIARES =================

    private boolean usuarioLogado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }

    private boolean adminLogado(HttpSession session) {
        return session.getAttribute("isAdmin") != null;
    }
}