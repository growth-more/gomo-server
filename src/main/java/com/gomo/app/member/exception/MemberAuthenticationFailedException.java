package com.gomo.app.member.exception;

import com.gomo.app.common.exception.DomainException;

public class MemberAuthenticationFailedException extends DomainException {

  public MemberAuthenticationFailedException(MemberErrorCode errorCode, String message) {
    super(errorCode.getHttpStatus(), errorCode.name(), message);
  }

  public MemberAuthenticationFailedException(MemberErrorCode errorCode, String message, Throwable cause) {
    super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
  }
}
