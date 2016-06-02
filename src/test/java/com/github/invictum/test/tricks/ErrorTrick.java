package com.github.invictum.test.tricks;

import com.github.invictum.tricks.core.AbstractTrick;

public class ErrorTrick extends AbstractTrick {
    public ErrorTrick() throws ReflectiveOperationException {
        throw new ReflectiveOperationException();
    }
}
