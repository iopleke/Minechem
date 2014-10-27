package minechem.utils;

public class Rect
{
	int x, y;
	int width, height;

	public Rect(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersectsWith(Rect other)
	{
		return other.width + other.x > this.x
				&& other.x < this.x + this.width
				&& other.y + other.height > y
				&& other.y < this.y + this.height;
	}
}
