package com.zzw.common.core.rest.baseVO;

import com.zzw.common.core.constant.CommonConstants;
import com.zzw.common.core.constant.eunms.ResultEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 响应信息主体
 *
 * @param <T>
 * @author liuyf
 */
@Builder
@ToString
@Accessors(chain = true)
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private int code = CommonConstants.SUCCESS_CODE;

	@Getter
	@Setter
	private String msg = "success";

	@Getter
	@Setter
	private boolean success = true;

	@Getter
	@Setter
	private T data;

	public R() {
		super();
	}

	public R(T data) {
		super();
		this.data = data;
	}

	public R(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public R(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = CommonConstants.FAIL_CODE;
	}

	public R(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public R(int code, String msg,T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public R(int code, String msg,boolean success,T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.success = success;
	}

	public R(ResultEnum resultEnum){
		super();
		this.msg = resultEnum.getMessage();
		this.code = resultEnum.getCode();
	}
	public static <T> R<T> fail() {
		return new R<T>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG,false,null);
	}

	public static <T> R<T> fail(String message) {
		return new R<T>(CommonConstants.FAIL_CODE, message,false,null);
	}

	public static <T> R<T> fail(int code, String message) {
		return new R<T>(code, message,false, null);
	}

	public static <T> R<T> fail(int code, String message, T result) {
		return new R<T>( code, message,false, result);
	}

	public static <T> R<T> ok() {
		return new R<T>( CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG, null);
	}

	public static <T> R<T> ok(T result) {
		return new R<T>( CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG, result);
	}
	public static <T> R<T> ok(int code, String message) {
		return new R<T>(code,message, null);
	}

	public static <T> R<T> ok(int code, String message, T result) {
		return new R<T>( code, message, result);
	}


}
