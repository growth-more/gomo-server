package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.application.DeleteInterestUseCase;
import com.gomo.app.interest.application.ReadInterestUseCase;
import com.gomo.app.interest.application.UpdateInterestUseCase;
import com.gomo.app.interest.domain.model.InterestId;
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
    public ResponseEntity<CreateInterestResponse> create(@RequestBody CreateInterestRequest request) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadInterestResponse> find(@PathVariable("id") InterestId interestId) {
        return null;
    }

    @GetMapping
    public ResponseEntity<ListInterestResponse> list() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") InterestId interestId, @RequestBody UpdateInterestRequest request) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") InterestId interestId) {
        return null;
    }
}
