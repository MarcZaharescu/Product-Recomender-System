
public class Pair<L, R> {
	public L x;
	public R y;

	public Pair(L x, R y) {
		this.x = x;
		this.y = y;
	}

	public L getL() {
		return this.x;
	}

	public R getR() {
		return this.y;
	}

	public void setL(L x) {
		this.x = x;
	}

	public void setR(R y) {
		this.y = y;
	}

}