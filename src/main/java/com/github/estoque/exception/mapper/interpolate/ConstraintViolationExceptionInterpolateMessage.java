package com.github.estoque.exception.mapper.interpolate;

import jakarta.validation.ConstraintViolationException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstraintViolationExceptionInterpolateMessage {
    private static final Pattern VIOLATION_PATTERN = Pattern.compile("ConstraintViolationImpl\\{interpolatedMessage='([^']*)'");

    public static Map<String, String> mapErrorMessage(ConstraintViolationException exception) {
        String detailedMessage = exception.getMessage();

        Matcher matcher = VIOLATION_PATTERN.matcher(detailedMessage);
        if (matcher.find()) {
            String errorMessage = matcher.group(1);
            return Map.of("message", errorMessage);
        }
        return null;
    }
}