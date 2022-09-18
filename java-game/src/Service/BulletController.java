package Service;

import javax.swing.*;
import java.awt.*;

import Dao.Event;
import Frame.*;
import Dao.*;
import View.*;
public class BulletController extends Bullet{
    private ActorController actor;
    private EnemyController enemy;
                                         /*9帧数  10攻击类型（0：单体 1：穿透 2：全屏）*/
    //平直（通常）
    public BulletController(int dir, int x, int y, int oldx, int oldy, int id, float atk) {
        super();
        bulletDir = dir;
        this.x = x;
        this.y = y;
        this.oldX = oldx;
        this.oldY = oldy;
        bulletID = id;
        this.atk = atk;
    }

    //近身攻击（怪物用）
    public BulletController(int dir, EnemyController enemy, int oldx, int oldy, int id, float atk) {
        super();
        bulletDir = dir;
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.oldX = oldx;
        this.oldY = oldy;
        bulletID = id;
        this.atk = atk;
        this.enemy = enemy;
        origin = 1;
    }

    //角度（怪物用）
    public BulletController(double a, double b, int x, int y, int id, float atk) {
        super();
        this.x = x;
        this.y = y;
        bulletID = id;
        this.atk = atk;
        this.angleX = a;
        this.angleY = b;
    }

    //跟踪（怪物用）
    public BulletController(int x, int y, int id, float atk) {
        super();
        this.x = x;
        this.y = y;
        bulletID = id;
        this.atk = atk;
        bulletDir = 100;
    }

    public void move() {
        //固定角度（跟踪）
        if (bulletDir == 0){
            x += angleX*getSpeed();
            y += angleY*getSpeed();
        }
        //动态角度（跟踪）
        if (bulletDir == 100){
            double a = Math.abs(x+originX - actor.getX());
            double b = Math.abs(y+originY - actor.getY());
            double c = Math.sqrt(a*a + b*b);
            if (x+originX > actor.getX()&&y+originY <= actor.getY()){
                x -= a/c*getSpeed();
                y += b/c*getSpeed();
            }
            if (x+originX >= actor.getX()&&y+originY > actor.getY()){
                x -= a/c*getSpeed();
                y -= b/c*getSpeed();
            }
            if (x+originX < actor.getX()&&y+originY >= actor.getY()){
                x += a/c*getSpeed();
                y -= b/c*getSpeed();
            }
            if (x+originX <= actor.getX()&&y+originY < actor.getY()){
                x += a/c*getSpeed();
                y += b/c*getSpeed();
            }
        }
        //平直
        if (bulletDir == 1){
            y -= getSpeed();
        }
        if (bulletDir == 2){
            y += getSpeed();
        }
        if (bulletDir == 3){
            x -= getSpeed();
        }
        if (bulletDir == 4){
            x += getSpeed();
        }
        //固定角度
        if (bulletDir >= 10){
            if (bulletDir/10 == 1){
                y -= getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    x += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    x -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 2){
                y += getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    x += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    x -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 3){
                x -= getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    y += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    y -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 4){
                x += getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    y += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    y -= (float)getSpeed()*Math.sin(0.5);
                }
            }
        }
    }
    public void actorPosition(ActorController actor){
        this.actor = actor;
    }
    public void draw(int id, Graphics g) {
        bulletID = id;
        tm = getTime();
        if (death) return;
        if (steps == tm) {
            steps = 0;  //步骤时间归0
            setDeath(true);  //子弹死亡
        }
        move();
        if (getBulletWidth() == 0) {
            g.drawImage(getImg(), 1280 / 2 - getWidth() / 2, 720 / 2 - getHeight() / 2, null);
        } else g.drawImage(getImg(), (originX-oldX)+x - getWidth() / 2 + 16, (originY-oldY)+y - getHeight() / 2 + 16, null);
        steps++;
        if (steps%6 == 0) {
            step++;
            if (step == getFrame()) {
                step = 1;
            }
        }
    }
}

