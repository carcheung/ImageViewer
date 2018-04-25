package com.example.carolyncheung.hisimageviewer.utils;

import android.util.Log;

import java.lang.reflect.Constructor;

/**
 * Created by Carolyn Cheung on 4/16/2018.
 */

class BSTNode {
    BSTNode left, right;
    int PIXEL_VALUE;
    int GREY_VALUE;

    // Constructor
    public BSTNode() {
        left = null;
        right = null;
        PIXEL_VALUE = 0;
        GREY_VALUE = 0;
    }

    // Constructor
    public BSTNode(int n, int v) {
        left = null;
        right = null;
        PIXEL_VALUE = n;
        GREY_VALUE = v;
    }

    // set left node
    public void setLeft(BSTNode n) {
        left = n;
    }

    // set right node
    public void setRight(BSTNode n) {
        right = n;
    }

    public BSTNode getLeft() {
        return left;
    }

    public BSTNode getRight() {
        return right;
    }

    // set data
    public void setData(int d) {
        PIXEL_VALUE = d;
    }

    public int getData() {
        return PIXEL_VALUE;
    }

    public int getGREY_VALUE() {
        return GREY_VALUE;
    }
}

class BST {
    private BSTNode root;

    // Constructor
    public BST() {
        root = null;
    }

    // check if tree is empty
    public boolean isEmpty() {
        return root == null;
    }

    // function to insert data
    public void insert(int data, int v) {
        root = insert(root, data, v);
    }

    // function to insert data recursively
    private BSTNode insert(BSTNode node, int data, int v) {
        // if the node is new
        if (node == null) {
            node = new BSTNode(data, v);
        } else {
            // if the data is less than this nodes data
            if (data <= node.getData()) {
                node.left = insert(node.left, data, v);
            } else {
                node.right = insert(node.right, data, v);
            }
        }
        return node;
    }

    // Delete data
    public void delete(int k) {
        if (isEmpty()) {
            Log.d("BST.delete()", "Empty!");
        } else if (search(k) == false) {
            Log.d("BST.delete()", k + " was not found!");
        } else {
            root = delete(root, k);
            Log.d("BST.delete()", k + " deleted from tree");
        }
    }

    private BSTNode delete(BSTNode root, int k) {
        BSTNode p, p2, n;
        if (root.getData() == k) {
            BSTNode lt, rt;
            lt = root.getLeft();
            rt = root.getRight();

            if (lt == null && rt == null) {
                return null;
            } else if (lt == null) {
                p = rt;
                return p;
            } else if (rt == null) {
                p = lt;
                return p;
            } else {
                p2 = rt;
                p = rt;
                while (p.getLeft() != null) {
                    p = p.getLeft();
                }
                p.setLeft(lt);
                return p2;
            }
        }
        if (k < root.getData()) {
            n = delete(root.getLeft(), k);
            root.setLeft(n);
        } else {
            n = delete(root.getRight(), k);
            root.setRight(n);
        }
        return root;
    }

    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(BSTNode r) {
        if (r == null) {
            return 0;
        } else {
            int l = 1;
            l += countNodes(r.getLeft());
            l += countNodes(r.getRight());
            return l;
        }
    }

    public int searchNearest(int p) {
        boolean found = false;
        BSTNode r = root;
        int rval;
        int gval = -1;
        while (r != null) {
            rval = r.getData();
            gval = r.getGREY_VALUE();
            if (p < rval) {
                r = r.getLeft();
            } else if (p > rval) {
                r = r.getRight();
            } else {
                break;
            }
        }
        return gval;
    }

    // search for element
    public boolean search(int val) {
        return search(root, val);
    }

    private boolean search(BSTNode r, int val) {
        boolean found = false;
        while ((r != null) && !found) {
            int rval = r.getData();
            if (val < rval) {
                r = r.getLeft();
            } else if (val > rval) {
                r = r.getRight();
            } else {
                found = true;
                break;
            }
            found = search(r, val);
        }

        return found;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(BSTNode r) {
        if (r != null) {
            inorder(r.getLeft());
            inorder(r.getRight());
        }
    }

    // preorder traversal
    public void preorder() {
        preorder(root);
    }

    private void preorder(BSTNode r) {
        if (r != null) {
            preorder(r.getLeft());
            preorder(r.getRight());
        }
    }

    public void postorder() {
        postorder(root);
    }

    private void postorder(BSTNode r) {
        if (r != null) {
            postorder(r.getLeft());
            postorder(r.getRight());
        }
    }


}
