package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JPanel implements ActionListener {
    private int count = 0;
    private boolean step;
    private JFrame jf;
    private JButton play, exit, reload;
    private Pic hxt1 = new Pic(200, 20, (new ImageIcon("src/image/Title/hxt1.png")).getImage());
    private Pic hxt2 = new Pic(500, 20, (new ImageIcon("src/image/Title/hxt1.png")).getImage());
    private Image icon = new ImageIcon("src/image/Item/arm/arm5.png").getImage();

    /**
     * 这个函数是启动窗口
     */
    public StartFrame() {
        //this.setSize(640, 480);
        jf = new JFrame("航小天历险记");
        play = new JButton("Start");
        play.setFont(new Font("微软雅黑", Font.BOLD, 26));
        play.setForeground(Color.black);
        //play.setContentAreaFilled(false);//消除背景
        play.setFocusPainted(false);//消除选择
        play.setBounds(300, 270, 170, 45);
        reload = new JButton("Reload");
        reload.setFont(new Font("微软雅黑", Font.BOLD, 26));
        reload.setForeground(Color.black);
      //  reload.setContentAreaFilled(false);//消除背景
        reload.setFocusPainted(false);//消除选择
        reload.setBounds(300, 340, 170, 45);
        exit = new JButton("Exit");
        exit.setFont(new Font("微软雅黑", Font.BOLD, 26));
        exit.setForeground(Color.black);
       // exit.setContentAreaFilled(false);//消除背景
        exit.setFocusPainted(false);//消除选择
        exit.setBounds(300, 410, 170, 45);
        jf.setSize(815, 540);
        jf.setIconImage(icon);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(play);
        jf.add(reload);
        jf.add(exit);
        play.addActionListener(this);
        exit.addActionListener(this);
        reload.addActionListener(this);
        jf.add(this);
        jf.setVisible(true);
        //窗口线程
        //使用多线程的方式让开始界面动起来
        Runnable startThread = () -> {
            while (true) {
                //count决定了界面标志的移动情况，保证其上下移动
                if (count < 50) {
                    count++;
                    step = true;
                } else if (count >= 50) {
                    count++;
                    step = false;
                }
                if (count == 100) {
                    count = 0;
                }
                //刷新页面，使其移动
                //repaint调用了paint方法
                this.repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread startline = new Thread(startThread);
        startline.start();
    }

    public void paint(Graphics g) {
        g.drawImage((new ImageIcon("src/image/Title/title_back.png")).getImage(), 0, 0, this);
        hxt1.draw(g);
        hxt2.draw(g);
        //界面标志的上下移动
        if (step) {
            hxt1.x += 0.5;
            hxt2.x -= 1;
        }
        if (!step) {
            hxt1.x -= 0.5;
            hxt2.x += 1;
        }
        g.drawImage((new ImageIcon("src/image/Title/title_light.png")).getImage(), -50, 57, this);
        g.drawImage((new ImageIcon("src/image/Title/hxt_title.png")).getImage(), 100, 75, this);
        play.repaint();
        exit.repaint();
        reload.repaint();
    }
    //按下按钮的时候都会调用此方法
    @Override
    public void actionPerformed(ActionEvent e) {
        //进入游戏
        if (e.getSource() == play || e.getSource()== reload) {
            jf.dispose();
            //新建游戏窗口
            GameFrame gf = null;
            try {
                gf = new GameFrame(e.getSource()== reload);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //新建按键监听器
            KeyListener kl = new KeyListener(gf);
            //按键监听器向游戏窗口注册
            gf.addKeyListener(kl);
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public class Pic {
        float x, y;
        Image img = null;

        Pic(float x, float y, Image img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        public void draw(Graphics g) {
            g.drawImage(img, (int) x, (int) y, null);
        }
    }
}
