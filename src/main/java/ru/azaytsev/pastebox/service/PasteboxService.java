package ru.azaytsev.pastebox.service;

import ru.azaytsev.pastebox.api.request.PasteboxRequest;
import ru.azaytsev.pastebox.api.response.PasteboxResponse;
import ru.azaytsev.pastebox.api.response.PasteboxUrlResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);

    List<PasteboxResponse> getFirstPublicPasteBoxes();

    PasteboxUrlResponse create(PasteboxRequest request);
}
