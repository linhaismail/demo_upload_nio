package com.example.demo_upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demo Upload
 *
 * @author Vincent
 * @date 2018-05-05 17:19
 */
@Controller
public class UploadController {

    @RequestMapping("/")
    public String index(){
        return "/upload";
    }

//    @RequestMapping("/uploadResult")
//    public String uploadResult() {
//        return "/uploadResult";
//    }

    private static final String UPLOAD_FOLDER = "D:/uploadFile/";

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "please select a file to upload");
            return "redirect:uploadResult";
        }

        try {

            Path uploadFolderPath = Paths.get(UPLOAD_FOLDER);
            if (!Files.exists(uploadFolderPath)) {
                Files.createDirectory(uploadFolderPath);
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message", "You successfully upload '" + file.getOriginalFilename() + "'");
            return "redirect:/uploadResult";

        } catch (IOException e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute("message", "Upload unsuccessfully!");
            return "redirect:/uploadResult";

        }
    }
}
