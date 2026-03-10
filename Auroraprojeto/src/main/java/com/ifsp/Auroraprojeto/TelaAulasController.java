package com.ifsp.Auroraprojeto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TelaAulasController {

    @GetMapping("/tela-aulas")
     public String telaAulas(){
        return "TelaAulas.html";
     } 
    
}
