package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.MemberErrorCode;

public class MemberAuthenticationFailedException extends ApplicationException {

  public MemberAuthenticationFailedException(MemberErrorCode errorCode) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
  }

  public MemberAuthenticationFailedException(MemberErrorCode errorCode, Throwable cause) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
  }
}
