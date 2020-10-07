package com.uisrael.Controller;

import com.uisrael.Model.Hero;
import com.uisrael.View.GameView;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

@Setter
@Getter
public class GameState {
    Hero hero;
    ArrayList<Hero> enemies = new ArrayList<>();
    String[][] mapGrid;
    final ThreadLocal<String[]> enemyNames = new ThreadLocal<String[]>() {
        @Override
        protected String[] initialValue() {
            return new String[]{"Smokegolem", "The Dirty Woman", "The Volatile Mutt", "Brinepest", "The Dangerous Anomaly"};
        }
    };
    final ThreadLocal<String[]> classes = new ThreadLocal<String[]>() {
        @Override
        protected String[] initialValue() {
            return new String[]{"Level1", "Level2", "Level3", "Level4", "Level5", "Level6", "Level7"};
        }
    };
    HundleInput input = new HundleInput();
    GameView view = new GameView();
    boolean win = false;

    public GameState(Hero player){
        this.hero = player;
    }

    public void play(){
        boolean play = true;
        String direction;
        EnemiesFactory(hero.getLevel() * 5, hero.getLevel());
        MapGrid(hero.getLevel(), ".");
        mapPlayers();
        view.showGrid(this.mapGrid);
        while(play){
            view.directions();
            direction = input.getString();
            play = moveHero(hero.getY(), hero.getX(), direction, ".");
            if (meetEnemy()){
                view.fightOrRun();
                int run = input.getInt();
                if (run == 1){
                    Random rand = new Random();
                    switch (rand.nextInt(2)) {
                        case 2:
                            if (fight())
                                System.out.println("You beat the Enemy");
                            break;
                    }
                } else
                    if (fight())
                        System.out.println("You beat the Enemy");
                    else {
                        view.GameOver();
                        try {
                            input.writeHero(this.hero);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
            }
            view.showGrid(this.mapGrid);
        }
        if (win){
            try {
                input.writeHero(this.hero);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            view.showWin();
        }
    }

   public void EnemiesFactory(int number, int level){
        Random random = new Random();
        Hero enemy;
        boolean exist = false;
        int mapSize = (level-1)*5+10-(level%2);
        int x, y;
        int enemyCount = number;
        while(enemyCount > 0){
            x = random.nextInt(mapSize);
            y = random.nextInt(mapSize);
            int i = 0;
            while (i < enemies.size()) {
                Hero p = enemies.get(i);
                if (p.getX() == x && y == p.getY()) {
                    exist = true;
                    break;
                }
                i++;
            }
            if (!exist){
                String playerClass = classes.get()[random.nextInt(level)];
                int[] stats = statsInfo(playerClass);
                enemy = new Hero(enemyNames.get()[random.nextInt(5)], playerClass, stats[0], stats[1], stats[2], stats[3], stats[4]);
                enemy.setX(x);
                enemy.setY(y);
                enemies.add(enemy);
                enemyCount--;
            }
            exist = false;
        }
   }

    private int[] statsInfo(String playerClass) {
        if ("Level1".equals(playerClass)) {
            return new int[]{1, 1000, 20, 15, 50};
        } else if ("Level2".equals(playerClass)) {
            return new int[]{2, 2000, 25, 30, 100};
        } else if ("Level3".equals(playerClass)) {
            return new int[]{3, 3000, 35, 30, 150};
        } else if ("Level4".equals(playerClass)) {
            return new int[]{4, 4000, 40, 45, 200};
        } else if ("Level5".equals(playerClass)) {
            return new int[]{5, 5000, 50, 45, 250};
        } else if ("Level6".equals(playerClass)) {
            return new int[]{6, 6000, 55, 60, 300};
        }
        return new int[]{7, 7000, 65, 60, 350};
    }

    public void MapGrid(int level, String val){
        int mapSize = (level-1)*5+10-(level%2);
        this.mapGrid = new String[mapSize][mapSize];
        int i = 0;
        while (i < mapSize) {
            int j = 0;
            while (j < mapSize) {
                mapGrid[i][j] = val;
                j++;
            }
            i++;
        }
    }

    public void mapPlayers(){
        putHero(hero.getY(), hero.getX(), 1);

        int i = 0;
        while (i < enemies.size()) {
            Hero p = enemies.get(i);
            putHero(p.getY(), p.getX(), 2);
            i++;
        }
    }

    public void putHero(int y, int x, int player){
        if (player == 1){
            mapGrid[y][x] = "H";
        } else if(player == 2) {
            mapGrid[y][x] = "E";
        }
    }

    public boolean moveHero(int y, int x, String d, String val){
        int mapSize = mapGrid.length - 1;
        if ((d.equalsIgnoreCase("North") && y - 1 < 0) ||
                (d.equalsIgnoreCase("East") && x + 1 > mapSize) ||
                (d.equalsIgnoreCase("West") && x - 1 < 0) ||
                (d.equalsIgnoreCase("South") && y + 1 > mapSize)){
            win = true;
            return false;
        }

        if ("1".equals(d.toUpperCase())) {
            mapGrid[y][x] = val;
            mapGrid[y - 1][x] = "H";
            hero.setY(y - 1);
        } else if ("2".equals(d.toUpperCase())) {
            mapGrid[y][x] = val;
            mapGrid[y][x + 1] = "H";
            hero.setX(x + 1);
        } else if ("3".equals(d.toUpperCase())) {
            mapGrid[y][x] = val;
            mapGrid[y][x - 1] = "H";
            hero.setX(x - 1);
        } else if ("4".equals(d.toUpperCase())) {
            mapGrid[y][x] = val;
            mapGrid[y + 1][x] = "H";
            hero.setY(y + 1);
        }
        return true;
    }

    public boolean fight() {
        int i = 0;
        while (i < enemies.size()) {
            Hero e = enemies.get(i);
            if (hero.getX() == e.getX() && hero.getY() == e.getY()){
                int levelup = e.getLevel()*1000+(int)(Math.pow(Double.parseDouble(String.valueOf(e.getLevel()-1)), 2))*450;
                if (hero.getAttack() > e.getAttack() && hero.getDefense() > e.getDefense()){
                    hero.setAttack(hero.getAttack() + 5);
                    hero.setDefense(hero.getDefense() + 5);
                    hero.setEx(hero.getEx() + (levelup/2));
                    hero.setHP(hero.getHP() + 20);
                    hero.setLevel(HeroLevel(hero.getEx()));
                    enemies.remove(enemies.get(i));
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    public int HeroLevel(int exp){
        if (exp < 2450) return 1;
        else if (exp < 4800)
            return 2;
        else if (exp < 8050)
            return 3;
        else if (exp < 12200)
            return 4;
        else if (exp < 17250)
            return 5;
        else if (exp < 23200)
            return 6;
        else {
            return 7;
        }
    }

    public boolean meetEnemy(){
        int i = 0;
        while (i < enemies.size()) {
            Hero e = enemies.get(i);
            if (hero.getX() == e.getX() && hero.getY() == e.getY()) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void Gridgenerater(){
        EnemiesFactory(hero.getLevel() * 5, hero.getLevel());
        MapGrid(hero.getLevel(), "");
        mapPlayers();
    }
}
