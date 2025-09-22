package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.MemberErrorCode;

public class MemberUnsupportedLoginMethodException extends ApplicationException {

  public MemberUnsupportedLoginMethodException(MemberErrorCode errorCode) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
  }

  public MemberUnsupportedLoginMethodException(MemberErrorCode errorCode, Throwable cause) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
  }
}
