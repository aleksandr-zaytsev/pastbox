package ru.azaytsev.pastebox.controller;

import org.springframework.web.bind.annotation.*;
import ru.azaytsev.pastebox.api.request.PasteBoxRequest;

import java.util.Collection;
import java.util.Collections;

@RestController
public class PasteBoxController {

    @GetMapping("/")
    public Collection<String> getPublicPasteBox() {
        return Collections.emptyList();
    }

    @GetMapping("/{hash}")
    public String getByHash(@PathVariable String hash) {
        return hash;
    }

    @PostMapping("/")
    public String add(@RequestBody PasteBoxRequest pasteBoxRequest) {
        return pasteBoxRequest.getData();
    }
}