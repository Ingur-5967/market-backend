package ru.solomka.product.card;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solomka.product.card.cqrs.command.PutImageCardCommand;
import ru.solomka.product.card.cqrs.query.GetImageCardByIdQuery;
import ru.solomka.product.common.cqrs.CommandHandler;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/product/source")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardViewRestController {

    @NonNull CommandHandler<PutImageCardCommand, CardViewEntity> putImageCardCommandHandler;

    @NonNull CommandHandler<GetImageCardByIdQuery, byte[]> getImageCardByIdQueryHandler;

    @SneakyThrows
    @GetMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<byte[]> getCardFileSource(@PathVariable("productId") UUID id) {
        byte[] image = getImageCardByIdQueryHandler.handle(new GetImageCardByIdQuery(id));
        return ResponseEntity.ok(image);
    }

    @SneakyThrows
    @PostMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<CardViewEntity> getCardFileSource(@PathVariable("productId") UUID id,
                                                  @RequestBody MultipartFile file) {
        CardViewEntity cardViewEntity = putImageCardCommandHandler.handle(new PutImageCardCommand(id, file.getBytes()));
        return ResponseEntity.ok(cardViewEntity);
    }
}