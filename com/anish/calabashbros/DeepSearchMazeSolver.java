package com.anish.calabashbros;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import mazeGenerator.*;

public class DeepSearchMazeSolver implements MazeSolver{

    private boolean maze[][];
    private String solution="";

    private Position start;
    private Position end;

    @Override
    public void loadMaze(boolean[][] maze,int[] start,int[] end) {
        this.maze=maze;
        this.start=new Position(start[0], start[1]);
        this.end=new Position(end[0], end[1]);
    }

    @Override
    public String getSolution() {
        if(solution=="")
        {
            this.execute();
            return solution;
        }
        else
            return solution;
    }

    private int width;
    private int height;

    private void execute(){
        width=maze[0].length;
        height=maze.length;
        Stack<Position> cross=new Stack<>();
        Stack<Position> path=new Stack<>();

        Position current=start;
        while(!current.isEqualWith(end)){
            ArrayList<Position> selectable=detectRoad(current);
            if(selectable.isEmpty()){
                while(!path.isEmpty()){
                    Position node=path.pop();
                    if(node.isEqualWith(cross.peek())){
                        maze[current.getX()][current.getY()]=false;
                        cross.pop();
                        current=node;
                        break;
                    }
                    else{
                        maze[current.getX()][current.getY()]=false;
                        current=node;
                    }
                }
                if(current.isEqualWith(start)){
                    solution="";
                    break;
                }
            }
            else{
                Position selectPath=selectable.get(0);
                selectable.remove(0);
                cross.push(current);
                maze[current.getX()][current.getY()]=false;
                path.push(current);
                current=selectPath;
            }
        }
        if(!current.isEqualWith(start))
            path.push(current);

        for (Position iterable_element : path) {
            solution+="{"+iterable_element.getX()+","+iterable_element.getY()+"}\n";
        }
    }

    private ArrayList<Position> detectRoad(Position p){
        ArrayList<Position> res=new ArrayList<>();
        if(positionValid(new Position(p.getX(), p.getY()+1)))
            res.add(new Position(p.getX(), p.getY()+1));
        if(positionValid(new Position(p.getX()+1, p.getY())))
            res.add(new Position(p.getX()+1, p.getY()));
        if(positionValid(new Position(p.getX(), p.getY()-1)))
            res.add(new Position(p.getX(), p.getY()-1));
        if(positionValid(new Position(p.getX()-1, p.getY())))
            res.add(new Position(p.getX()-1, p.getY()));
        return res;
    }

    private boolean positionValid(Position p){
        if(p.getX()>=0&&p.getX()<height)
            if(p.getY()>=0&&p.getY()<width)
                if(maze[p.getX()][p.getY()]==true)
                    return true;
        return false;
    }

    public static void main(String[] args){
        int testDim=100;
        DeepSearchMazeSolver s=new DeepSearchMazeSolver();
        MazeGenerator maze=new MazeGenerator(testDim);
        maze.generateMaze();
        int[] startNode={0,0};
        int[] endNode={testDim-1,testDim-1};
        s.loadMaze(getArrayRawMaze(maze,testDim),startNode,endNode);
        System.out.println(maze.getRawMaze());
        System.out.println(s.getSolution());
    }

    private static boolean[][] getArrayRawMaze(MazeGenerator maze,int mazeDim){
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
    
}

class Position{
    private int x;
    private int y;
    public Position(int a,int b){
        x=a;
        y=b;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isEqualWith(Position p){
        return (p.getX()==this.x)&&(p.getY()==this.y);
    }
}
