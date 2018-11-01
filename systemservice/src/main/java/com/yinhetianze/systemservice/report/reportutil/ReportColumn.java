package com.yinhetianze.systemservice.report.reportutil;

import java.lang.annotation.*;

/**
 * @description report data flag(report parameter)
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ReportColumn {
	// default value
	String value() default "";
	
	/**
	 * @description
	 * cell style
	 */
	String styleKey() default "defaultStyle";
	
	/**
	 * @description
	 * sheet locked(not editable attribute, need sheet set password)
	 */
	boolean locked() default true;
	
	/**
	 * @description
	 * date type format
	 */
	String dateFormat() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	/**
	 * @description
	 * number type format
	 */
	String numberFormat() default "";

	/**
	 * @description
	 * set the decimal precision, be used for double type or float type or long type
	 */
	int precision() default 0;

}
