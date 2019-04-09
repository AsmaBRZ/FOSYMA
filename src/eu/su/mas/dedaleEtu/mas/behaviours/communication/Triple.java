package eu.su.mas.dedaleEtu.mas.behaviours.communication;

public class Triple<L,M,R> {

	  private final L left;
	  private final M middle;
	  private final R right;

	  public Triple(L left, M middle,R right) {
	    this.left = left;
	    this.middle=middle;
	    this.right = right;
	  }
	  public M getMiddle() { return middle; }
	  public L getLeft() { return left; }
	  public R getRight() { return right; }



	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Triple)) return false;
	    Triple<L,M,R> t = (Triple<L,M,R>) o;
	    return this.left.equals(t.getLeft()) &&
	           this.right.equals(t.getRight()) && this.middle.equals(t.getMiddle());
	  }

	}