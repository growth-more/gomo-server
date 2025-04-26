package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
import com.gomo.app.member.application.CreateMemberUseCase;
import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ReadMemberResponse> read(@Auth AuthInfo authInfo) {
        ReadMemberResponse response = readMemberUseCase.find(MemberId.of(authInfo.getMemberId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateMemberRequest request) {
        updateMemberUseCase.update(MemberId.of(authInfo.getMemberId()), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
        deleteMemberUseCase.delete(MemberId.of(authInfo.getMemberId()));
        return ResponseEntity.noContent().build();
    }
}
