package org.penny_craal.mairion.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.penny_craal.mairion.model.TimeRange;

public class TimeRangeStartsBeforeEndingValidator
		implements ConstraintValidator<TimeRangeStartsBeforeEnding, TimeRange> {
	@Override
	public void initialize(TimeRangeStartsBeforeEnding timeRangeStartsBeforeEnding) {}

	@Override
	public boolean isValid(TimeRange tr,
				ConstraintValidatorContext constraintValidatorContext) {
		if (tr == null || tr.getZonedStartPoint() == null || tr.getZonedEndPoint() == null) {
			return true;
		}
		return tr.getZonedStartPoint().isBefore(tr.getZonedEndPoint());
	}
}
