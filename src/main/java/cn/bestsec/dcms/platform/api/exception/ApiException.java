package cn.bestsec.dcms.platform.api.exception;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;
    private CommonResponseFieldsSupport response;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode, CommonResponseFieldsSupport response) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.response = response;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public CommonResponseFieldsSupport getResponse() {
        return response;
    }
}
