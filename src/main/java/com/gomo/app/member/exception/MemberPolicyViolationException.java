package com.gomo.app.member.exception;

import com.gomo.app.common.exception.DomainException;

public class MemberPolicyViolationException extends DomainException {

  public MemberPolicyViolationException(MemberErrorCode errorCode, String message) {
    super(errorCode.getHttpStatus(), errorCode.name(), message);
  }

  public MemberPolicyViolationException(MemberErrorCode errorCode, String message, Throwable cause) {
    super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
  }
}
