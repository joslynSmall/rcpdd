package cc.mrbird.febs.common.exception;


import lombok.Getter;
import lombok.Setter;

import javax.validation.ValidationException;


public class DefinedMessageException extends ValidationException {

    @Setter
    @Getter
    private CodeMsg codeMsg;


    private static final long serialVersionUID = 1L;

    public DefinedMessageException() {
        super();
    }

    public DefinedMessageException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public DefinedMessageException(CodeMsg codeMsg, Throwable cause) {
        super(codeMsg.getMsg(), cause);
        this.codeMsg = codeMsg;
    }

}
