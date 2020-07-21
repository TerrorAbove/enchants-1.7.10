package com.terrorAndBlueMods.weaponDistanceEnchant;

public class Location
{
	public double x, y, z;
	public int dim;
	
	public Location(double x, double y, double z, int dim)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Location))
			return false;
		
		Location loc = (Location)o;
		
		return this.x == loc.x && this.y == loc.y && this.z == loc.z && this.dim == loc.dim;
	}
	
	public double distance(Location other)
	{
		return Math.sqrt(Math.pow(this.x-other.x, 2) + Math.pow(this.y-other.y, 2) + Math.pow(this.z-other.z, 2));
	}
}
