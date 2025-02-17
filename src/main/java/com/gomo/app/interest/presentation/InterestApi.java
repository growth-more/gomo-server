package com.gomo.app.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.application.DeleteInterestUseCase;
import com.gomo.app.interest.application.ReadInterestUseCase;
import com.gomo.app.interest.application.UpdateInterestUseCase;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests")
@Presentation
public class InterestApi {

    private final CreateInterestUseCase createInterestUseCase;
    private final ReadInterestUseCase readInterestUseCase;
    private final UpdateInterestUseCase updateInterestUseCase;
    private final DeleteInterestUseCase deleteInterestUseCase;

    @PostMapping
    public ResponseEntity<CreateInterestResponse> create(@Auth MemberId memberId, @RequestPart CreateInterestRequest request, @RequestPart MultipartFile logo) {
        CreateInterestResponse response = createInterestUseCase.create(RegistrantId.of(memberId.getId()), request, logo);
        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadInterestResponse> find(@PathVariable("id") UUID interestId) {
        ReadInterestResponse response = readInterestUseCase.find(InterestId.of(interestId));
        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ListInterestResponse> list(@Auth MemberId memberId) {
        ListInterestResponse response = readInterestUseCase.findAll(RegistrantId.of(memberId.getId()));
        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Auth MemberId memberId, @PathVariable("id") UUID interestId, @RequestBody UpdateInterestRequest request) {
        updateInterestUseCase.update(memberId.getId(), InterestId.of(interestId), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Auth MemberId memberId, @PathVariable("id") UUID interestId) {
        deleteInterestUseCase.delete(memberId.getId(), InterestId.of(interestId));
        return ResponseEntity.noContent().build();
    }
}
