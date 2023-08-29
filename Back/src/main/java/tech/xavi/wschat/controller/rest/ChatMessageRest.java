package tech.xavi.wschat.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.wschat.service.room.ChatRoomMessageService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatMessageRest {

    private final ChatRoomMessageService messageService;

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessageHistory(@PathVariable String roomId){
        return ResponseEntity.ok().body(
                messageService.getMessageHistory(roomId)
        );
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck(){
        return ResponseEntity.ok().body(LocalDate.now());
    }
}
