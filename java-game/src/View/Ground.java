package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import Frame.*;
import Dao.*;
import View.*;
public class Ground {
    public int x = 0;
    public int y = 0;
    private int bgid = 0;
    private Image img = null;

    public void draw(int id, Graphics g, boolean ifGif,boolean ifDialog) {
        if (!ifGif &&!ifDialog) {
            bgid = id;
            img = (new ImageIcon("src/image/Ground/ground" + bgid + ".png")).getImage();
            g.drawImage(img, x, y, null);
        }
        if(ifGif){
            bgid = id + 1;
            //img=(new ImageIcon("src/image/Gif/"+gid+"/ (" + step + ").png")).getImage();
            img = (new ImageIcon("src/image/Gif/" + bgid + ".gif")).getImage();
            g.drawImage(img, x, y, null);
        }
        if(ifDialog)
        {
            bgid=id;
            img = (new ImageIcon("src/image/Back/" + bgid + ".png")).getImage();
            if(bgid==1) g.drawImage(img, x, y+300, null);
            if(bgid==2) g.drawImage(img, x, y, null);
            if(bgid==3) g.drawImage(img, x, y, null);
        }
    }
}
