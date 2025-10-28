package com.gomo.app.core.interest.adapter.out.client;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.image.application.port.DeleteImagePortIn;

@DisplayName("[Adapter unit]: 이미지 삭제 테스트")
@ExtendWith(MockitoExtension.class)
class DeleteImageClientTest {

	@InjectMocks
	DeleteImageClient sut;

	@Mock
	DeleteImagePortIn deleteImagePortIn;

	@DisplayName("이미지를 업로드한다.")
	@Test
	void upload_image() {
		sut.delete("imageUrl");

		verify(deleteImagePortIn, times(1)).delete(any());
	}
}
