package Service;

import Dao.Actor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Frame.*;
import Dao.*;
import View.*;
//角色移动类和事件页
public class ActorController extends Actor {
    //传递对象
    private GameFrame gf;
    public ActorController(GameFrame gf) {
        this.gf = gf;
    }
    private ArrayList<EffectController> effects = new ArrayList<>();
    public ArrayList<EffectController> getEffects() {
        return effects;
    }
    //角色的移动及地图卷动
    @Override
    public void run() {
        while (true) {
            if (actorLined){
                checkImage();
                if (state[0] == 1) {
                    if (getActorhitenemy()) {
                        if ((this.hit(1))) {
                            this.speed = 0;
                        }
                    } else if ((this.hit(1) || this.hitenemy(1))) {
                        this.speed = 0;
                    }
                    if (this.y >= 720 / 2 - 180) {
                        this.y -= this.speed;
                    }
                    if (this.y <= 720 / 2 - 180) {
                        if (gf.getGameMap().y != 0) {
                            gf.getGameMap().y += this.speed;
                            gf.getGameMap().bg.y += this.speed;
                        }
                        if (gf.getGameMap().y == 0) {
                            if (this.y != 0) {
                                this.y -= this.speed;
                            }
                        }
                    }
                    this.speed = oldSpeed;
                }

                if (state[1] == 1) {
                    if (getActorhitenemy()) {
                        if ((this.hit(2))) {
                            this.speed = 0;
                        }
                    } else if ((this.hit(2) || this.hitenemy(2))) {
                        this.speed = 0;
                    }
                    if (this.y <= 720 / 2 + 180 - 32) {
                        this.y += this.speed;
                    }
                    if (this.y >= 720 / 2 + 180 - 32) {
                        if (gf.getGameMap().y >= -(gf.getGameMap().airWall.length * 32 - 720)) {
                            gf.getGameMap().y -= this.speed;
                            gf.getGameMap().bg.y -= this.speed;
                        }
                        if (gf.getGameMap().y <= -(gf.getGameMap().airWall.length * 32 - 720)) {
                            if (this.y <= 720 - 32*2) {
                                this.y += this.speed;
                            }
                        }
                    }
                    this.speed = oldSpeed;
                }

                if (state[2] == 1) {
                    //与空气墙碰撞检测
                    if (getActorhitenemy()) {
                        if ((this.hit(dir))) {
                            //人物不移动
                            this.speed = 0;
                        }
                    } else if ((this.hit(3) || this.hitenemy(3))) {
                        //人物不移动
                        this.speed = 0;
                    }

                    //在安全边距内移动
                    if (this.x >= 1280 / 2 - 320) {
                        //人物移动
                        this.x -= this.speed;
                    }
                    //脱离安全边距移动时
                    if (this.x <= 1280 / 2 - 320) {
                        //地图可以卷动时
                        if (gf.getGameMap().x != 0) {
                            //地图卷动
                            gf.getGameMap().x += this.speed;
                            gf.getGameMap().bg.x += this.speed;
                        }
                        //地图无法卷动时
                        if (gf.getGameMap().x == 0) {
                            //人物未达到地图边缘时
                            if (this.x != 0) {
                                //人物移动
                                this.x -= this.speed;
                            }
                        }
                    }
                    //重置角色移动速度
                    this.speed = oldSpeed;
                }

                if (state[3] == 1) {
                    if (getActorhitenemy()) {
                        if ((this.hit(4))) {
                            this.speed = 0;
                        }
                    } else if ((this.hit(4) || this.hitenemy(4))) {
                        this.speed = 0;
                    }

                    if (this.x <= 1280 / 2 + 320 - 32) {
                        this.x += this.speed;
                    }
                    if (this.x >= 1280 / 2 + 320 - 32) {
                        if (gf.getGameMap().x != -(gf.getGameMap().airWall[0].length * 32 - 1280)) {
                            gf.getGameMap().x -= this.speed;
                            gf.getGameMap().bg.x -= this.speed;
                        }
                        if (gf.getGameMap().x == -(gf.getGameMap().airWall[0].length * 32 - 1280)) {
                            if (this.x != 1280 - 32) {
                                this.x += this.speed;
                            }
                        }
                    }
                    this.speed = oldSpeed;
                }

            }

            try {
                if (gf.isTest()){
                    speed = 32;
                    oldSpeed = 32;
                    sleep(100);
                }else {
                    speed = 4;
                    oldSpeed = 4;
                    sleep(15);
                }
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }
    }

    //获取基本信息
    public boolean isActorLined() {
        return actorLined;
    }

    public void setActorLined(boolean actorLined) {
        this.actorLined = actorLined;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDir(int dir){
        this.dir = dir;
    }

    public void setState(int state,int a) {
        if (state == 1){
            this.state[0] = a;
        }
        if (state == 2){
            this.state[1] = a;
        }
        if (state == 3){
            this.state[2] = a;
        }
        if (state == 4){
            this.state[3] = a;
        }
    }

    public Image getImg() {
        return img;
    }

    /**
     * 动态图像
     *
     */
    private void checkImage(){
        //朝向修正
        int s = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i]>0){
                s++;
            }
        }
        if (s == 1){
            for (int i = 0; i < state.length; i++) {
                if (state[i] == 1){
                    dir = i+1;
                }
            }
        }
        //图像修正
        if (checkRun()){
            if (dir == 1){
                img = (new ImageIcon("src/image/Characters/actor_u.gif")).getImage();
            }
            if (dir == 2){
                img = (new ImageIcon("src/image/Characters/actor_d.gif")).getImage();
            }
            if (dir == 3){
                img = (new ImageIcon("src/image/Characters/actor_l.gif")).getImage();
            }
            if (dir == 4){
                img = (new ImageIcon("src/image/Characters/actor_r.gif")).getImage();
            }
        }else {
            if (dir == 1){
                img = (new ImageIcon("src/image/Characters/actor_u.png")).getImage();
            }
            if (dir == 2){
                img = (new ImageIcon("src/image/Characters/actor_d.png")).getImage();
            }
            if (dir == 3){
                img = (new ImageIcon("src/image/Characters/actor_l.png")).getImage();
            }
            if (dir == 4){
                img = (new ImageIcon("src/image/Characters/actor_r.png")).getImage();
            }
        }
    }

    /**
     * 检查是否行走
     *
     * @return
     */
    private boolean checkRun(){
        if (state[0] == 1||state[1] == 1||state[2] == 1||state[3] == 1){
            return true;
        }return false;
    }

    /**
     * 调整速度
     *
     * @param speed
     */
    public void adjustSpeed(int speed){
        this.speed += speed;
        oldSpeed = speed;
    }

    /**
     * 角色立定
     *
     */
    public void actorStop() {
        if (dir == 1) this.img = (new ImageIcon("src/image/Characters/actor_u.png")).getImage();
        if (dir == 2) this.img = (new ImageIcon("src/image/Characters/actor_d.png")).getImage();
        if (dir == 3) this.img = (new ImageIcon("src/image/Characters/actor_l.png")).getImage();
        if (dir == 4) this.img = (new ImageIcon("src/image/Characters/actor_r.png")).getImage();
    }

    /**获取角色矩形
     *
     * @return
     */
    public Rectangle getActorRect() {
        Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
        return myrect;
    }

    /**
     * 获取角色矩形(方向)
     *
     * @param dir
     * @return
     */
    public Rectangle getActorRect(int dir) {
        Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
        if (dir == 3) {
            return myrect = new Rectangle(this.x - speed, this.y, this.width, this.height);
        } else if (dir == 4) {
            return myrect = new Rectangle(this.x + speed, this.y, this.width, this.height);
        } else if (dir == 1) {
            return myrect = new Rectangle(this.x, this.y - speed, this.width, this.height);
        } else if (dir == 2) {
            return myrect = new Rectangle(this.x, this.y + speed, this.width, this.height);
        }
        return myrect;
    }

    /**
     * 角色与空气墙的碰撞检测
     *
     * @param dir
     * @return
     */
    private boolean hit(int dir) {
        //test////////////////////////////////
        if (gf.isTest()){
            return false;
        }
        int check = 0;
        Rectangle wallrect = null;
        Rectangle waterrect = null;
        Rectangle eventrect = null;
        int wallsize = gf.getGameMap().wallList.size();
        int watersize = gf.getGameMap().waterList.size();
        int eventsize = gf.getGameMap().eventList.size();
        for (int i = 0; i < wallsize; ++i) {
            Square wall = gf.getGameMap().wallList.get(i);
            wallrect = new Rectangle(gf.getGameMap().x + wall.getX(), gf.getGameMap().y + wall.getY(), wall.getWidth(), wall.getHeight());
            //判断角色矩形与空气墙矩形是否交叉
            if (getActorRect(dir).intersects(wallrect)) {
                check++;
            }
        }
        for (int j = 0; j < watersize; ++j) {
            Square water = gf.getGameMap().waterList.get(j);
            waterrect = new Rectangle(gf.getGameMap().x + water.getX(), gf.getGameMap().y + water.getY(), water.getWidth(), water.getHeight());
            if (getActorRect(dir).intersects(waterrect)) {
                check++;
            }
        }
        for (int j = 0; j < eventsize; ++j) {
            EventController event = gf.getGameMap().eventList.get(j);
            if (!event.isLive()) break;
            if (event.isLoot()) break;
            if (getActorRect(dir).intersects(event.getSmallRectangle(speed))) {
                check++;
            }
        }
        if (check > 0) {
            return true;
        }
        return false;
    }

    /**
     * 碰撞怪物
     * @param dir
     * @return
     */
    private boolean hitenemy(int dir) {
        int enemysize = gf.getGameMap().enemyList.size();
        for (int j = 0; j < enemysize; ++j) {
            EnemyController enemy = gf.getGameMap().enemyList.get(j);
            if (getActorRect(dir).intersects(gf.getGameMap().enemyList.get(j).getRectangle())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取角色是否卡怪
     */
    private boolean getActorhitenemy() {
        Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
        int enemysize = gf.getGameMap().enemyList.size();
        for (int j = 0; j < enemysize; ++j) {
            EnemyController enemy = gf.getGameMap().enemyList.get(j);
            if (myrect.intersects(gf.getGameMap().enemyList.get(j).getRectangle())) {
                return true;
            }
        }
        return false;
    }

    /**于怪物上显示特效
     *
     * @param efid
     */
    public void addEffect(int efid) {
        effects.add(new EffectController(this, efid));
    }

    /**于怪物上绘制特效
     *
     * @param g
     */
    public void drawEffect(Graphics g) {
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(g,this);
        }
    }

    /**攻击
     *
     */
    public void ATK() {
        if (!gf.actorData.arms.isEmpty()&& gf.actorData.checkATKCD(0)&&gf.actorData.getActorSP()-gf.actorData.arms.get(0).spCost()>0) {
            BulletController bullet = new BulletController(dir, x, y,gf.getGameMap().x,gf.getGameMap().y, gf.actorData.getBullet(0),gf.actorData.getAtk());
            bullet.gameMapPosition(gf.getGameMap().x,gf.getGameMap().y);
            if (dir == 1){
                bullet.setY(y-32);
                gf.getGameMap().bullets.add(bullet);
            }
            if (dir == 2){
                bullet.setY(y+32);
                gf.getGameMap().bullets.add(bullet);
            }
            if (dir == 3){
                bullet.setX(x-32);
                gf.getGameMap().bullets.add(bullet);
            }
            if (dir == 4){
                bullet.setX(x+32);
                gf.getGameMap().bullets.add(bullet);
            }
            if (gf.actorData.getBullet(0) == 3 || gf.actorData.getBullet(0) == 4){
                BulletController bullet1 = new BulletController(dir, x, y, gf.getGameMap().x,gf.getGameMap().y, gf.actorData.getBullet(0),gf.actorData.getAtk());
                bullet1.gameMapPosition(gf.getGameMap().x,gf.getGameMap().y);
                bullet1.setBulletDir(dir*10+1);
                gf.getGameMap().bullets.add(bullet1);
                BulletController bullet2 = new BulletController(dir, x, y, gf.getGameMap().x,gf.getGameMap().y, gf.actorData.getBullet(0),gf.actorData.getAtk());
                bullet2.gameMapPosition(gf.getGameMap().x,gf.getGameMap().y);
                bullet2.setBulletDir(dir*10+2);
                gf.getGameMap().bullets.add(bullet2);
            }
            gf.actorData.spControl(-gf.actorData.arms.get(0).spCost());
            gf.actorData.setATKCD(0);
        }
    }

}
