package com.gomo.app.common.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.auth.adapter.in.filter.AuthenticationFilter;

@WebMvcTest(
	controllers = ApplicationExceptionAdvice.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthenticationFilter.class)
)
@DisplayName("[Common integration] 예외 공통 처리 테스트")
@Import({ApplicationExceptionAdviceTest.FakeController.class})
class ApplicationExceptionAdviceTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("ApplicationException 발생 시, 커스텀 응답을 반환한다.")
	@Test
	void application_exception() throws Exception {
		mockMvc.perform(get("/test/app-exception"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.path").value("/test/app-exception"))
			.andExpect(jsonPath("$.httpStatus").value(400))
			.andExpect(jsonPath("$.code").value("TEST-001"))
			.andExpect(jsonPath("$.message").value("This is a test application exception."));
	}

	@DisplayName("RuntimeException 발생 시, 500 응답을 반환한다.")
	@Test
	void runtime_exception() throws Exception {
		mockMvc.perform(get("/test/runtime-exception"))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.path").value("/test/runtime-exception"))
			.andExpect(jsonPath("$.httpStatus").value(500))
			.andExpect(jsonPath("$.code").value("Server internal error"))
			.andExpect(jsonPath("$.message").value("This is a generic runtime exception."));
	}

	@DisplayName("MaxUploadSizeExceededException 발생 시, 413 응답을 반환한다.")
	@Test
	void max_upload_size_exceeded_exception() throws Exception {
		mockMvc.perform(post("/test/upload"))
			.andExpect(status().isPayloadTooLarge())
			.andExpect(jsonPath("$.path").value("/test/upload"))
			.andExpect(jsonPath("$.httpStatus").value(413))
			.andExpect(jsonPath("$.code").value("IMA-ROO-001"));
	}

	@RestController
	static class FakeController {

		@GetMapping("/test/app-exception")
		public void throwApplicationException() {
			throw new ApplicationException(400, "TEST-001", "This is a test application exception.");
		}

		@GetMapping("/test/runtime-exception")
		public void throwRuntimeException() {
			throw new RuntimeException("This is a generic runtime exception.");
		}

		@PostMapping("/test/upload")
		public void throwUploadException() {
			throw new MaxUploadSizeExceededException(1024L);
		}
	}
}
