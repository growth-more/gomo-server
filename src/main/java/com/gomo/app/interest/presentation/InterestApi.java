package com.gomo.app.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateInterestResponse> create(@Auth AuthInfo authInfo, @ModelAttribute CreateInterestRequest request) {
        CreateInterestResponse response = createInterestUseCase.create(authInfo.getMemberId(), request);
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
        updateInterestUseCase.update(authInfo.getMemberId(), interestId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId) {
        deleteInterestUseCase.delete(authInfo.getMemberId(), interestId);
        return ResponseEntity.noContent().build();
    }
}
