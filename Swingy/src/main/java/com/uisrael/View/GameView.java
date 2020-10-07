package com.uisrael.View;

public class GameView {
    public void showGrid(String[][] map){
        int i = 0, mapLength = map.length;
        while (i < mapLength) {
            String[] str = map[i];
            int j = 0;
            while (j < str.length) {
                String v = str[j];
                System.out.print(v + " ");
                j++;
            }
            System.out.println();
            i++;
        }
    }

    public void directions(){
        printLn("\n|-----------------------------|\n", "| Please enter move direction |\n", "|                             |\n", "| 1.  for North               |\n", "| 2.  for East                |\n", "| 3.  for West                |\n", "| 4.  for South               |\n", "|_____________________________|");
    }

    private void printLn(String s, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
        System.out.println(s +
                s2 +
                s3 +
                s4 +
                s5 +
                s6 +
                s7 +
                s8);
    }

    public void showWin(){
        printLn("\n|------------------------------|\n", "| Game Over, you have won           |\n", "| Play again to increase you level  |\n");
    }

    private void printLn(String s, String s2, String s3) {
        System.out.println(s +
                "|                                   |\n" +
                s2 +
                "|                                   |\n" +
                s3 +
                "|                                   |\n" +
                "|___________________________________|");
    }

    public void fightOrRun() {
        printLn("\n|--------------------------\n", "|                           |\n", "| You met with the Villain  |\n", "|                           |\n", "| 1. Run                    |\n", "| 2. Fight                  |\n", "|                           |\n", "|___________________________|");
    }

    public void GameOver() {
        getPrintln("\n|---------------------|\n", "| Game Over, you have lost          |\n", "| Try again to increase you level   |\n");
    }

    private void getPrintln(String s, String s2, String s3) {
        System.out.println(s +
                "|                                   |\n" +
                s2 +
                "|                                   |\n" +
                s3 +
                "|                                   |\n" +
                "|___________________________________|");
    }
}
