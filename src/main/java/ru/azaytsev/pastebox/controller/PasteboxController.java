package ru.azaytsev.pastebox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.azaytsev.pastebox.api.request.PasteboxRequest;
import ru.azaytsev.pastebox.api.response.PasteboxResponse;
import ru.azaytsev.pastebox.api.response.PasteboxUrlResponse;
import ru.azaytsev.pastebox.service.PasteboxService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class PasteboxController {

    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteBox() {

        return pasteboxService.getFirstPublicPasteBoxes();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {

        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest pasteboxRequest) {
        return pasteboxService.create(pasteboxRequest);
    }
}