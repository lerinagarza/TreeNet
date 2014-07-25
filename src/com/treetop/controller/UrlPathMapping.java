package com.treetop.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds URL Path Mapping to ViewBeans
 * @author jhagle
 * 
 * @Documented
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UrlPathMapping {

	String[] value();

}
