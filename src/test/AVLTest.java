package test;
import org.junit.Test;
import static org.junit.Assert.*;
import avl.AVL;
import java.util.Iterator;

public class AVLTest {

    @Test
    public void addTest() {
        AVL<Integer> tree = new AVL<>();
        tree.add(14);
        tree.add(10);
        tree.add(6);
        tree.add(2);
        tree.add(12);
        tree.add(4);
        tree.add(8);
        assertTrue(tree.checkBalance());
        assertTrue(tree.contains(8));
        assertEquals(7, tree.size());
        tree.add(15);
        assertEquals(8, tree.size());
        assertTrue(tree.checkInvariant());

    }



    @Test
    public void removeTest() {
        AVL<Integer> tree = new AVL<>();
        tree.add(10);
        tree.add(6);
        tree.add(2);
        tree.add(12);
        tree.add(4);
        tree.add(8);
        tree.remove(10);
        assertFalse(tree.contains(10));
        assertEquals(5, tree.size());
        assertTrue(tree.checkBalance());

    }

    @Test
    public void containsTest() {
        AVL<Integer> tree = new AVL<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(12));
        assertFalse(tree.contains(15));
        tree.remove(4);
        assertFalse(tree.contains(4));
    }
    @Test
    public void iteratorTest() {
        AVL<Integer> tree = new AVL<>();
        tree.add(10);
        tree.add(3);
        tree.add(15);
        tree.add(5);
        tree.add(7);
        tree.add(2);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(15, iterator.next().intValue());
        assertFalse(iterator.hasNext());
    }

    @Test
 public void testForEach(){
        AVL<Integer> tree = new AVL<Integer>();
        assertTrue(tree.isEmpty());
        tree.add(5);
        tree.add(9);
        tree.add(3);
        tree.add(4);
        int[] array = new int[tree.size()];
        int[ ] array2 = {3,4, 5, 9};
        int[ ] array3 = {3, 4, 5};
        int j = 0;
        for(int i: tree) {
            array[j] = i;
            j++;

            if (j>=tree.size()) break;
        }
        assertFalse(tree.isEmpty());
        assertArrayEquals(array, array2);
        tree.remove(9);
        assertFalse(tree.contains(9));
        j = 0;
        array = new int[tree.size()];
        for(int i: tree) {
            array[j] = i;
            j++;
            if (j>=(tree.size())) break;
        }
        assertArrayEquals(array, array3);
    }



}