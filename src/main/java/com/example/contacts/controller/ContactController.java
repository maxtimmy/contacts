package com.example.contacts.controller;

import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository repo;

    public ContactController(ContactRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("contacts", repo.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "form";
    }

    @PostMapping
    public String create(@ModelAttribute Contact contact) {
        repo.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        repo.findById(id).ifPresent(c -> model.addAttribute("contact", c));
        return "form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Contact contact) {
        contact.setId(id);
        repo.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/contacts";
    }
}