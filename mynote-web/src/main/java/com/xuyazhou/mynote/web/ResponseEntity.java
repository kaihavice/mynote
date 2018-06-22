package com.xuyazhou.mynote.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xuyazhou.mynote.Utils.ExceptionUtil;
import com.xuyazhou.mynote.Utils.ResultEnum;
import com.xuyazhou.mynote.Utils.ResultEnumImp;
import com.xuyazhou.mynote.exception.BaseException;
import com.xuyazhou.mynote.exception.DefineException;
import com.xuyazhou.mynote.exception.ParameterException;
import com.xuyazhou.mynote.exception.StateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonSerialize(include = Inclusion.NON_NULL)
public class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 6210446170434591429L;

    private static Logger logger = LoggerFactory.getLogger(ResponseEntity.class);

    public static ResponseEntity getEntityError(Throwable ex, Locale local, MessageSource messageSource) {
        if (ex instanceof BaseException) {
            BaseException e = (BaseException) ex;
            return new ResponseEntity(false, e.getErrorCode(), e.getCause() != null ? e.getCause().getMessage() :
                    "", null);
        } else if (ex instanceof ParameterException) {
            ParameterException pe = (ParameterException) ex;
            return new ResponseEntity(false, pe.getErrorCode(), pe.getMessage(), null);
        } else if (ex instanceof StateException) {
            StateException m_StateException = (StateException) ex;
            return new ResponseEntity(false, "StateError", ExceptionUtil.getRootCause(ex).getMessage(), null);
        } else if (ex instanceof DefineException) {
            DefineException de = (DefineException) ex;
            return new ResponseEntity(false, de.getErrorCode(), de.getMessage(), null);
        } else {
            Exception e = (Exception) ex;
            if (e.getCause().getMessage().length() < 15) {
                return new ResponseEntity(false, e.getCause().getMessage(), e.getCause().getMessage(), null);
            } else {
                return new ResponseEntity(false, BaseException.SYSTEM_ERROR,
                        "服务器内部错误", null);
            }
        }
    }


    /**
     * 将异常堆栈转换为字符串
     *
     * @param aThrowable 异常
     * @return String
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * 接口调用成功，返回
     *
     * @param data
     */

    private ResponseEntity(ResultEnum result, Object data) {
        this.success = result.getSuccess();
        this.errCode = result.getErrCode();
        this.message = result.getMessage();
        this.data = data;
        logger.info(toString());
    }


    /**
     * 创建实例
     *
     * @param data
     * @return
     */
    public static ResponseEntity getEntity(Object data) {
        return new ResponseEntity(data);
    }

    public static ResponseEntity getEntity(ResultEnum result, Object data) {
        return new ResponseEntity(result, data);
    }

    public static ResponseEntity getEntityError(String msg, String errorCode) {
        return new ResponseEntity(false, errorCode, msg, null);
    }


    public ResponseEntity(Boolean success, String errCode, String message, Object data) {
        this.success = success;
        this.errCode = errCode;
        this.message = message;
        this.data = data;
        logger.info(toString());
    }

    /**
     * 返回成功
     *
     * @param data
     */
    @SuppressWarnings("unchecked")
    private ResponseEntity(Object data) {
        int size = 1;
        ResultEnum result = null;
        if (data == null) {
            result = ResultEnumImp.OK;
        } else {
            if (data.getClass().equals(ResultEnumImp.class)) {
                result = (ResultEnum) data;
            } else {
                result = ResultEnumImp.OK;
                if (data instanceof List) {
                    size = ((List<Object>) data).size();
                }
            }
        }
        this.success = result.getSuccess();
        this.errCode = result.getErrCode();
        this.message = result.getMessage();
        this.data = data;
        logger.info(toString() + ",size:" + size);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setStatus(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    private Boolean success;
    private String errCode;
    private String message;
    private Object data;

    public String toString() {
        String resData;
        if (data != null) {
            String dataStr = data.toString();
            resData = dataStr.length() > 1000 ? dataStr.substring(0, 200) + "..." : dataStr;
        } else {
            resData = "";
        }
        return "{success:" + success + ",errCode:" + errCode + ",message:" + message + ",data:" + resData
                + "}";
    }
}
