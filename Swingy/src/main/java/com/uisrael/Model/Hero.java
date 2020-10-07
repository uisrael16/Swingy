package com.uisrael.Model;

import lombok.Setter;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Random;

@Setter
@Getter
public class Hero {
    @NotNull(message="Hero name cannot be blank")
    @Length(min=3, max=10, message="Hero name must be between 3 and 10 in length")
    private String name;
    private String playerClass;
    private int level;
    private int ex;
    private int attack;
    private int defense;
    private int HP;
    private int y;
    private int x;

    public Hero(String name, String playerClass){
        this.name = name;
        this.playerClass = playerClass;
        this.level = 1;
        this.ex = 500;
        this.attack = attack();
        this.defense = defense();
        this.HP = 100;
        this.y = 4;
        this.x = 4;
    }

    public Hero(String name, String playerClass, int level, int ex, int attack, int defense, int HP){
        this.name = name;
        this.playerClass = playerClass;
        this.level = level;
        this.ex = ex;
        this.attack = attack;
        this.defense = defense;
        this.HP = HP;
        this.y = heroposition(level);
        this.x = heroposition(level);
    }

    private int heroposition(int level){
        int mapSize = (level-1)*5+10-(level%2);
        return (mapSize-1)/2;
    }

    private int attack(){
        Random rand = new Random();
        int[] attack;
        attack = new int[]{10, 15, 20, 25};
        return attack[rand.nextInt(4)];
    }

    private int defense(){
        Random rand = new Random();
        int[] attack;
        attack = new int[]{10, 15, 20, 25};
        return attack[rand.nextInt(4)];
    }
}
