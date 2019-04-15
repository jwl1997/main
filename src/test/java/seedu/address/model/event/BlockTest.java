package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

public class BlockTest {

    @Test
    public void keywordsTest() {
        assertEquals(Block.morning(false), new Block(LocalTime.parse("06:00"), LocalTime.parse("12:00"), false));
        assertEquals(Block.afternoon(false), new Block(LocalTime.parse("12:00"), LocalTime.parse("18:00"), false));
        assertEquals(Block.evening(false), new Block(LocalTime.parse("18:00"), LocalTime.parse("20:00"), false));
        assertEquals(Block.night(false), new Block(LocalTime.parse("20:00"), LocalTime.parse("00:00"), false));
        assertEquals(Block.midnight(false), new Block(LocalTime.parse("00:00"), LocalTime.parse("06:00"), false));
        assertEquals(Block.school(false), new Block(LocalTime.parse("08:00"), LocalTime.parse("18:00"), false));
        assertEquals(Block.breakfast(false), new Block(LocalTime.parse("07:00"), LocalTime.parse("10:00"), false));
        assertEquals(Block.lunch(false), new Block(LocalTime.parse("12:00"), LocalTime.parse("14:00"), false));
        assertEquals(Block.dinner(false), new Block(LocalTime.parse("17:00"), LocalTime.parse("20:00"), false));
        assertEquals(Block.supper(false), new Block(LocalTime.parse("21:00"), LocalTime.parse("01:00"), false));
        assertEquals(Block.brunch(false), new Block(LocalTime.parse("10:00"), LocalTime.parse("13:00"), false));
    }

    @Test
    public void negationTest() {
        assertEquals(Block.morning(true), new Block(LocalTime.parse("12:00"), LocalTime.parse("06:00"), false));
        assertEquals(Block.morning(true), new Block(LocalTime.parse("06:00"), LocalTime.parse("12:00"), true));
        assertNotEquals(Block.morning(true), Block.morning(false));
    }

    @Test
    public void nonBlockTest() {
        assertEquals(new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), false),
                new Block(LocalTime.of(1, 1), LocalTime.of(1, 1), false));
        assertEquals(new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), true),
                new Block(LocalTime.of(1, 1), LocalTime.of(1, 1), false));
        assertEquals(new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), true),
                new Block(LocalTime.of(1, 1), LocalTime.of(1, 1), true));
    }

    @Test
    public void testWithin() {
        Block nonBlock = new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), false);
        assertTrue(nonBlock.isWithinBlock(LocalDateTime.MIN, LocalDateTime.MAX));
        Block anotherBlock = new Block(LocalTime.of(9, 0), LocalTime.of(19, 0), false);
        assertTrue(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 1, 9, 0),
                LocalDateTime.of(1, 1, 1, 19, 0)));
        assertFalse(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 1, 8, 59),
                LocalDateTime.of(1, 1, 1, 19, 0)));
        assertFalse(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 1, 9, 0),
                LocalDateTime.of(1, 1, 1, 19, 1)));
        assertFalse(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 1, 9, 0),
                LocalDateTime.of(1, 1, 2, 19, 0)));
        anotherBlock = new Block(LocalTime.of(22, 0), LocalTime.of(2, 0), false);
        assertTrue(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 1, 22, 0),
                LocalDateTime.of(1, 1, 2, 2, 0)));
        assertFalse(anotherBlock.isWithinBlock(LocalDateTime.of(1, 1, 2, 2, 0),
                LocalDateTime.of(1, 1, 2, 22, 0)));
    }

    @Test
    public void equalityTest() {
        Block nonBlock = new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), false);
        assertTrue(nonBlock.equals(nonBlock));
        assertFalse(nonBlock.equals(new Object()));
        Block anotherBlock = new Block(LocalTime.of(9, 0), LocalTime.of(19, 0), false);
        assertFalse(nonBlock.equals(anotherBlock));
    }

    @Test
    public void toStringTest() {
        Block nonBlock = new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), false);
        assertEquals(nonBlock.toString(), "any time");
        Block anotherBlock = new Block(LocalTime.of(9, 0), LocalTime.of(19, 0), false);
        assertEquals(anotherBlock.toString(), "09:00 to 19:00");
        anotherBlock = new Block(LocalTime.of(2, 0), LocalTime.of(22, 0), true);
        assertEquals(anotherBlock.toString(), "22:00 to 02:00");
    }

    @Test
    public void getFirst() {
        Block block = new Block(LocalTime.of(12, 34), LocalTime.of(23, 45), false);
        assertEquals(block.getFirst(), LocalTime.of(12, 34));
        block = new Block(LocalTime.of(12, 34), LocalTime.of(23, 45), true);
        assertEquals(block.getFirst(), LocalTime.of(23, 45));
    }

}
