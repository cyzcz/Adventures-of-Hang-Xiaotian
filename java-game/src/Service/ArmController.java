package Service;

import javax.swing.*;
import java.awt.*;
import Frame.*;
import Dao.*;
import View.*;
public class ArmController extends Arm{

    public ArmController(int id) {
        this.id = id;
        cd = 0;
        cdLimit = getCdLimit();
    }
}    //基本信息

