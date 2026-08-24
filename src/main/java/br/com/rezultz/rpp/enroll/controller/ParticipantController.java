package br.com.rezultz.rpp.enroll.controller;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.message.producer.ParticipantProducerRabbit;
import br.com.rezultz.rpp.enroll.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participant")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final ParticipantProducerRabbit participantProducerRabbit;

    @GetMapping
    public ResponseEntity<List<Participant>> list(){
        return ResponseEntity.ok(participantService.list());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Participant>> listAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(participantService.listAll(pageable));
    }

    @PostMapping("/rabbit")
    public ResponseEntity<Void> createRabbit(@RequestBody ParticipantCreateMessage participantCreateMessage){
        participantProducerRabbit.sendCreateParticipant(participantCreateMessage);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/rabbit")
    public ResponseEntity<Void> updateName(@RequestBody ParticipantCreateMessage participantCreateMessage){
        participantProducerRabbit.sendUpdateNameParticipant(participantCreateMessage);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/rabbit")
    public ResponseEntity<Void> logicDelete(@RequestBody ParticipantCreateMessage participantCreateMessage){
        participantProducerRabbit.sendDeleteParticipant(participantCreateMessage);
        return ResponseEntity.accepted().build();
    }

}
