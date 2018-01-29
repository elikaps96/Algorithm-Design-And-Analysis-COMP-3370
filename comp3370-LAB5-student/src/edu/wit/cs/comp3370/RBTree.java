
package edu.wit.cs.comp3370;

/* Implements methods to use a red-black tree 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 5
 * 
 */

public class RBTree extends LocationHolder {

	/*
	 * sets a disk location's color to red.
	 * 
	 * Use this method on fix-insert instead of directly coloring nodes red to
	 * avoid setting nil as red.
	 */
	private void setRed(DiskLocation z) {
		if (z != nil)
			z.color = RB.RED;
	}

	// Tree max based on passed node.
	private DiskLocation max(DiskLocation d) {
		while (!d.right.equals(nil)) {
			d = d.right;
		}
		return d;
	}

	// Tree min based on passed node.
	private DiskLocation min(DiskLocation d) {
		while (!d.left.equals(nil)) {
			d = d.left;
		}
		return d;
	}

	// Up
	private DiskLocation up(DiskLocation d) {

		DiskLocation y = d.parent;
		if (y.equals(nil) || d.equals(y.left))
			return y;
		return up(y);
	}

	// Find and return passed value in tree.
	@Override
	public DiskLocation find(DiskLocation d) {
		// TODO: implement method
		DiskLocation x = root;
		while (!d.equals(nil) && !d.equals(x)) {
			if (d.isGreaterThan(x))
				x = x.right;
			else
				x = x.left;
		}
		return x;
	}

	// if right child exits return min under right child
	// else return min ancestor to right.
	@Override
	public DiskLocation next(DiskLocation d) {
		// TODO: implement method

		if (!d.right.equals(nil))
			return min(d.right);
		return up(d);

	}

	// if left child exits return min under left child
	// else return min ancestor to left.
	@Override
	public DiskLocation prev(DiskLocation d) {
		// TODO: implement method
		DiskLocation y = nil;
		if (!d.left.equals(nil))
			return max(d.left);

		y = d.parent;

		while (!y.equals(nil) && d.equals(y.left)) {
			d = y;
			y = y.parent;
		}
		return y;
	}

	@Override
	public void insert(DiskLocation d) {
		// TODO Auto-generated method stub
		 d.parent = nil;
		 d.left = nil;
		 d.right = nil;
		 nil.left = nil;
		 nil.right = nil;
		 nil.parent = nil;

		 if (root == null) {
		 root = d;
		 root.color = RB.BLACK;
		 return;
		 }
		
	
		 
		 d.parent = findParent(d, root, nil);
		
		 if (d.parent.equals(nil))
		 root = d;
		 else if (!d.isGreaterThan(d.parent))
		 d.parent.left = d;
		 else
		 d.parent.right = d;

		
		setRed(d);
		fixInsert(d);

	}

	// Find height of tree
	@Override
	public int height() {
		// TODO: implement method
		int h = 0;
		DiskLocation d = min(root);
		while (!d.parent.equals(nil)) {
			h++;
			d = d.parent;
		}
		return h;
	}

	// Find parent
	private DiskLocation findParent(DiskLocation n, DiskLocation curr, DiskLocation parent) {

		if (curr.equals(nil))
			return parent;

		if (!n.isGreaterThan(curr))
			return findParent(n, curr.left, curr);
		else
			return findParent(n, curr.right, curr);

	}

	// When a the properties of a Red Black tree are
	// are violated fixInsert reestablishes the balanced tree.
	private void fixInsert(DiskLocation d) {

		while (d.parent.color == RB.RED) {
			if (d.parent.equals(d.parent.parent.left)) {
				DiskLocation y = d.parent.parent.right;
				if (y.color == RB.RED) {
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				} else if (d.equals(d.parent.right)) {
					d = d.parent;
					rotateLeft(d);
				}
				d.parent.color = RB.BLACK;
				setRed(d.parent.parent);
				rotateRight(d.parent.parent);
			} else {
				DiskLocation y = d.parent.parent.left;
				if (y.color == RB.RED) {
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				} else if (d.equals(d.parent.left)) {
					d = d.parent;
					rotateRight(d);
				}
				d.parent.color = RB.BLACK;
				setRed(d.parent.parent);
				rotateLeft(d.parent.parent);

			}
		}
		root.color = RB.BLACK;

	}

	// Moves node to left side of the tree.
	private void rotateLeft(DiskLocation d) {
		DiskLocation y = d.right;
		d.right = y.left;
		if (!y.left.equals(nil)) {
			y.left.parent = d;

		}
		y.parent = d.parent;
		if (d.parent.equals(nil)) {
			root = y;
		} else if (d.equals(d.parent.left)) {
			d.parent.left = y;
		} else
			d.parent.right = y;
		y.left = d;
		d.parent = y;
	}

	// Moves node to right side of the tree.
	private void rotateRight(DiskLocation d) {
		DiskLocation y = d.left;
		d.left = y.right;
		if (!y.right.equals(nil)) {
			y.right.parent = d;

		}
		y.parent = d.parent;
		if (d.parent.equals(nil)) {
			root = y;
		} else if (d.equals(d.parent.right)) {
			d.parent.right = y;
		} else
			d.parent.left = y;
		y.right = d;
		d.parent = y;
	}

}
