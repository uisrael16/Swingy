package com.uisrael.View;

import com.uisrael.Controller.GameState;
import com.uisrael.Controller.HundleInput;
import com.uisrael.Model.Hero;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class GUIGame implements Play {
    @Override
    public void start() {
        JFrame main = new JFrame("Swingy Main");

        JButton create = new JButton("Create Hero");
        create.setBounds(60, 100, 120, 20);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHero();
            }
        });

        JButton select = new JButton("Select Hero");
        select.setBounds(60, 50, 120, 20);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectHero();
            }
        });

        main.add(select);
        main.add(create);
        main.setSize(260, 200);
        main.setLayout(null);
        main.setVisible(true);
    }

    public void createHero(){
        final JFrame create = new JFrame("Create Player");

        JLabel hero = new JLabel("Hero Name");
        hero.setBounds(50, 50, 100, 20);

        final JTextField heroName = new JTextField();
        heroName.setBounds(150, 50, 100, 20);

        JRadioButton spiderman = new JRadioButton("Spiderman");
        spiderman.setBounds(50, 80, 100, 20);
        spiderman.setActionCommand("Spiderman");
        spiderman.setSelected(true);

        JRadioButton spongebob = new JRadioButton("Spongebob");
        spongebob.setBounds(50, 110, 100, 20);
        spongebob.setActionCommand("Spongebob");

        JRadioButton wonderWoman = new JRadioButton("WonderWoman");
        wonderWoman.setBounds(50, 140, 150, 20);
        wonderWoman.setActionCommand("WonderWoman");

        final ButtonGroup classes = new ButtonGroup();
        classes.add(spiderman);
        classes.add(spongebob);
        classes.add(wonderWoman);

        final JLabel test = new JLabel();
        test.setBounds(50, 10, 100, 20);

        JButton start = new JButton("Start Game");
        start.setBounds(50, 180, 100, 20);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (heroName.getText().length() > 2) {
                    String actionCommand = classes.getSelection().getActionCommand();
                    Hero player = new Hero(heroName.getText(), actionCommand);
                    create.dispatchEvent(new WindowEvent(create, WindowEvent.WINDOW_CLOSING));
                    play(player);
                }
            }
        });

        create.add(hero);
        create.add(heroName);
        create.add(spiderman);
        create.add(spongebob);
        create.add(wonderWoman);
        create.add(start);
        create.add(test);
        create.setSize(300, 300);
        create.setLayout(null);
        create.setVisible(true);
    }

    public void selectHero(){
        final HundleInput input = new HundleInput();
        ArrayList<String> heroes = new ArrayList<>();
        try {
            heroes = input.readHeroes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (0 >= heroes.size()) {
            createHero();
        } else {
            final JFrame select = new JFrame("Select Hero");
            JLabel welcome = new JLabel("Select a Hero");
            welcome.setBounds(50, 50, 200, 20);
            select.add(welcome);

            final ButtonGroup heroGroup = new ButtonGroup();

            int i = 0;
            while (i < heroes.size()) {
                JRadioButton button = createSet(heroes.get(i).split(",")[0], i + 1);
                select.add(button);
                heroGroup.add(button);
                i++;
            }

            JButton playButton = new JButton("Start game");
            playButton.setBounds(50, heroes.size()*30+100, 100, 20);
            final ArrayList<String> finalHeroes = heroes;
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = heroGroup.getSelection().getActionCommand();
                    String[] line = finalHeroes.get(Integer.parseInt(actionCommand)).split(",");
                    Hero player = new Hero(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]));
                    select.dispatchEvent(new WindowEvent(select, WindowEvent.WINDOW_CLOSING));
                    play(player);
                }
            });

            select.add(playButton);
            select.setSize(300, 300);
            select.setLayout(null);
            select.setVisible(true);

        }
    }

    public JRadioButton createSet(String title, int count){
        JRadioButton but = new JRadioButton(title);
        if (count == 0) {
            but.setSelected(false);
        } else {
            but.setSelected(true);
        }
        but.setActionCommand(String.valueOf(count-1));
        but.setBounds(50, count*30+50, 150, 20);
        return but;
    }

    public void play(final Hero hero){
        final GameState gameState = new GameState(hero);
        final int mapSize = (hero.getLevel()-1)*5+10-(hero.getLevel()%2);
        gameState.Gridgenerater();
        JFrame game = new JFrame("Swingy Swingy");

        final JLabel info = new JLabel();
        info.setBounds(10, 10, 200, 20);
        game.add(info);

        JLabel player = new JLabel(hero.getName() + " - " + hero.getPlayerClass());
        player.setBounds(10, 50, 200, 20);

        final JLabel level = new JLabel("Level - " + hero.getLevel());
        level.setBounds(10, 80, 150, 20);
        game.add(level);

        final JLabel exp = new JLabel("Experience - " + hero.getEx());
        exp.setBounds(10, 110, 150, 20);
        game.add(exp);

        final JLabel att = new JLabel("Attack - " + hero.getAttack());
        att.setBounds(10, 140, 150, 20);
        game.add(att);

        final JLabel def = new JLabel("Defense - " + hero.getDefense());
        def.setBounds(10, 170, 150, 20);
        game.add(def);

        final JLabel hp = new JLabel("Hit Points - " + hero.getHP());
        hp.setBounds(10, 200, 150, 20);
        game.add(hp);


        final JTable table = new JTable();
        table.setBounds(200, 50, mapSize*20, mapSize*16);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        table.setModel(createModel(gameState, mapSize));

        JButton north = new JButton("North");
        north.setBounds(250 + mapSize*20, 50, 100, 20);
        north.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(gameState, hero, "N", info);
                playerMoved(hero, level, exp, att, def, hp);
                table.setModel(createModel(gameState, mapSize));
            }
        });

        JButton east = new JButton("East");
        east.setBounds(250 + mapSize*20, 80, 100, 20);
        east.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(gameState, hero, "E", info);
                playerMoved(hero, level, exp, att, def, hp);
                table.setModel(createModel(gameState, mapSize));
            }
        });

        JButton west = new JButton("West");
        west.setBounds(250 + mapSize*20, 110, 100, 20);
        west.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(gameState, hero, "W", info);
                playerMoved(hero, level, exp, att, def, hp);
                table.setModel(createModel(gameState, mapSize));
            }
        });

        JButton south = new JButton("South");
        south.setBounds(250 + mapSize*20, 140, 100, 20);
        south.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(gameState, hero, "S", info);
                playerMoved(hero, level, exp, att, def, hp);
                table.setModel(createModel(gameState, mapSize));
            }
        });

        game.add(table);
        game.add(north);
        game.add(east);
        game.add(west);
        game.add(south);
        game.add(player);
        game.setSize(400 + mapSize*20, 150 + mapSize*16);
        game.setLayout(null);
        game.setVisible(true);
    }

    private void movePlayer(final GameState gameState, final Hero hero, String direction, final JLabel info) {
        boolean moved = gameState.moveHero(hero.getY(), hero.getX(), direction, "");
        final HundleInput input = new HundleInput();
        if (gameState.meetEnemy()){
            final JFrame f = new JFrame("Fight");
            JLabel text = new JLabel("You met with the Villain");
            text.setBounds(10,10,150,20);
            f.add(text);


            final JButton fightEnemy = new JButton("Fight");
            fightEnemy.setBounds(10, 40, 75, 20);
            fightEnemy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameState.fight())
                        info.setText("You beat the Villain");
                    else {
                        try {
                            input.writeHero(hero);
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        System.exit(1);
                    }
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                }
            });
            f.add(fightEnemy);

            final JButton runAway = new JButton("Run");
            runAway.setBounds(90, 40, 75, 20);
            runAway.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Random rand = new Random();
                    if (rand.nextInt(2) == 2)
                        if (gameState.fight())
                            info.setText("You beat the Villain");
                    else
                        info.setText("Next time you will fight");
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                }
            });

            f.add(runAway);

            f.setSize(200, 200);
            f.setLayout(null);
            f.setVisible(true);
        }
        if (!moved){
            try {
                input.writeHero(hero);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }

    private void playerMoved(Hero hero, JLabel level, JLabel exp, JLabel att, JLabel def, JLabel hp) {
        level.setText("Level - " + hero.getLevel());
        exp.setText("Experience - " + hero.getEx());
        att.setText("Attack - " + hero.getAttack());
        def.setText("Defense - " + hero.getDefense());
        hp.setText("Hit Points - " + hero.getHP());
    }

    public TableModel createModel(final GameState gameState, final int mapSize){
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return mapSize; }
            public int getRowCount() { return mapSize;}
            public Object getValueAt(int row, int col) {
                String[][] map = gameState.getMapGrid();
                return map[row][col];
            }
        };
        return dataModel;
    }
}
