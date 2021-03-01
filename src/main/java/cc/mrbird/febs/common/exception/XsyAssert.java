package cc.mrbird.febs.common.exception;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class XsyAssert {

    /**
     * 抛出错误信息
     */
    public static void throwErrorMessage(Exception e) {
        if (e.getCause() == null) {
            if (e instanceof DefinedMessageException) {
                isTrue(false, ((DefinedMessageException) e).getCodeMsg());
            } else {
                isTrue(false, new CodeMsg(-1, e.getMessage()));
            }
        } else if (e.getCause().getCause() == null) {
            if (e instanceof DefinedMessageException) {
                isTrue(false, ((DefinedMessageException) e).getCodeMsg());
            } else {
                isTrue(false, new CodeMsg(-1, e.getCause().getMessage()));
            }
        } else {
            if (e instanceof DefinedMessageException) {
                isTrue(false, ((DefinedMessageException) e).getCodeMsg());
            } else {
                isTrue(false, new CodeMsg(-1, e.getCause().getCause().getMessage()));
            }
        }
    }

    /**
     * 异常状态提示
     *
     * @param msg
     */
    public static void throwErrorMessage(String msg) {
        isTrue(false, new CodeMsg(-5, msg));
    }

    /**
     * 异常状态提示
     *
     * @param msg
     */
    public static void throwErrorMessage(int code, String msg) {
        isTrue(false, new CodeMsg(code, msg));
    }

    /**
     * 异常状态提示
     *
     * @param message
     */
    public static void throwErrorMessage(CodeMsg message) {
        isTrue(false, message);
    }

    /**
     * 当表达式为false时，进行提醒
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, CodeMsg message) {
        if (!expression) {
            throw new DefinedMessageException(message);
        }
    }

    public static void notNull(Object object, CodeMsg message) {
        if (object == null) {
            throw new DefinedMessageException(message);
        }
    }

    public static void isNull(Object object, CodeMsg message) {
        if (object != null) {
            throw new DefinedMessageException(message);
        }
    }

    public static void hasLength(String text, CodeMsg message) {
        if (!StringUtils.hasLength(text)) {
            throw new DefinedMessageException(message);
        }
    }

    public static void hasText(String text, CodeMsg message) {
        if (!StringUtils.hasText(text)) {
            throw new DefinedMessageException(message);
        }
    }

    public static void notEmpty(Object[] array, CodeMsg message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new DefinedMessageException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, CodeMsg message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new DefinedMessageException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map, CodeMsg message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new DefinedMessageException(message);
        }
    }
}
