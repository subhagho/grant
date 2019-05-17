package com.codekutter.grant.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception type for escalating validation error(s).
 */
@Getter
@Setter
public class ValidationExceptions extends Exception {
    private static final String __MESG__ = "Validation Error detected.";

    /**
     * List of validation errors.
     */
    private List<ValidationException> errors;

    /**
     * Default empty constructor.
     */
    public ValidationExceptions() {
        super(__MESG__);
    }

    /**
     * Constructor with list of validation errors.
     *
     * @param errors - Validation Errors.
     */
    public ValidationExceptions(List<ValidationException> errors) {
        super(__MESG__);
        this.errors = errors;
    }

    /**
     * Add a validation error.
     *
     * @param error - Validation Error
     */
    public void add(@Nonnull ValidationException error) {
        Preconditions.checkArgument(error != null);
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    /**
     * Add the list of Validation errors.
     *
     * @param errs - Validation Errors.
     */
    public void addAll(@Nonnull List<ValidationException> errs) {
        Preconditions.checkArgument(errs != null);
        Preconditions.checkArgument(!errs.isEmpty());
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.addAll(errs);
    }
}
