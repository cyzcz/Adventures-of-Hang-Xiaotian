package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EndFrame extends JFrame {
    private Image img = null; // 声明图像对象
    private CaptionSpecificPanel captionSpecificPanel = null; // 声明图像面板对象
    private int frame=0;
    public static void main(String args[]) {

        EndFrame frame = new EndFrame();
        frame.setVisible(true);
    }

    public EndFrame() {
        super();
        URL imgUrl = EndFrame.class.getResource("/image/UI/black.png");// 获取图片资源的路径
        img = Toolkit.getDefaultToolkit().getImage(imgUrl); // 获取图像资源
        captionSpecificPanel = new CaptionSpecificPanel(); // 创建图像面板对象
        this.setBounds(200, 160, 1280, 720); // 设置窗体大小和位置
        this.add(captionSpecificPanel); // 在窗体上添加图像面板对象
        Thread thread = new Thread(captionSpecificPanel);// 创建线程对象
        thread.start();// 启动线程对象
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体关闭模式
        this.setTitle("恭喜通关"); // 设置窗体标题
        System.out.println();
    }

    // 创建面板类
    class CaptionSpecificPanel extends JPanel implements Runnable {
        int[] x ={450,410,480,0,500,310,490,400,450,0,580,410,460,410,460};// 存储绘制点的x坐标
        int[] y = {540,540,540,540,540,540,540,540,540,540,540,540,540,540,540};// 存储绘制点的y坐标
        String[] value ={"航小天在学校闯过了重重困难",
                "顺利的从西北工业大学软件学院毕业",
                "成为了一个了不起的人",
                "",
                "《航小天历险记》",
                "制作人：赵晨卓 马婉荣 钟宜芮 马晓楠 张潇元 孙晟昊",
                "UI：钟宜芮 张潇元",
                "游戏主要编写：赵晨卓 马婉荣 钟宜芮",
                "游戏设计：马晓楠 孙晟昊",
                "",
                "鸣谢：",
                "感谢《航小天历险记》开发组的付出",
                "感谢软件学院老师们的指导",
                "感谢西北工业大学与软件学院的栽培",
                "感谢所有支持本游戏的人"
        };// 存储绘制的内容
        public void paint(Graphics g) {
            System.out.println(y.length);
            g.clearRect(0, 0, 1280, 720);// 清除绘图上下文的内容
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);// 绘制图像
            Font font = new Font("微软雅黑", Font.BOLD, 26);// 创建字体对象
            g.setFont(font);// 指定字体
            g.setColor(Color.white);// 指定颜色
            for(int i=0;i<value.length;i++) {
                if(frame>60*i && y[i]>150) {
                    y[i]--;
                    g.drawString(value[i], x[i], y[i]);// 绘制文本
                }
                if(frame==1230) System.exit(0);
            }
        }
        public void run() {
            try {
                while (true) { // 读取内容
                    Thread.sleep(20); // 当前线程休眠1秒
                    frame++;
                    repaint();// 调用paint()方法
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
