package com.github.invictum.test.enhanced.table;

import com.github.invictum.EnhancedTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class EnhancedTableTest {

    @Test
    public void getFirstRowAsTest() {
        EnhancedTable sud = new EnhancedTable("|key    |value      |\n|keyData|valueData  |");
        TestTableDto actual = sud.getFirstRowsAs(TestTableDto.class);
        TestTableDto expected = new TestTableDto("keyData", "valueData");
        assertThat("Returned object is wrong.", actual, equalTo(expected));
    }

    @Test
    public void getRowAsTest() {
        EnhancedTable sud = new EnhancedTable("|key    |value      |\n|keyData|valueData  |\n|key|value |");
        List<TestTableDto> actual = sud.getRowsAs(TestTableDto.class, false);
        List<TestTableDto> expected = new ArrayList<>();
        expected.add(new TestTableDto("keyData", "valueData"));
        expected.add(new TestTableDto("key", "value"));
        assertThat("Returned objects is wrong.", actual, equalTo(expected));
    }
}
