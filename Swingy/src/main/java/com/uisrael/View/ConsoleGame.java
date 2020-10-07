package com.uisrael.View;

import com.uisrael.Controller.GameState;
import com.uisrael.Controller.HundleInput;
import com.uisrael.Model.Hero;
import com.uisrael.Model.ValidateUserInput;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ConsoleGame implements Play {
    HundleInput input = new HundleInput();
    ValidateUserInput validator;

    {
        validator = new ValidateUserInput();
    }

    public ConsoleGame(){}
    public void start(){
        GameState game;
        Hero hero = null;
        boolean errors = true;
        printLn("|--------------------|\n" +
                "| Welcome to Swingy  |\n" +
                "|                    |\n" +
                "| 1. Create Player   |\n" +
                "| 2. Select Player   |\n" +
                "|____________________|\n" +
                "Choose option: ");
        int option = input.getInt();
        switch (option) {
            case 1:
                while (errors) {
                    hero = createHero();
                    errors = validator.validate(hero);
                }
                game = new GameState(hero);
                game.play();
                break;
            case 2:
                while (errors) {
                    hero = selectHero();
                    errors = validator.validate(hero);
                }
                hero.setX(PositionOfHero(hero.getLevel()));
                hero.setY(PositionOfHero(hero.getLevel()));
                game = new GameState(hero);
                game.play();
                break;
            default:
                printLn("Invalid choice");
                start();
                break;
        }
    }

    private void printLn(String s) {
        System.out.println(s);
    }

    public Hero createHero(){
        String name, playerClass;
        printLn("Please enter Hero name: ");
        name = input.getString();
        playerClass = classDisplay();
        return new Hero(name, playerClass);
    }

    public Hero selectHero() {
        String[] line;
        ArrayList<String> heroes = null;
        try {
            heroes = input.readHeroes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (heroes.size() <= 0 ) {
            printLn("No heroes saved, create a new one");
            return createHero();
        } else {
            printLn("Select a Hero index:\n");
            int i = 0;
            if (i < heroes.size()) {
                do {
                    System.out.println(i + 1 + ". " + heroes.get(i).split(",")[0]);
                    i++;
                } while (i < heroes.size());
            }
            line = heroes.get(input.getInt()-1).split(",");
        }
        return (new Hero(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
    }

    public int PositionOfHero(int level){
        int mapSize = (level-1)*5+10-(level%2);
        return (mapSize-1)/2;
    }

    public String classDisplay() {
        printLn("|------------------- |\n" +
                            "| Choose class       |\n" +
                            "| 1. Batman          |\n" +
                            "| 2. Batgirl         |\n" +
                            "| 3  Doctor Strange  |\n" +
                            "|____________________|");
        int num = input.getInt();
        if (num == 1) {
            return "Batman";
        } else if (num == 2) {
            return "Batgirl";
        } else if (num == 3) {
            return "Doctor Strange";
        }
        return "Please choose one of the above hero classes";
    }
}