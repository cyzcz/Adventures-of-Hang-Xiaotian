package Dao;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Actor extends Thread{
    //角色线程开关
    protected boolean actorLined = true;
    //角色初始位置
    protected int x = 32 * 20;
    protected int y = 32 * 21;
    protected int dir = 2;//朝向
    protected int[] state = {0,0,0,0};//运动状态(0:静止 1:行走)
    //角色速度
    protected int speed = 4;
    protected int oldSpeed = 4;
    //角色素材信息
    protected int width = 32;
    protected int height = 32;
    protected Image img = (new ImageIcon("src/image/Characters/actor_d.png")).getImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setState()
    {
        state[0]=0;
        state[1]=0;
        state[2]=0;
        state[3]=0;
    }
}
