package Dao;

import javax.swing.*;
import java.awt.*;

public class Item {
    //基本信息
    protected int id;
    protected int type;//0：消耗品（UI，个数，菜单）；1：弹药（个数，菜单）；2：其他（菜单）
    protected int number;
    protected Image image = null;
    protected String name = null;
    protected String info = null;
    protected int[][] itemList = {
           /*0hp    1sp */
            {60,     0},//苹果
            {0,     60},//葡萄
            {100,    100},//黑面包
            {200,    0},//小红
            {0,    200},//小蓝
            {500,  500},//大蓝
    };
    //构造
    public Item(int id, int type, int number) {
        this.id = id;
        this.type = type;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void adjustNumber(int a) {
        number += a;
    }
    //获取数量
    public int getNumber() {
        return number;
    }

    //获取图像
    public Image getImage() {
        image = (new ImageIcon("src/image/Item/prop/item_"+(id+1)+".png")).getImage();
        return image;
    }

    //获取名字
    public String getName() {
        switch (id) {
            case 0:
                name = "香蕉";
                break;
            case 1:
                name = "咖啡";
                break;
            case 2:
                name = "烤肉";
                break;
            case 3:
                name = "可乐";
                break;
            case 4:
                name = "葡萄糖口服液";
                break;
            case 5:
                name = "滴眼液";
                break;
        }
        return name;
    }

    //获取介绍
    public String getInfo() {
        switch (id) {
            case 0:
                info = "大学生超市的香蕉，很甜.";
                break;
            case 1:
                info = "北餐厅的瑞幸咖啡，可以提神醒脑";
                break;
            case 2:
                info = "南餐三楼的烤肉，太好吃啦";
                break;
            case 3:
                info = "一瓶可乐";
                break;
            case 4:
                info = "葡萄糖口服溶液，让人重新焕发活力";
                break;
            case 5:
                info = "滴眼液，让人全方面的恢复";
                break;
        }
        return info;
    }
    //获取介绍
    public String getEffect() {
        switch (id) {
            case 0:
                info = "恢复30HP 短时间恢复HP";
                break;
            case 1:
                info = "恢复30SP 短时间恢复SP";
                break;
            case 2:
                info = "恢复50HP 50SP 短时间恢复HP SP";
                break;
            case 3:
                info = "恢复100HP";
                break;
            case 4:
                info = "恢复100SP";
                break;
            case 5:
                info = "恢复500HP 500SP";
                break;
        }
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }
}
