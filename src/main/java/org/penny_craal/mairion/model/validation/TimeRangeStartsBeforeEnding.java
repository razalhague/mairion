package org.penny_craal.mairion.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeStartsBeforeEndingValidator.class)
public @interface TimeRangeStartsBeforeEnding {
	String message() default "{timeRange.endNotAfterBeginning}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
