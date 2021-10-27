package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.anish.calabashbros.World;
import com.anish.calabashbros.Monster;
import com.anish.calabashbros.QuickSorter2D;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Monster[][] bros= new Monster[15][20];
    String[] sortSteps;

    public WorldScreen() {
        world = new World();
/*
        bros = new Calabash[7];

        bros[3] = new Calabash(new Color(204, 0, 0), 1, world);
        bros[5] = new Calabash(new Color(255, 165, 0), 2, world);
        bros[1] = new Calabash(new Color(252, 233, 79), 3, world);
        bros[0] = new Calabash(new Color(78, 154, 6), 4, world);
        bros[4] = new Calabash(new Color(50, 175, 255), 5, world);
        bros[6] = new Calabash(new Color(114, 159, 207), 6, world);
        bros[2] = new Calabash(new Color(173, 127, 168), 7, world);

        world.put(bros[0], 10, 10);
        world.put(bros[1], 12, 10);
        world.put(bros[2], 14, 10);
        world.put(bros[3], 16, 10);
        world.put(bros[4], 18, 10);
        world.put(bros[5], 20, 10);
        world.put(bros[6], 22, 10);
*/
        createMonsters(world, 300);

        QuickSorter2D<Monster> b = new QuickSorter2D<>();
        b.load(bros);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private void createMonsters(World line,int num){
        int[] place = new int[num]; 
        for(int i:place)
            place[i] = 0;
        int[] rgb={0,255,0};
        String[] changeRule={"bu","gd","ru","bd","gu","rd"};
        int deep = 1536/num;
        for(int i=0;i<num;i++){
            String action = changeRule[(i/(num/6))%6];
            int index=0;
            if(action.charAt(0)=='b'){
                index=2;
            }
            else if(action.charAt(0)=='g')
                index=1;
            else 
                index=0;
            if(action.charAt(1)=='u'){
                rgb[index]=(rgb[index]+deep)%256;
            }
            else
                rgb[index]=(rgb[index]-deep)%256;
            Monster monster = new Monster(new Color(rgb[0], rgb[1], rgb[2]),i,line);
            int rand = (new Random()).nextInt(num);
            rand=rand%num;
            while(place[rand%num]!=0){
                rand++;
                rand=rand%num;
            }
            bros[rand/20][rand%20]=monster;
            line.put(monster,10+2*(rand%20),5+2*(rand/20));
            place[rand]=1;
        }
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Monster[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Monster getBroByRank(Monster[][] bross, int rank) {
        for (Monster[] bros : bross) {
            for (Monster bro: bros)
                if (bro.getRank() == rank) {
                    return bro;
                }
        }
        return null;
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

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(bros, sortSteps[i]);
            i++;
        }

        return this;
    }

}
