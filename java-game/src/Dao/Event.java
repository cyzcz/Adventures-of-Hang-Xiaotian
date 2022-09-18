package Dao;
import javax.swing.*;
import java.awt.*;
import Frame.*;
import Dao.*;
import Service.ArmController;
import Service.GameMap;
import View.*;
public class Event {
    protected int x,y;
    protected int width,height;
    protected int dx,dy;
    protected int oldX, oldY;
    protected int originX, originY;
    protected int lootID;
    protected int mapId;
    protected int eventId;
    protected int type;//事件类型：0：与角色接触执行 1：自动执行
    protected int frame = 1;
    protected int cost = 0;
    protected int timer = 0;
    protected boolean live;
    protected boolean action = true;
    protected Rectangle actorRect = null;
    protected Image img = null;
    protected Font font = new Font("宋体", Font.PLAIN, 12);
    protected String str = null;

    protected int[][][] eventArrayList = {
            /*0type    1x      2y      3w     4h      5f     6live  7dx    8dy    9掉落物类型(0：不是 1：是 2：商品 3:商人 4:npc)*/
            {{0,      0,      0,     24,     24,     1,     1,     0,     0,     1},//掉落物
                    {0,      0,      0,      0,      0,     1,     0,     0,     0,     0}},//0
            {{0,    610,     2,     40,     30,      4,     1,   -22,   -50,     0},//传送门
                    {1,    576,    224,     64,     32,     1,     1,    16,     0,     1},//路障
                     {0,     20*32, 15*32,   32,     32,     1,     1,     0,     0,      4},
                    {1,      0,      0,      0,      0,     0,     1,     0,     0,     0}},//1
            {{0,    38*32,    8*32,     40,     40,     4,     1,   -22,   -50,     0},//传送门
                    {0,    4*32,    4*32,     32,     32,     1,     1,     0,     0,     3},//商人
                    {0,    4*32-40,    4*32+50,     24,     24,     1,     1,     0,     0,     2},//商品1
                    {0,    4*32,    4*32+50,     24,     24,     1,     1,     0,     0,     2},//商品2
                    {0,    4*32+40,    4*32+50,     24,     24,     1,     1,     0,     0,     2},//商品3
                    {0,     25*32, 4*32,   32,     32,     1,     1,     0,     0,      4},
                    {0,      0,      0,     0,       0,     1,     0,     0,     0,     0}},//2
            {{0,    15*32,      9*32,   32,     32,     1,     1,     0,     0,     4},
                    {0,   37*32,    10*32,     32,    130,     1,     1,     0,     0,     0}},//3
    };

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public Event(int mid, int oldX, int oldY, int eid) {
        mapId = mid;
        eventId = eid;
        this.oldX = oldX;
        this.oldY = oldY;
        this.type = eventArrayList[mapId][eid][0];
        this.x = oldX+eventArrayList[mapId][eid][1];
        this.y = oldY+eventArrayList[mapId][eid][2];
        this.width = eventArrayList[mapId][eid][3];
        this.height = eventArrayList[mapId][eid][4];
        this.dx = eventArrayList[mapId][eid][7];
        this.dy = eventArrayList[mapId][eid][8];
        if (eventArrayList[mapId][eid][6] == 1){//其掉落倍率在GameMap中写
            this.live = true;
        }
        if (eventArrayList[mapId][eid][9] == 2){
            int s = (int) (Math.random()*16+1);
            if (s >= 1&&s<=5){
                lootID = s;
            }
            if (s >= 6&&s<=8){
                lootID = 100+s-5;
            }
            if (s >= 9&&s<=11){
                lootID = 200+s-8;
            }
            if (s >= 12&&s<=16){
                lootID = 300+s-11;
            }
            cost = s*10;
        }
        if (eventArrayList[mapId][eid][9] == 3){
            cost = 20;
        }
        getEventimg();
    }

    public int getEventId() {
        return eventId;
    }

    public int getLootID() {
        return lootID;
    }

    public void setLootID(int lootID) {
        this.lootID = lootID;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getType() {
        return type;
    }

    public boolean isAction(){
        return action;
    }

    public boolean isLive() {
        return live;
    }

    public int getDX(){
        return dx;
    }

    public int getDY(){
        return dy;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isLoot(){
        if (lootID != 0){
            return true;
        }
        return false;
    }

    public int getFrame(){
        return eventArrayList[mapId][eventId][5];
    }

    public Image getEventimg() {
        if (isLoot()){
            if (lootID == 500) {
                img = new ImageIcon("src/image/Item/prop/coin.png").getImage();//金币
            }
            if (lootID >= 1 && lootID <= 100) {
                img = new ImageIcon("src/image/Item/prop/item_" + lootID + ".png").getImage();//道具
            }
            if (lootID >= 101 && lootID <= 200) {
                img = new ImageIcon("src/image/Item/hat/hat" + (lootID - 100) + ".png").getImage();//帽子
            }
            if (lootID >= 201 && lootID < 300) {
                img = new ImageIcon("src/image/Item/equip/equip" + (lootID - 200) + ".png").getImage();//衣服
            }
            if (lootID >= 300 && lootID <= 400) {
                img = new ImageIcon("src/image/Item/arm/arm" + (lootID - 300) + ".png").getImage();//武器
            }

        }else if (mapId == 1){
            if (eventId == 1){
                img = new ImageIcon("src/image/Event/stop.png").getImage();
            }
            if (eventId == 2){
                img = new ImageIcon("src/image/Event/baoxiang.png").getImage();
            }
        }else if (mapId == 2){
            if (eventId == 1){
                img = new ImageIcon("src/image/Event/trader.png").getImage();
            }
            if(eventId==5)
            {
                img = new ImageIcon("src/image/Event/baoxiang.png").getImage();
            }
        }else if (mapId == 3){
            if (eventId == 0){
                img = new ImageIcon("src/image/Event/baoxiang.png").getImage();
            }
            if(eventId==1)
            {
                img = new ImageIcon("src/image/Event/portal"+frame+".png").getImage();
            }
        }else {
//            img = new ImageIcon("src/image/X.png").getImage();
        }
        return img;
    }

    public void getActorRect(Rectangle rect) {
        actorRect = rect;
    }

    public void getGameMapOrigin(int x, int y) {
        originX = x;
        originY = y;
    }

    //范围检测（a=0时，范围包括中心点a>=0，b>=1）
    public boolean RangeCheck(int a, int b) {
        Rectangle rectB = new Rectangle((originX-oldX)+x-b*32, (originY-oldY)+y-b*32, (2*b+1)*32, (2*b+1)*32);
        Rectangle rectA = new Rectangle((originX-oldX)+x-(a-1)*32, (originY-oldY)+y-(a-1)*32, (2*(a-1)+1)*32, (2*(a-1)+1)*32);
        if (a == 0) {
            if (rectB.intersects(actorRect)) {
                return true;
            }
        } else if (rectB.intersects(actorRect) && !rectA.intersects(actorRect)) {
            return true;
        }
        return false;
    }

    public Rectangle getRectangle() {
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, width, height);
    }

    public Rectangle getSmallRectangle(int a) {
        return new Rectangle((originX-oldX)+x+a, (originY-oldY)+y+a, width-a*2, height-a*2);
    }

}
