package com.anish.calabashbros;

public interface MazeSolver {
    public void loadMaze(boolean[][] maze,int[] start,int[] end);
    public String getSolution();
}
