package Frame;

import Frame.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import Frame.*;
import Dao.*;
import View.*;
public class KeyListener extends KeyAdapter {
    //导入对象
    private GameFrame gf;

    public KeyListener(GameFrame gf) {
        this.gf = gf;
    }

    //按下按键时
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gf.isIfDialog()) {
            if (code == 65) {
                gf.setKey("A");
            }
            if (code == 66) {
                gf.setKey("B");
            }
            if (code == 67) {
                gf.setKey("C");
            }
            if (code == 68) {
                gf.setKey("D");
            }
            if (code == 32) {
                gf.setKey("Continue");
            }
        }
        if(!gf.isIfDialog()) {
            switch (code) {
                case 37://left
                    gf.actor.setState(3, 1);
                    gf.actor.setDir(3);
                    break;
                case 38://up
                    gf.actor.setState(1, 1);
                    gf.actor.setDir(1);
                    break;
                case 39://right
                    gf.actor.setState(4, 1);
                    gf.actor.setDir(4);
                    break;
                case 40://down
                    gf.actor.setState(2, 1);
                    gf.actor.setDir(2);
                    break;
                case 68: {
                    gf.getMenu().setVisible(true);
                    break;
                }
                case 27: {
                    HelpFrame ef = new HelpFrame();
                    break;
                }
            }
        }
    }
    //松开按键
    public void keyReleased(KeyEvent e) {
        System.out.println(gf.isIfDialog());
        int code = e.getKeyCode();
        //37 左    65 A
        //38 上    66 B
        //39 右    67 C
        //40 下    68 D

        if (!gf.isIfDialog()) {
            if (code == 39) {
                gf.actor.setState(4, 0);
            }
            if (code == 37) {
                gf.actor.setState(3, 0);
            }
            if (code == 38) {
                gf.actor.setState(1, 0);
            }
            if (code == 40) {
                gf.actor.setState(2, 0);
            }
            //D
            if (code == 68) {
                gf.getMenu().setVisible(false);
            }
            //C
            if (code == 67) {
                //如果是测试形态
                if (gf.isTest()) {
                    int i, j;
                    int check = 0;
                    int wallsize = gf.getGameMap().wallList.size();
                    int watersize = gf.getGameMap().waterList.size();
                    for (i = 0; i < wallsize; ++i) {
                        Square wall = gf.getGameMap().wallList.get(i);
                        //判断角色矩形与空气墙矩形是否交叉
                        if (gf.actor.getActorRect().intersects(wall.getRectangle())) {
                            check = i;
                            gf.getGameMap().wallList.remove(i);
                            break;
                        }
                    }
                    //判断和水墙是否交叉
                    for (j = 0; j < watersize; ++j) {
                        Square water = gf.getGameMap().waterList.get(j);
                        if (gf.actor.getActorRect().intersects(water.getRectangle())) {
                            check = -j;
                        }
                    }
                    if (check == 0) {
                        Square wall = new Square((gf.actor.getX()) / 32 * 32, (gf.actor.getY()) / 32 * 32, gf.getGameMap().x, gf.getGameMap().y, 32, 32);
                        System.out.println("当前位置:x: " + (gf.actor.getX() + gf.getGameMap().x) + " y: " + (gf.actor.getY() - gf.getGameMap().y));
                        gf.getGameMap().wallList.add(wall);
                    }
                } else {
                    if (gf.actorData.isLive()) {
                        gf.actor.ATK();
                    } else if (gf.actorData.isDeath()) {//已知，按得太快导致不显示死亡提示
                        gf.getGameMap().initAirWall();
                        gf.actorData.setActorHPF(200);
                        gf.actorData.setActorHPT(200);
                        gf.actorData.hpControl(9999);
                        gf.actorData.spControl(9999);
                        gf.actorData.setLive(true);
                        gf.ChangeMap(2, 20 * 32, 13 * 32,1);
                    }
                }
            }
            //X
            //如果是添加水
            if (code == 88) {
                if (gf.isTest()) {
                    int i, j;
                    int check = 0;
                    int wallsize = gf.getGameMap().wallList.size();
                    int watersize = gf.getGameMap().waterList.size();
                    for (i = 0; i < wallsize; ++i) {
                        Square wall = gf.getGameMap().wallList.get(i);
                        //判断角色矩形与空气墙矩形是否交叉
                        if (gf.actor.getActorRect().intersects(wall.getRectangle())) {
                            check = i;
                        }
                    }
                    for (j = 0; j < watersize; ++j) {
                        Square water = gf.getGameMap().waterList.get(j);
                        if (gf.actor.getActorRect().intersects(water.getRectangle())) {
                            check = -j;
                            gf.getGameMap().waterList.remove(j);
                            break;
                        }
                    }
                    if (check == 0) {
                        Square wall = new Square((gf.actor.getX()) / 32 * 32, (gf.actor.getY()) / 32 * 32, gf.getGameMap().x, gf.getGameMap().y, 32, 32);
                        System.out.println("当前位置:x: " + gf.actor.getX() + " y: " + gf.actor.getY());
                        gf.getGameMap().waterList.add(wall);
                    }
                }
            }
            //Z
            if (code == 90) {
                gf.actorData.changeArm();
            }
            //A
            if (code == 65) {
                gf.actorData.changeItem();
            }
            //S
            if (code == 83) {
                gf.actorData.useItem(0);
            }
            //F12 test
            if (code == 123) {
                if (gf.isTest()) {
                    gf.setTest(false);
                    try {
                        gf.getGameMap().writeAirWall();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else gf.setTest(true);
            }
        }
    }
}
