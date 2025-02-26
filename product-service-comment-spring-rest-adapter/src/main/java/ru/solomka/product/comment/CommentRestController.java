package ru.solomka.product.comment;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.product.common.cqrs.CommandHandler;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentRestController {


    @NonNull
    CommandHandler<>

    @GetMapping(value = "/{productId}", produces = "application/json")
    public List<CommentEntity> getCommentsByProductId(@PathVariable("productId") UUID productId) {

    }


}
