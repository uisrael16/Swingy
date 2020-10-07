package com.uisrael.Controller;

import com.uisrael.Model.Hero;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.String.format;

public class HundleInput {
    FileWriter fileWriter;
    FileReader fileReader;

    public HundleInput() {

    }

    public int getInt() {
        Scanner scanner = new Scanner(System.in);
        int i;
        try {
            i = scanner.nextInt();
        } catch (Exception e) {
            System.out.println(" invalid input!!!!");
            return getInt();
        }
        return i;
    }

    public String getString() {
        Scanner scanner = new Scanner(System.in);
        String str = null;
        try {
            if (scanner.hasNext()) {
                str = scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getString();
        }
        return str;
    }

    public void writeHero(Hero p) throws FileNotFoundException {
        ArrayList<String> heroes = readHeroes();
        try {
            fileWriter = new FileWriter("Hero.csv");
            String[] line;
            boolean updated = false;
            if (heroes.size() > 0) {
                int i = 0;
                while (!(i >= heroes.size())) {
                    String hero = heroes.get(i);
                    line = hero.split(",");
                    if (p.getName().equals(line[0])) {
                        fileWriter.write(format("%s,%s,%s,%s,%s,%s,%s\n",
                                p.getName(),
                                p.getPlayerClass(),
                                p.getLevel(),
                                p.getEx(),
                                p.getAttack(),
                                p.getDefense(),
                                p.getHP()));
                        updated = isUpdated();
                    } else {
                        fileWriter.write(format("%s,%s,%s,%s,%s,%s,%s\n",
                                line[0],
                                line[1],
                                line[2],
                                line[3],
                                line[4],
                                line[5],
                                line[6]));
                    }
                    i++;
                }
                if (!updated)
                    fileWriter.write(format("%s,%s,%s,%s,%s,%s,%s\n",
                            p.getName(),
                            p.getPlayerClass(),
                            p.getLevel(),
                            p.getEx(),
                            p.getAttack(),
                            p.getDefense(),
                            p.getHP()));
            } else {
                fileWriter.write(format("%s,%s,%s,%s,%s,%s,%s\n",
                        p.getName(),
                        p.getPlayerClass(),
                        p.getLevel(),
                        p.getEx(),
                        p.getAttack(),
                        p.getDefense(),
                        p.getHP()));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUpdated() {
        boolean updated;
        updated = true;
        return updated;
    }

    public ArrayList<String> readHeroes() throws FileNotFoundException {
        Scanner scan;
        ArrayList<String> heroes = new ArrayList<>();
        String line;

        fileReader = new FileReader("Hero.csv");
        scan = new Scanner(fileReader);
        if (scan.hasNext()) {
            do {
                line = scan.nextLine();
                heroes.add(line);
            } while (scan.hasNext());
        }

        return heroes;
    }
}
