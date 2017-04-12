package com.github.invictum;

import com.github.invictum.fixtures.FixtureProcessor;
import org.junit.runners.model.Statement;

public class InvictumStatement extends Statement {

    private Statement statement;

    public InvictumStatement(final Statement statement) {
        this.statement = statement;
    }

    @Override
    public void evaluate() throws Throwable {
        try {
            statement.evaluate();
        } finally {
            FixtureProcessor.rollback();
        }
    }
}
