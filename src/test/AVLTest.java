package test;
import org.junit.Test;
import static org.junit.Assert.*;
import avl.AVL;

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
        assertTrue(tree.isBalance());
        assertTrue(tree.contains(8));
        assertEquals(7, tree.size());
        tree.add(15);
        assertEquals(8, tree.size());
        assertTrue(tree.isInvariant());

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
        assertTrue(tree.isBalance());

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


}