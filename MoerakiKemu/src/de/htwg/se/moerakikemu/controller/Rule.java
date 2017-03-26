package de.htwg.se.moerakikemu.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rule {

	private static final List<Point> allowedStartPosition = new ArrayList<>();
	
	public Rule(){
		initAllowedStartPosition();
	}
	
	private void initAllowedStartPosition(){
		allowedStartPosition.add(new Point(6,4));
		allowedStartPosition.add(new Point(5,5));
		allowedStartPosition.add(new Point(7,5));
		allowedStartPosition.add(new Point(4,6));
		allowedStartPosition.add(new Point(6,6));
		allowedStartPosition.add(new Point(8,6));
		allowedStartPosition.add(new Point(5,7));
		allowedStartPosition.add(new Point(7,7));
		allowedStartPosition.add(new Point(6,8));
		
	}

	public boolean isStartDotPosCorrect(Point position){
		for(Point p : allowedStartPosition)
			if(p.equals(position))
				return true;
		
		return false;
	}
}
