package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.Service.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

import static ru.practicum.shareit.request.controller.CustomHeaders.X_SHARER_USER_ID;

@RestController
@Validated
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDto saveItemRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                   @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.saveItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    List<ItemRequestDto> getAllByUserId(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemRequestService.getAllByUserId(userId);
    }

    @GetMapping("/all")
    List<ItemRequestDto> getAllRequests(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "20") int size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    ItemRequestDto getRequestById(@RequestHeader(X_SHARER_USER_ID) Long userId, @PathVariable Long requestId) {
        return itemRequestService.getRequestById(requestId, userId);
    }
}
