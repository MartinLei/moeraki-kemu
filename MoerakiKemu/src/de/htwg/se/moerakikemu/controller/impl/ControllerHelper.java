package de.htwg.se.moerakikemu.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ControllerHelper {
	private int x,y, maxLength;
	private int[] squareArray;
	private boolean finished;
	private Square square;
	
	

	
	public ControllerHelper (int xCoordinates, int yCoordinates, int maxLength){
		this.x = xCoordinates;
		this.y = yCoordinates;
		this.maxLength = maxLength;
		this.finished = false;
		this.squareArray = new int[17];
		this.square = new Square();
		

	}

	public void testSquare(){
		square.test();
	}
	
	public int[] getSquareArray(){
		return squareArray;
	}
	
	public void resetSquareTest(){
		square.resetSquare();
	}
	
	class Square {
		private SquareState squareState;
		
		public Square(){
			squareState = new EdgeSquare();
		}
		
		public void setSquare(SquareState s){
			squareState = s;
		}
		
		public void resetSquare(){
			setSquare(new EdgeSquare());
		}
		
		public void test(){
			while(!finished){
				squareState.test(this);
			}
		}
	}
	
	interface SquareState{
		public void test(Square square);
	}
	
	class EdgeSquare implements SquareState{
		@Override
		public void test(Square square){
			if(x == 0 && y == 0){
				squareArray[0] = 1;
				setArray(1, x, y, x + 1, y + 1);
				finished = true;
			} else if(x == 0 && y == maxLength){
				squareArray[0] = 1;
				setArray(1, x, y, x + 1, y - 1);
				finished = true;
			} else if(x == maxLength && y == 0){
				squareArray[0] = 1;
				setArray(1, x, y, x - 1, y + 1);
				finished = true;
			} else if(x == maxLength && y == maxLength){
				squareArray[0] = 1;
				setArray(1, x, y, x - 1, y - 1);
				finished = true;
			}else {
				square.setSquare(new BorderSquare());
			}
		}
	}
	
	class BorderSquare implements SquareState{
		
		public BorderSquare(){
			test(square);
		}

		@Override
		public void test(Square square){
			if(x == 0){
				squareArray[0] = 2;
				setArray(1, x, y, x + 1, y + 1);
				setArray(5, x, y, x + 1, y + 1);
				finished = true;
			} else if(y == 0){
				squareArray[0] = 2;
				setArray(1, x, y, x + 1, y + 1);
				setArray(5, x, y, x - 1, y + 1);
				finished = true;
			} else if(x == maxLength){
				squareArray[0] = 2;
				setArray(1, x, y, x - 1, y + 1);
				setArray(5, x, y, x - 1, y - 1);
				finished = true;
			} else if(y == maxLength){
				squareArray[0] = 2;
				setArray(1, x, y, x - 1, y - 1);
				setArray(5, x, y, x + 1, y - 1);
				finished = true;
			} else {
				square.setSquare(new InnerSquare());
			}
		}
	}
	
	class InnerSquare implements SquareState{
		
		public InnerSquare(){
			test(square);
		}

		@Override
		public void test(Square square){
			if(x < maxLength && y < maxLength){
				squareArray[0] = 4;
				setArray(1, x, y, x + 1, y - 1);
				setArray(5, x, y, x - 1, y - 1);
				setArray(9, x, y, x + 1, y + 1);
				setArray(13, x, y, x - 1, y + 1);
				finished = true;
			}
		}
	}

	
	private void setArray(int start, int edgeOne, int edgeTwo, int edgeThree, int edgeFour){
		squareArray[start] = edgeOne;
		squareArray[start + 1] = edgeTwo;
		squareArray[start + 2] = edgeThree;
		squareArray[start + 3] = edgeFour;
	}
}
