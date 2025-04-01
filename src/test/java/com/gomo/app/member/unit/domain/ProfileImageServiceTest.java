package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.ProfileImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.common.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.MajorInterestDuplicatedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 프로필 이미지 URL 조회 테스트")
public class ProfileImageServiceTest {

    @InjectMocks
    ProfileImageService sut;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("프로필 이미지 URL을 조회한다.")
    @Test
    void find_all_profile_image_url(){
        List<String> url_list = new ArrayList<>();
        url_list.add("https://image.nurdykim.me/gomo/default-profile-image.jpg");
        doReturn(url_list).when(memberRepository).findAllByProfileImageUrl();

        Set<String> actual = sut.getAllProfileImageUrl();

        assertThat(actual).isNotNull();
    }
}
