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

    // ================= LOGIN ALUNO =================
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

    // ================= LOGIN ADMIN (TELA E LÓGICA) =================
    @GetMapping("/login-admin")
    public String loginAdmin(HttpSession session) {
        if (adminLogado(session)) return "redirect:/admin";
        return "login-admin";
    }

    @PostMapping("/admin/autenticar")
    public String autenticarAdmin(@RequestParam String usuario, 
                                 @RequestParam String senha, 
                                 HttpSession session, 
                                 Model model) {
        if (ADMIN_USER.equals(usuario) && ADMIN_PASS.equals(senha)) {
            session.setAttribute("isAdmin", true);
            return "redirect:/admin";
        }
        model.addAttribute("erro", "Credenciais de administrador inválidas!");
        return "login-admin";
    }

    // ================= ÁREA ADMINISTRATIVA (PROTEGIDA) =================
    
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

    @GetMapping("/admin/provas")
    public String adminProvas(HttpSession session) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        return "upload-provas";
    }

    @GetMapping("/admin/materiais")
    public String adminMateriais(HttpSession session) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        return "materiais-extras";
    }

    @PostMapping("/salvarconteudo")
    public String salvarConteudo(@ModelAttribute Conteudo conteudo, HttpSession session) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        conteudoRepository.save(conteudo);
        return "redirect:/admin/conteudo";
    }

    @GetMapping("/admin/excluir/{id}")
    public String excluirAula(@PathVariable Long id, HttpSession session) {
        if (!adminLogado(session)) return "redirect:/login-admin";
        conteudoRepository.deleteById(id);
        return "redirect:/admin/conteudo";
    }

    // ================= DASHBOARD ALUNO =================
    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "TelaInicio";
    }

    // ================= DISCIPLINAS / OUTROS =================
    @GetMapping("/disciplinas")
    public String disciplinas(HttpSession session, Model model) {
        if (!usuarioLogado(session)) return "redirect:/login";
        model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        return "TelaDisciplinas";
    }

    // ... (Mantenha seus outros métodos de disciplinas aqui) ...

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Limpa login de aluno e admin ao mesmo tempo
        return "redirect:/login";
    }

    // ================= VERIFICAÇÕES DE SESSÃO =================
    private boolean usuarioLogado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }

    private boolean adminLogado(HttpSession session) {
        return session.getAttribute("isAdmin") != null;
    }
}