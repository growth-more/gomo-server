package com.gomo.app.interest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/interests")
@Presentation
public class InterestApi {

    private final CreateInterestUseCase createInterestUseCase;
    private final ReadInterestUseCase readInterestUseCase;
    private final UpdateInterestUseCase updateInterestUseCase;
    private final DeleteInterestUseCase deleteInterestUseCase;

    @PostMapping
    public ResponseEntity<CreateInterestResponse> create(@Auth AuthInfo authInfo, @RequestPart CreateInterestRequest request, @RequestPart MultipartFile logo) {
        CreateInterestResponse response = createInterestUseCase.create(RegistrantId.of(authInfo.getMemberId()), request, logo);
        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadInterestResponse> find(@PathVariable("id") UUID interestId) {
        ReadInterestResponse response = readInterestUseCase.find(InterestId.of(interestId));
        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ListInterestResponse> list(@Auth AuthInfo authInfo) {
        ListInterestResponse response = readInterestUseCase.findAll(RegistrantId.of(authInfo.getMemberId()));
        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId, @RequestBody UpdateInterestRequest request) {
        updateInterestUseCase.update(authInfo.getMemberId(), InterestId.of(interestId), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId) {
        deleteInterestUseCase.delete(authInfo.getMemberId(), InterestId.of(interestId));
        return ResponseEntity.noContent().build();
    }
}
