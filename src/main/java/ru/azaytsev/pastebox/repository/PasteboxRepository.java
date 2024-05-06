package ru.azaytsev.pastebox.repository;

import ru.azaytsev.pastebox.model.PasteboxEntity;

import java.util.List;

public interface PasteboxRepository {

PasteboxEntity getByHash(String hash);

List<PasteboxEntity> getListOfPublicAndALive(int amount);

void add(PasteboxEntity pasteboxEntity);

}
