package com.example.springbootsindb.controller;

import com.example.springbootsindb.entity.Foto;
import com.example.springbootsindb.service.BlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    BlobService blobService;

    @GetMapping({"/","/pagina1"})
    public String main(){
        return "pagina1";
    }

    @GetMapping("/pagina2")
    public String pagina2(){
        return "pagina2";
    }

    @PostMapping("/guardar")
    public String guardarFoto(@RequestParam("imagen") MultipartFile img, Model model,
                              RedirectAttributes a, HttpSession session){

        switch (img.getContentType()) {

            case "image/jpeg":
            case "image/png":
            case "application/octet-stream":
                break;
            default:

                model.addAttribute("err", "Solo se deben de enviar imágenes");

                return "pagina1";
        }

        if (img.getOriginalFilename().length() != 0) {
            try {
                Foto fotoPerfil = new Foto();

                // Guardado en el Contenedor de archivos
                //MultipartFile newImg = blobService.formatearArchivo(img, "usuario");

                if (blobService.subirArchivo(img)) {
                    fotoPerfil.setRuta(blobService.obtenerUrl(img.getOriginalFilename()));
                }

                session.setAttribute("fotoPerfil", fotoPerfil.getRuta());

            } catch (Exception e) {
                e.getMessage();
                System.out.println(e.getMessage());
                a.addFlashAttribute("msg", -2);
                return "redirect:/perfil";
            }
        }
        return "pagina2";
    }
}
