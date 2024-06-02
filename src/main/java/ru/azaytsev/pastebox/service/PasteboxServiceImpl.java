package ru.azaytsev.pastebox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azaytsev.pastebox.api.request.PasteboxRequest;
import ru.azaytsev.pastebox.api.request.PublicStatus;
import ru.azaytsev.pastebox.api.response.PasteboxResponse;
import ru.azaytsev.pastebox.api.response.PasteboxUrlResponse;
import ru.azaytsev.pastebox.model.PasteboxEntity;
import ru.azaytsev.pastebox.repository.PasteboxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasteboxServiceImpl implements PasteboxService {

    private final String host = "http://abc.com";
    private final int publicListSize = 10;

    private final PasteboxRepository repository;

    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteBoxes() {

       List<PasteboxEntity> list = repository.getListOfPublicAndALive(publicListSize);

       return list.stream().map(pasteboxEntity ->
               new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic()))
               .collect(Collectors.toList());
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {

        int hash = generateId();
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setId(hash);
        pasteboxEntity.setHash(Integer.toHexString(hash));
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteboxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteboxEntity);

        return new PasteboxUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
