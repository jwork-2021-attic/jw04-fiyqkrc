package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.DeepSearchMazeSolver;
import com.anish.calabashbros.Thing;

import mazeGenerator.MazeGenerator;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    String[] sortSteps;
    int mazeDim=World.WIDTH<World.HEIGHT? World.WIDTH:World.HEIGHT;

    public WorldScreen() {
        world = new World();
        MazeGenerator maze = new MazeGenerator(mazeDim);
        maze.generateMaze();
        boolean[][] mazeArray=getArrayRawMaze(maze);
        for (int x=0;x<mazeDim;x++)
            for(int y=0;y<mazeDim;y++){
                if(mazeArray[x][y]==false){
                    Wall wall=new Wall(world);
                    world.put(wall, x, y);
                }
            }
        DeepSearchMazeSolver solver=new DeepSearchMazeSolver();
        solver.loadMaze(mazeArray);
        String solution=solver.getSolution();
        sortSteps=solution.split("\n");
    }

    private boolean[][] getArrayRawMaze(MazeGenerator maze){
        String mazeStr=maze.getRawMaze();
        String[] mazeStrs = mazeStr.split("\n");
        boolean[][] res=new boolean[mazeDim][mazeDim];
        int x=0,y=0;
        for(String str : mazeStrs){
            y=0;
            str=str.substring(1, str.length()-1);
            String[] strs =str.split(", ");
            for(String s:strs) {
                res[x][y]=Integer.parseInt(s)==1?true:false;
                y++;
            }
            x++;
        }
        return res;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;
    Thing current;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(i==0){
            Calabash cala=new Calabash(new Color(204,0,0), 1, world);
            String step= sortSteps[0];
            String[] xy=step.substring(1, step.length()-1).split(",");
            world.put(cala,Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
            current=cala;
        }
        else if (i < this.sortSteps.length) {
            String step=sortSteps[i];
            if (step!=""){
                String[] xy=step.substring(1, step.length()-1).split(",");
                Thing temp=world.get(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
                int x=current.getX();
                int y=current.getY();
                world.put(current,Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
                world.put(temp,x,y);
            }
        }
        i++;

        return this;
    }

}
