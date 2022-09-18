package Frame;

import Dao.Record;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HelpFrame extends JPanel implements ActionListener {
        private int count = 0;
        private boolean step;
        private JFrame jf;
        private final JButton helpButton;
        private final JButton saveButton;
        private final JButton backButton;
        //private JLabel tip;
        private Image help = new ImageIcon("src/image/Help/help.png").getImage();
        private Image icon = new ImageIcon("src/image/Item/arm/arm5.png").getImage();

        /**
         * 这个函数是启动窗口
         */
        public HelpFrame() {
            this.setSize(640, 365);
            jf = new JFrame("帮助");
//            tip= new JLabel("您确定要退出吗？");
//            tip.setFont(new Font("微软雅黑", Font.BOLD, 24));
//            tip.setForeground(Color.white);
//            tip.setBackground(Color.black);
            helpButton = new JButton("帮助信息");
            helpButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
            helpButton.setForeground(Color.white);
            helpButton.setContentAreaFilled(false);//消除背景
            helpButton.setFocusPainted(false);//消除选择
            helpButton.setBounds(255, 40, 130, 30);
            saveButton = new JButton("保存");
            saveButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
            saveButton.setForeground(Color.white);
            saveButton.setContentAreaFilled(false);//消除背景
            saveButton.setFocusPainted(false);//消除选择
            saveButton.setBounds(255, 100, 130, 30);
            backButton = new JButton("取消");
            backButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
            backButton.setForeground(Color.white);
            backButton.setContentAreaFilled(false);//消除背景
            backButton.setFocusPainted(false);//消除选择
            backButton.setBounds(255, 160, 130, 30);
            jf.setSize(640, 365);
            jf.setIconImage(icon);
            jf.setLocationRelativeTo(null);
            jf.setResizable(false);
            jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jf.add(helpButton);
            jf.add(saveButton);
            jf.add(backButton);
           //jf.getContentPane().add(tip);
            helpButton.addActionListener(this);
            saveButton.addActionListener(this);
            backButton.addActionListener(this);
            jf.add(this);
            jf.setVisible(true);
            //窗口线程
            this.repaint();
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(help,0,0,null);
            helpButton.repaint();
            saveButton.repaint();
            backButton.repaint();
            //tip.repaint();

        }
        //按下按钮的时候都会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            //进入游戏
            if (e.getSource() == helpButton) {
                JOptionPane.showMessageDialog(this, "W,A,S,D：上下左右移动\nC：发射子弹\nD:查看属性\nZ：切换武器\nA：切换道具\nS:使用道具", "游戏帮助",JOptionPane.WARNING_MESSAGE);
            }
            if (e.getSource() == saveButton) {
                Record.setSave(true);
                e.setSource(backButton);
            }
            if (e.getSource() == backButton) {
                jf.dispose();
            }
        }

    }





