package com.gomo.app.support.image.application.usecase;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.image.application.port.ManageImagePortOut;

@DisplayName("[Application Unit]: 이미지 파일 삭제 테스트")
@ExtendWith(MockitoExtension.class)
class DeleteImageUseCaseTest {

	@InjectMocks
	private DeleteImageUseCase sut;

	@Mock
	private ManageImagePortOut manageImagePortOut;

	@DisplayName("이미지 파일을 삭제한다.")
	@Test
	void delete_image() {
		sut.delete("imageUrl");

		verify(manageImagePortOut, times(1)).delete(anyString());
	}
}
