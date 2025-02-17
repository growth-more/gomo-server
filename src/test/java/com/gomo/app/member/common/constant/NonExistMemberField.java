package com.gomo.app.member.common.constant;

/**
 * 회원 관련 기능에 필요한 필드 값을 상수로 관리한다.
 * 테스트 사용자와 다른 값을 가지기 때문에 중복확인, 생성, 수정 등의 테스트에서 사용할 수 있다.
 */

public interface NonExistMemberField {
	String EMAIL = "NonExistEmail@naver.com";
	String PASSWORD = "NonExistPassword12!";
	String HANDLE = "@NonExistHandle";
	String NAME = "NonExistName";
	String MOTTO = "NonExist Motto";
	String LOGIN_PROVIDER = "EMAIL";
}
