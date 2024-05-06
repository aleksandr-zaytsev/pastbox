package ru.azaytsev.pastebox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.azaytsev.pastebox.api.request.PasteboxRequest;
import ru.azaytsev.pastebox.api.request.PublicStatus;
import ru.azaytsev.pastebox.api.response.PasteboxResponse;
import ru.azaytsev.pastebox.api.response.PasteboxUrlResponse;
import ru.azaytsev.pastebox.model.PasteboxEntity;
import ru.azaytsev.pastebox.repository.PasteboxRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteboxServiceImpl implements PasteboxService {

    private String host = "http://abc.com";
    private int publicListSize = 10;

    private final PasteboxRepository repository;

    private AtomicInteger idGenerator = new AtomicInteger(0);

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
        repository.add(pasteboxEntity);

        return new PasteboxUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
