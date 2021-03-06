/*
 *  Copyright 2018, Oath Inc.
 *  Licensed under the terms of the Apache License, Version 2.0.
 *  See the LICENSE file associated with the project for terms.
 */
package com.yahoo.bullet.parsing;

import com.google.gson.annotations.Expose;
import com.yahoo.bullet.common.BulletError;
import com.yahoo.bullet.typesystem.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.yahoo.bullet.common.BulletError.makeError;

@Getter @Setter
public class BinaryExpression extends Expression {
    public static final BulletError BINARY_EXPRESSION_REQUIRES_VALID_EXPRESSIONS_ERROR =
            makeError("This Expression needs 2 valid operands", "Please add an expression for 'left' and 'right'.");

    @Expose
    private Expression left;
    @Expose
    private Expression right;
    // If type is not null, cast the result to this type.
    @Expose
    private Type type;

    /**
     * Default Constructor. GSON recommended.
     */
    public BinaryExpression() {
        super();
        left = null;
        right = null;
    }

    @Override
    public Set<String> getRequiredFields() {
        Set<String> result = new HashSet<>(left.getRequiredFields());
        result.addAll(right.getRequiredFields());
        return result;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + ", left: " + left + ", right: " + right + ", type: " + type + "}";
    }

    @Override
    public Optional<List<BulletError>> initialize() {
        if (left == null || right == null) {
            return Optional.of(Collections.singletonList(BINARY_EXPRESSION_REQUIRES_VALID_EXPRESSIONS_ERROR));
        }
        Optional<List<BulletError>> errors = left.initialize();
        if (errors.isPresent()) {
            return errors;
        }
        return right.initialize();
    }
}

