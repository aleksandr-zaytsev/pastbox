package ru.azaytsev.pastebox.api.response;

import lombok.Data;
import ru.azaytsev.pastebox.api.request.PublicStatus;

@Data
public class PasteboxResponse {
    private final String data;
    private final boolean isPublic;
}
