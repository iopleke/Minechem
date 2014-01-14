package pixlepix.particlephysics.common.helper;

public class CoordTuple {

	public int x;
	public int y;
	public int z;
	public CoordTuple(int x, int y, int z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	@Override
	public boolean equals(Object other){
		if(other instanceof CoordTuple){
			CoordTuple tuple=(CoordTuple) other;
			if(this.x==tuple.x&&this.y==tuple.y&&this.z==tuple.z){
				return true;
			}
		}
		return false;
		
	}
	
}
