package tech.xavi.wschat.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xavi.wschat.model.join.JoinInfo;
import tech.xavi.wschat.model.join.JoinReq;
import tech.xavi.wschat.model.setup.AppConfigurationRes;
import tech.xavi.wschat.model.setup.CreateRoomReq;
import tech.xavi.wschat.model.setup.UserStorageRes;
import tech.xavi.wschat.service.room.ChatRoomConfigurationService;
import tech.xavi.wschat.service.room.ChatRoomCreationService;
import tech.xavi.wschat.service.room.ChatRoomJoinService;
import tech.xavi.wschat.service.room.ChatRoomStatusService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chat")
public class ChatSetupRest {

    private final ChatRoomCreationService chatRoomCreationService;
    private final ChatRoomConfigurationService chatRoomConfigurationService;
    private final ChatRoomStatusService chatRoomStatusService;
    private final ChatRoomJoinService chatRoomJoinService;

    @PostMapping("create")
    public ResponseEntity<UserStorageRes> createNewRoom(@RequestBody CreateRoomReq createRoomReq){
        return new ResponseEntity<>(
                chatRoomCreationService.createNewRom(createRoomReq),
                HttpStatus.CREATED
        );
    }

    @GetMapping("configuration")
    public ResponseEntity<AppConfigurationRes> getAppConfiguration(){
        return new ResponseEntity<>(
                chatRoomConfigurationService.getAppConfiguration(),
                HttpStatus.OK
        );
    }

    @GetMapping("{roomId}/join")
    public ResponseEntity<JoinInfo> getRoomInfo(@PathVariable String roomId){
        return new ResponseEntity<>(
                chatRoomStatusService.getJoinInfo(roomId),
                HttpStatus.OK
        );
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<UserStorageRes> joinRoom(@PathVariable String roomId, @RequestBody JoinReq joinRoomReq){
        return new ResponseEntity<>(
                chatRoomJoinService.joinRoom(roomId,joinRoomReq),
                HttpStatus.CREATED
        );
    }
}
