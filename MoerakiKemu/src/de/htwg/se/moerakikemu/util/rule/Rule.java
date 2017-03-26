package de.htwg.se.moerakikemu.util.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class Rule {

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
	
	/**
	 * tell if the position is a possible position for a player
	 * @param position
	 * @return if the position is possible to set a dot 
	 */
	public static boolean isPositionPossibleInput(Point position) {
		if (position.x <= 0 || position.x >= 13 && position.y <= 0 || position.y >= 13)
			return false;
		
		if (evenNumber(position.x) && evenNumber(position.y) || !evenNumber(position.x) && !evenNumber(position.y))
			return false;

		return true;
	}

	private static boolean evenNumber(int number) {
		return number % 2 == 0;
	}
}
