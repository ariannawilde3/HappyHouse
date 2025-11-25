package junit;

import com.happyhouse.util.AnonymousUsernameGenerator;
import org.junit.Test;


import static org.junit.Assert.*;

public class AnonymousUsernameTest {
    @Test
    public void testGenerateMultiple_LoopNotExecuted() {
        // Loop body not executed at all (count = 0)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(0);

        assertEquals(0, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedOnce() {
        // Loop body executed exactly once (count = 1)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(1);

        assertEquals(1, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedTwice() {
        // Loop body executed exactly twice (count = 2)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(2);

        assertEquals(2, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedTypicalTimes() {
        // Loop body executed typical number of times (count = 10)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(10);

        assertEquals(10, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedNMinusOneTimes() {
        // Loop body executed n-1 times (assuming n = 1000 as reasonable upper bound)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(999);

        assertEquals(999, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedNTimes() {
        // Loop body executed exactly n times (n = 1000)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(1000);

        assertEquals(1000, usernames.length);
    }

    @Test
    public void testGenerateMultiple_LoopExecutedNPlusOneTimes() {
        // Loop body executed n+1 times (n+1 = 1001)
        String[] usernames = AnonymousUsernameGenerator.generateMultiple(1001);

        assertEquals(1001, usernames.length);
    }
}
