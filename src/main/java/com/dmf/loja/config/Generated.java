package com.dmf.loja.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD,ElementType.CONSTRUCTOR})
public @interface Generated {
	
	public static final String IDE = "Gerado pela IDE";

	String value();
}
