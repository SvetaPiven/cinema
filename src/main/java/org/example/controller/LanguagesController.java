package org.example.controller;

import org.example.entity.dto.LanguageDto;
import org.example.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguagesController {

    private final LanguageService languageService;

    @Autowired
    public LanguagesController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDto>> getAllLanguages() {
        return ResponseEntity.status(HttpStatus.OK).body(languageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> findLanguageById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(languageService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody LanguageDto languageDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(languageService.create(languageDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDto> updateLanguage(@PathVariable Long id, @RequestBody LanguageDto languageDto) {
        return ResponseEntity.status(HttpStatus.OK).body(languageService.update(languageDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLanguageById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Language deleted");
    }
}