package com.gomo.app.member.unit.domain;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.MemberName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[Domain unit]: 이름 생성 및 수정 테스트")
public class MemberNameTest {
    private static final String NAME="test";
    private static final String BLANK="  ";
    private static final String TOO_SHORT_NAME = "a";
    private static final String TOO_LONG_NAME = Stream.generate(() -> "a").limit(21).collect(Collectors.joining());
    private static final String FORBIDDEN_NAME = "test@";

    @DisplayName("이름을 생성한다.")
    @Test
    void create_member_name() {
        MemberName name = MemberName.of(NAME);
        assertThat(name.toString()).isEqualTo(NAME);
    }

    @DisplayName("null을 입력하면 이름을 생성할 수 없다.")
    @Test
    void create_member_name_with_null() {
        assertThatThrownBy(() -> MemberName.of(null))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be blank");
    }

    @DisplayName("공백만 입력하면 이름을 생성할 수 없다.")
    @Test
    void create_member_name_with_blank(){
        assertThatThrownBy(() -> MemberName.of(BLANK))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be blank");
    }

    @DisplayName("최소 길이보다 짧은 이름은 생성할 수 없다.")
    @Test
    void create_member_name_with_too_short_name(){
        assertThatThrownBy(() -> MemberName.of(TOO_SHORT_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be less than 2 characters");
    }

    @DisplayName("최대 길이보다 긴 이름은 생성할 수 없다.")
    @Test
    void create_member_name_with_too_long_name(){
        assertThatThrownBy(() -> MemberName.of(TOO_LONG_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be more than 20 characters");
    }

    @DisplayName("규칙을 위반한 이름은 사용할 수 없다.")
    @Test
    void create_member_name_with_forbidden_name(){
        assertThatThrownBy(() -> MemberName.of(FORBIDDEN_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must contain only letters and numbers");
    }

    @DisplayName("이름을 수정한다.")
    @Test
    void update_member_name(){
        MemberName name = MemberName.of(NAME);
        MemberName updated_name = name.update("updated");

        assertThat(updated_name.toString()).isEqualTo("updated");
    }

    @DisplayName("수정하려는 이름이 null이면 안된다.")
    @Test
    void update_memeber_name_with_null(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(null))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be blank");
    }

    @DisplayName("수정하는 이름이 공백이면 안된다.")
    @Test
    void update_member_name_with_blank(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(BLANK))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be blank");
    }

    @DisplayName("수정하는 이름이 최소길이보다 짧으면 안된다.")
    @Test
    void update_member_name_with_too_short_name(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(TOO_SHORT_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be less than 2 characters");
    }

    @DisplayName("수정하는 이름이 최대길이보다 길면 안된다.")
    @Test
    void update_member_name_with_too_long_name(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(TOO_LONG_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be more than 20 characters");
    }

    @DisplayName("수정하는 이름이 규칙을 위반하면 안된다.")
    @Test
    void update_member_name_with_forbidden_name(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(FORBIDDEN_NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must contain only letters and numbers");
    }

    @DisplayName("수정하는 이름이 원본과 같으면 안된다.")
    @Test
    void update_member_name_with_same_name(){
        MemberName name = MemberName.of(NAME);
        assertThatThrownBy(() -> name.update(NAME))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("name must not be same with origin name");
    }
}
