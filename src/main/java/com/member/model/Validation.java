package com.member.model;


//此為拿來定義@Validated分組驗證的介面
public class Validation {
	public interface ValidationLogin {}
	public interface ValidationRegister extends ValidationLogin {}
	
}
