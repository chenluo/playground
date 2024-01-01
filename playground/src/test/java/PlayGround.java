import org.junit.jupiter.api.Test;

public class PlayGround {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Test
    public void test() {
        System.out.println(10 >> 1);
        System.out.println(10 >>> 1);
        System.out.println(-10 >> 1);
        System.out.println(-10 >>> 1);
    }

    @Test
    public void testTableSizeFor() {
        System.out.println(tableSizeFor(10));
    }
}
