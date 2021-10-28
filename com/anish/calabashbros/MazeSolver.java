package com.anish.calabashbros;

import com.anish.calabashbros.DeepSearchMazeSolver.Position;

public interface MazeSolver {
    public void loadMaze(boolean[][] maze,Position start,Position end);
    public String getSolution();
}
