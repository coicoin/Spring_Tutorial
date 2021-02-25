package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookValueToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;


@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String showBooks(@RequestParam(value = "sortObject", required = false) String sortObject,
                            @RequestParam(value = "desc", required = false) String desc,
                            Model model) throws Exception {
        logger.info(this.toString());
        logger.info("sortObject -> " + sortObject + ", desc -> " + desc);
        model.addAttribute("book", new Book());
        model.addAttribute("bookValueToRemove", new BookValueToRemove());
        model.addAttribute("sortedBookList", bookService.getAllSortedBooks(sortObject, desc));
        model.addAttribute("files", bookService.getFiles());

        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookValueToRemove", new BookValueToRemove());
            model.addAttribute("sortedBookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookValueToRemove bookValueToRemove, BindingResult bindingResult, Model model,
                             @RequestParam(value = "selectedTypeToRemove", required = false) String selectedTypeToRemove) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("sortedBookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            logger.info(selectedTypeToRemove + " " + bookValueToRemove.getValueToRemove());
            bookService.removeBookByOption(selectedTypeToRemove, bookValueToRemove.getValueToRemove());
            return "redirect:/books/shelf";

        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdir();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at: " + serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }

    @PostMapping("/download")
    public String downloadFile(@RequestParam(value = "filePath", required = false) String filePath) throws Exception {
        if (filePath != null) {
            File sourceFile = new File(filePath);

            if(sourceFile.exists()) {
                String rootPath = System.getProperty("catalina.home");
                File destinationDir = new File(rootPath + File.separator + "local_downloads");
                File destinationFile = new File(destinationDir.getAbsolutePath() + File.separator + sourceFile.getName());

                if (!destinationDir.exists()) {
                    destinationDir.mkdir();
                }

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinationFile));
                FileCopyUtils.copy(bis, bos);
                bis.close();
                bos.close();

                logger.info("file was downloaded to local folder: " + destinationFile.getAbsolutePath());
            }
        }
        return "redirect:/books/shelf";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public String handleError(Model model, FileNotFoundException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "redirect:/books/shelf";
    }

}
