package edu.wit.cs.comp3370;

// TODO: document class
public class BST extends LocationHolder {

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

	// Find parent
	private DiskLocation findParent(DiskLocation n, DiskLocation curr, DiskLocation parent) {

		if (curr.equals(nil))
			return parent;

		if (!n.isGreaterThan(curr))
			return findParent(n, curr.left, curr);

		return findParent(n, curr.right, curr);

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

	// Insert d into tree at appropriate spot,
	@Override
	public void insert(DiskLocation d) {
		// TODO: implement method
		if (root == null) {
			root = nil;
		}
		d.left = nil;
		d.right = nil;
		d.parent = findParent(d, root, nil);

		if (d.parent.equals(nil))
			root = d;
		else if (d.track < d.parent.track)
			d.parent.left = d;
		else
			d.parent.right = d;

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

}
