package com.lsl.demo.tree;

import org.junit.Test;

/**
 * <pre>
 * Created by liusilong on 2017/7/4.
 * 练习构建查找二叉树
 * 参考：http://blog.csdn.net/speedme/article/details/21659899
 * 定义：
 * 维基百科：https://zh.wikipedia.org/wiki/%E4%BA%8C%E5%85%83%E6%90%9C%E5%B0%8B%E6%A8%B9
 * 1.若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 2.若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * 3.任意节点的左、右子树也分别为二叉查找树；
 * 4.没有键值相等的节点。
 * </pre>
 */

public class MyTest1 {
    @Test
    public void client() {
        GenerateSearchBinaryTree gst = new GenerateSearchBinaryTree();
        int[] arr = {45, 23, 43, 98, 17, 48, 16};
        for (int i : arr) {
            gst.insert(i);
        }
        preOrder(gst.getRoot());
        System.out.println();
        //查找元素17
        TestTreeNode node = searchBST(gst.getRoot(), 17);
        System.out.println(node.getValue() + "\t" + node.getLeft().getValue());
        System.out.println();
        //插入元素25
        insertBST(gst.getRoot(), 25);
        preOrder(gst.getRoot());
    }

    /**
     * 使用迭代的方式前序遍历生成的二叉树
     *
     * @param node™
     */
    public void preOrder(TestTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + "\t");
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    /**
     * <pre>
     *  在二叉搜索树b中查找x的过程为
     *      1.若b是空树，则搜索失败，否则：
     *      2.若x等于b的根节点的数据域之值，则查找成功；否则：
     *      3.若x小于b的根节点的数据域之值，则搜索左子树；否则：
     *      4.查找右子树。
     * </pre>
     * 查找某一个节点
     *
     * @param root  根节点
     * @param value 需要查找的节点的value
     * @return
     */
    public TestTreeNode searchBST(TestTreeNode root, int value) {
        if (root == null) {
            return null;
        }
        if (value == root.getValue()) {
            return root;
        } else if (value < root.getValue()) {
            return searchBST(root.getLeft(), value);
        } else if (value > root.getValue()) {
            return searchBST(root.getRight(), value);
        }
        return null;
    }

    /**
     * 插入元素
     * <pre>
     *
     * </pre>
     *
     * @param root  根节点
     * @param value 带插入的元素
     * @return
     */
    public boolean insertBST(TestTreeNode root, int value) {
//       若根节点为空的情况下 直接返回false
        if (root == null) {
            return false;
        }
        if (value == root.getValue()) { //如果插入的节点和当前的节点相等，则返回false
            return false;
        } else if (value < root.getValue()) {
            if (root.getLeft() == null) {
                root.setLeft(value);
                return true;
            } else {
                insertBST(root.getLeft(), value);
            }
        } else if (value > root.getValue()) {
            if (root.getRight() == null) {
                root.setRight(value);
                return true;
            } else {
                insertBST(root.getRight(), value);
            }

        }
        return false;
    }
}

/**
 * <pre>
 * 创建查找二叉树
 * in:{45, 23, 43, 98, 17, 48, 16};
 * out:
 *                  45
 *           23            98
 *      17      43      48
 *  16
 * </pre>
 */
class GenerateSearchBinaryTree {
    TestTreeNode root;

    public void setRoot(int value) {
        this.root = new TestTreeNode(value);
    }

    public TestTreeNode getRoot() {
        return root;
    }

    /**
     * <pre>
     * 添加节点
     * 1.每次从root节点遍历
     *          如value比root节点的value小则让当前节点等于当前节点的左节点
     *          否则当前节点等于当前节点的右节点
     * 2.用变量y来记录每次遍历之后的最后的一个节点
     *          如果y==null说明当前root节点为空，则设置root节点
     *          否则：
     *              如果value<y.value,则让value为y的左节点
     *              如果value>y.value，则让value为y的有节点
     * </pre>
     *
     * @param value
     */
    public void insert(int value) {
        TestTreeNode x = root;
        TestTreeNode y = null;
        while (x != null) {
            y = x;
            if (value < x.getValue()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        if (y == null) {
            setRoot(value);
        } else if (value < y.getValue()) {
            y.setLeft(value);
        } else {
            y.setRight(value);
        }
    }
}

/**
 * 节点类
 */
class TestTreeNode {
    private int value;
    private TestTreeNode left;
    private TestTreeNode right;

    public TestTreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TestTreeNode getLeft() {
        return left;
    }

    public void setLeft(int value) {
        this.left = new TestTreeNode(value);
    }

    public TestTreeNode getRight() {
        return right;
    }

    public void setRight(int value) {
        this.right = new TestTreeNode(value);
    }
}
