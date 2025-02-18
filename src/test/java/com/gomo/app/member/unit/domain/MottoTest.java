package com.gomo.app.member.unit.domain;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.Motto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[Domain unit]: 모토 생성 및 수정 테스트")
public class MottoTest {

    private static final String MOTTO = "test";
    private static final String TOO_LONG_MOTTO = Stream.generate(() -> "test").limit(200).collect(Collectors.joining());
    private static final String FORBIDDEN_MOTTO = "[<>&';|{}[]()`]--*";

    @DisplayName("모토를 생성한다.")
    @Test
    void create_motto(){
        Motto motto = Motto.of(MOTTO);
        assertThat(motto.toString()).isEqualTo(MOTTO);
    }

    @DisplayName("최대 길이보다 긴 모토는 생성할 수 없다.")
    @Test
    void create_motto_with_long_length(){
        assertThatThrownBy(() -> Motto.of(TOO_LONG_MOTTO))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Motto must not exceed 200 characters");
    }

    @DisplayName("모토 생성 룰을 위반한 모토는 생성할 수 없다.")
    @Test
    void create_motto_with_forbidden_characters(){
        assertThatThrownBy(() -> Motto.of(FORBIDDEN_MOTTO))
            .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Motto must comply with the motto rules");
    }

    @DisplayName("모토를 수정한다.")
    @Test
    void update_motto(){
        Motto motto = Motto.of(MOTTO);
        Motto updatedMotto = motto.update("updated_motto");

        assertThat(updatedMotto.toString()).isEqualTo("updated_motto");
    }

    @DisplayName("최대 길이보다 긴 모토로 수정할 수 없다.")
    @Test
    void update_motto_with_long_length(){
        Motto motto = Motto.of(MOTTO);

        assertThatThrownBy(() -> motto.update(TOO_LONG_MOTTO))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Motto must not exceed 200 characters");
    }

    @DisplayName("금지 문자를 포함한 모토로 수정할 수 없다,")
    @Test
    void update_motto_with_forbidden_characters(){
        Motto motto = Motto.of(MOTTO);

        assertThatThrownBy(() -> motto.update(FORBIDDEN_MOTTO))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Motto must comply with the motto rules");
    }

    @DisplayName("동일한 내용으로 수정할 수 없다.")
    @Test
    void update_motto_with_same_motto(){
        Motto motto = Motto.of(MOTTO);
        assertThatThrownBy(() -> motto.update(MOTTO))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Motto cannot update with same motto");
    }
}
