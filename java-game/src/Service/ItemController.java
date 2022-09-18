package Service;

import Service.DataController;

import javax.swing.*;
import java.awt.*;
import Frame.*;
import Dao.*;
import View.*;
public class ItemController extends Item{
    //基本信息

    ItemController(int id, int type, int number) {
        super(id, type, number);
    }

    public void useEffect(int i, DataController data) {
        data.hpControl(itemList[i][0]);
        data.spControl(itemList[i][1]);
        switch (id){
            case 0:{
                data.adjustStatus(0,1);
                data.adjustStatusTime(0,100);
            }
            break;
            case 1:{
                data.adjustStatus(1,1);
                data.adjustStatusTime(1,100);
            }
                break;
            case 2:{
                data.adjustStatus(0,1);
                data.adjustStatus(1,1);
                data.adjustStatusTime(0,100);
                data.adjustStatusTime(1,100);
            }break;
        }
    }

}
