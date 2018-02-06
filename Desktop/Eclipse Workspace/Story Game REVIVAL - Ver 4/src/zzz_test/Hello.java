package zzz_test;

import util.Dfl;

public class Hello extends Hi
{
	public void stuff()
	{
		Dfl.out("Hello");
	}
	
	static Hello.Inner h = (new Hello()).new Inner();
	
	public static void main(String[] args)
	{
		Hi thing = new Hello();
		thing.stuff();
	}
}
