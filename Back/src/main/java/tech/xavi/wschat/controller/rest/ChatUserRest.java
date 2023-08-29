package tech.xavi.wschat.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.wschat.model.user.RoomUsersRes;
import tech.xavi.wschat.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatUserRest {

    private final UserService userService;

    @GetMapping("/{roomId}/users")
    public ResponseEntity<RoomUsersRes> getRoomStatus(@PathVariable String roomId){
        return ResponseEntity.ok().body(
                userService.getRoomUsers(roomId)
        );
    }
}
