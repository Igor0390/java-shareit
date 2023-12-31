package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.Create;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.Update;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;

import static ru.practicum.shareit.CustomHeaders.SHARER_USER_ID;

@Controller
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    ResponseEntity<Object> saveItem(@RequestHeader(SHARER_USER_ID) Long userId, @RequestBody @Validated(Create.class) ItemDto itemDto) {
        log.info("Create item {}", itemDto);
        return itemClient.saveItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    ResponseEntity<Object> updateItem(@Validated(Update.class) @PathVariable Long itemId, @RequestHeader(SHARER_USER_ID) Long userId,
                                      @RequestBody ItemDto itemDto) {
        log.info("Update item {}", itemId);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ResponseEntity<Object> getItemById(@PathVariable Long itemId, @RequestHeader(SHARER_USER_ID) Long userId) {
        log.info("Get item by userId={}", userId);
        return itemClient.getItemById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemByUserId(@RequestHeader(SHARER_USER_ID) Long userId,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get all items from user {}", userId);
        return itemClient.getItemByUserId(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemByText(@RequestParam String text,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Search items by text {}", text);
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            return itemClient.getItemByText(text, from, size);
        }
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> saveComment(@RequestHeader(SHARER_USER_ID) Long userId, @PathVariable Long itemId,
                                              @RequestBody @Valid CommentDto commentDto) {
        log.info("Create comment from user {}", userId);
        return itemClient.saveComment(userId, itemId, commentDto);
    }
}