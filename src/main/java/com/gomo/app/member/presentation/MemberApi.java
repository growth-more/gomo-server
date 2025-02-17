package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.CreateMemberUseCase;
import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.member.presentation.response.ReadMemberResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class MemberApi {

    private final CreateMemberUseCase createMemberUseCase;
    private final ReadMemberUseCase readMemberUseCase;
    private final UpdateMemberUseCase updateMemberUseCase;
    private final DeleteMemberUseCase deleteMemberUseCase;

    @PostMapping
    public ResponseEntity<CreateMemberResponse> create(@RequestBody CreateMemberRequest request) {
        CreateMemberResponse response = createMemberUseCase.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ReadMemberResponse> read(@Auth MemberId memberId) {
        ReadMemberResponse response = readMemberUseCase.find(memberId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Auth MemberId memberId, @RequestBody UpdateMemberRequest request) {
        updateMemberUseCase.update(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Auth MemberId memberId) {
        deleteMemberUseCase.delete(memberId);
        return ResponseEntity.noContent().build();
    }
}
