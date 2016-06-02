package com.github.invictum.test.tricks;

import com.github.invictum.tricks.core.TrickFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TrickFactory.class)
public class TrickFactoryTest {

    @Test
    public void getTrickTest() throws Exception {
        FakeTrick trickMock = Mockito.mock(FakeTrick.class);
        PowerMockito.whenNew(FakeTrick.class).withAnyArguments().thenReturn(trickMock);
        TrickFactory.getTrick(FakeTrick.class, null);
        PowerMockito.verifyNew(FakeTrick.class, Mockito.times(1));
    }

    @Test(expected = IllegalStateException.class)
    public void getTrickErrorTest() throws Exception {
        TrickFactory.getTrick(ErrorTrick.class, null);
    }
}
