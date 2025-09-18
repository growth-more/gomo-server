package com.gomo.app.diagnositc;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/simulators/logs")
@Slf4j
@Tag(name = "Diagnostic", description = "Log simulation APIs (non-production only)")
@Profile({"local", "dev"})
public class LogSimulator {

	@PostMapping("/create-level-count")
	@Operation(summary = "Generate logs", description = "Generate logs of given level and count immediately")
	public String simulatorLogLevelAndCount(@RequestParam String level, @RequestParam int count) {
		return generateLogs(level, count, 0);
	}

	@PostMapping("/create-level-count-interval")
	@Operation(summary = "Generate logs with interval", description = "Generate logs of given level and count with specified interval (ms)")
	public String simulatorLogLevelCountInterval(@RequestParam String level, @RequestParam int count, @RequestParam long interval) {
		return generateLogs(level, count, interval);
	}

	private String generateLogs(String level, int count, long interval) {
		level = level.toUpperCase();
		for (int i = 0; i < count; i++) {
			switch (level) {
				case "TRACE" -> log.trace("Simulated TRACE log #{}", i + 1);
				case "DEBUG" -> log.debug("Simulated DEBUG log #{}", i + 1);
				case "INFO" -> log.info("Simulated INFO log #{}", i + 1);
				case "WARN" -> log.warn("Simulated WARN log #{}", i + 1);
				case "ERROR" -> log.error("Simulated ERROR log #{}", i + 1);
				case "FATAL" -> log.error("Simulated FATAL log #{}", i + 1);
				default -> throw new RuntimeException("Unsupported log level: " + level);
			}
			if (interval > 0 && i < count - 1) {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					throw new RuntimeException("Failed to generate logs during interval processing", e);
				}
			}
		}
		return String.format("Generated %d %s logs with %d ms interval", count, level, interval);
	}
}
