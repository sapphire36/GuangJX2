package org.kzcw.common.global;
public class Picdeliver {
	
	private static Picdeliver instance=new Picdeliver();
			
	public byte pic[];
	public static Picdeliver getInstance(){
		return instance;
	}
}