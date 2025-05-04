package com.gomo.app.auth.domain.service;

import java.util.Random;

import com.gomo.app.common.DomainService;

@DomainService
public class AuthCodeGenerator {

	public String generate() {
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}
}
