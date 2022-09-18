package Service;

import javax.swing.*;
import java.awt.*;

import Dao.Event;
import Frame.*;
import Dao.*;
import View.*;
public class EventController extends Event {
    private GameMap gameMap;

    public EventController(int mid, int oldX, int oldY, int eid) {
        super(mid, oldX, oldY, eid);
    }

    public void draw(int x, int y, Graphics g){
        timer++;
        if (timer == 10&&getFrame()!=1){
            frame++;
            timer = 0;
        }
        if (frame == getFrame()) {
            frame = 1;
        }
        if (cost != 0){
            g.setFont(font);
            g.setColor(Color.CYAN);
            str = "$"+cost;
            g.drawString(str,(originX-oldX)+x+getDX()+getWidth()/2-str.length()*3,(originY-oldY)+y+getDY()-5);
        }
        g.drawImage(getEventimg(),(originX-oldX)+x+getDX(),(originY-oldY)+y+getDY(),null);
    }

    //事件页
    public void eventPlay(GameFrame gf) {
        if (mapId == 0){
            if (eventId == 0){
                //掉落物
                if (lootID == 500){
                    gf.actorData.adjustMoney((int)(Math.random()*20+11));
                    this.setLive(false);
                }
                if (lootID >= 1&&lootID <= 6){
                    gf.actorData.getItem(new ItemController(lootID-1,0,1),1);
                    this.setLive(false);
                }
                if (lootID >= 101&&lootID <= 200){
                    int s;
                    s = lootID - 100;
                    lootID = gf.actorData.getHat() + 100;
                    gf.actorData.setHat(s);
                    cost = 0;
                }
                if (lootID >= 201&&lootID < 300){
                    int s;
                    s = lootID - 200;
                    lootID = gf.actorData.getEquip() + 200;
                    gf.actorData.setEquip(s);
                    cost = 0;
                }
                if (lootID >= 300&&lootID <= 400){
                    int s;
                    s = lootID - 300;
                    if (gf.actorData.arms.size() == 3){
                        lootID = gf.actorData.arms.get(0).getId() + 300;
                        gf.actorData.getArm(new ArmController(s));
                    }else {
                        gf.actorData.getArm(new ArmController(lootID-300));
                        this.setLive(false);
                    }
                }
            }else System.out.println(mapId +"地图"+ eventId +"事件"+"启动");
        }
        if (mapId == 1){
            //传送点
            if (eventId == 0){
                gf.ChangeMap(2,5*32,10*32,1);
            }
            //路障
            if (eventId == 1){
                if (gf.getGameMap().enemyList.size() == 1){
                    this.setLive(false);
                }
            }
            if(eventId==2)
            {
                gf.setIfDialog(true);
                this.setLive(false);
            }
            //地图卷动
            if (eventId == 3){
                gf.gameMapScroll(0,-580);
                this.setLive(false);
            }
        }
        if (mapId == 2){
            //传送门
            if (eventId == 0){
                if (gf.getGameMap().enemyList.size() == 0) {
                    gf.ChangeMap(3,5*32 , 5*32, 1);
                }
            }
            if (eventId == 1){
                if (gf.actorData.getMoney() >= cost){
                    //商店
                    for (int i = 2; i < gf.getGameMap().eventList.size(); i++) {
                        if (gf.getGameMap().eventList.get(i).isLive()&&((gf.getGameMap().eventList.get(i).eventId == 2||gf.getGameMap().eventList.get(i).eventId == 3||gf.getGameMap().eventList.get(i).eventId == 4))){
                            gf.getGameMap().eventList.remove(i);
                            gf.getGameMap().eventList.add(i,new EventController(gf.getGameMap().id,gf.getGameMap().x,gf.getGameMap().y,i));
                        }
                    }
                    gf.actorData.adjustMoney(-cost);
                }
            }
            if (eventId == 2|| eventId == 3|| eventId == 4){
                //商品
                if (gf.actorData.getMoney() >= cost){
                    if (lootID <= 100){
                        gf.actorData.getItem(new ItemController(lootID-1,0,1),1);
                        gf.actorData.adjustMoney(-cost);
                        this.setLive(false);
                    }
                    if (lootID >= 101&&lootID <= 200){
                        int s;
                        s = lootID - 100;
                        lootID = gf.actorData.getHat() + 100;
                        gf.actorData.setHat(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 201&&lootID < 300){
                        int s;
                        s = lootID - 200;
                        lootID = gf.actorData.getEquip() + 200;
                        gf.actorData.setEquip(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 300&&lootID <= 400){
                        int s;
                        s = lootID - 300;
                        if (gf.actorData.arms.size() == 3){
                            lootID = gf.actorData.arms.get(0).getId() + 300;
                            gf.actorData.getArm(new ArmController(s));
                        }else {
                            gf.actorData.getArm(new ArmController(lootID-300));
                            this.setLive(false);
                        }
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                }
            }
            if(eventId==5)
            {
                gf.setIfDialog(true);
                this.setLive(false);
            }
        }
        if (mapId == 3){
            if (eventId == 0){
                gf.setIfDialog(true);
                this.setLive(false);
            }
            //传送点右
            if (eventId == 1){
                if (gf.getGameMap().enemyList.size() == 0){
                    this.setLive(false);
                gf.setWin(true);
                }
            }
        }

//                gf.actorData.hpControl(9999);
//                gf.actorData.spControl(9999);
//                gf.actor.addEffect(12);

        }
    }
