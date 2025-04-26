package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.MemberErrorCode;

public class MemberDuplicatedException extends ApplicationException {

  public MemberDuplicatedException(MemberErrorCode errorCode) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
  }

  public MemberDuplicatedException(MemberErrorCode errorCode, Throwable cause) {
    super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
  }
}
