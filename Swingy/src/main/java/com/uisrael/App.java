package com.uisrael;

import com.uisrael.View.Play;
import com.uisrael.View.GUIGame;
import com.uisrael.View.ConsoleGame;

public class App {
    public static void main(String[] args) {
        Play game;
        if (!(args.length <= 0)){
            if ("Console".equalsIgnoreCase(args[0])) {
                game = new ConsoleGame();
                game.start();
            } else {
                if ("GUI".equalsIgnoreCase(args[0])) {
                    game = new GUIGame();
                    game.start();
                } else {
                    Print();
                }
            }
        } else {
            Print();
        }
    }

    private static void Print() {
        System.out.println("Please type the following command to play the game \n" +
                           " java -jar Swingy-1.0.jar Console or GUI");
    }
}
