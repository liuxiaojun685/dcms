package cn.bestsec.dcms.platform.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成动态验证码
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月5日 下午4:10:30
 */
@WebServlet("/IdentifyingCode")
public class IdentifyingCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int width = 70;
    private static final int height = 25;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdentifyingCode() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        // 设置背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 设置边框
        g.setColor(Color.blue);
        g.drawRect(0, 0, width - 1, height - 1);
        // 画干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i <= 9; i++) {
            int xStart = new Random().nextInt(width);
            int yStart = new Random().nextInt(height);
            int xEnd = new Random().nextInt(width);
            int yEnd = new Random().nextInt(height);
            g.drawLine(xStart, yStart, xEnd, yEnd);
        }
        // 写随机数
        int red = 0, green = 0, blue = 0;
        g.setFont(new Font("微软雅黑", Font.BOLD + Font.ITALIC, 20));
        int x = 5;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            Random rd = new Random();
            String cha = String.valueOf(codeSequence[rd.nextInt(36)]);
            red = rd.nextInt(255);
            green = rd.nextInt(255);
            blue = rd.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(cha, x, 20);
            sb.append(cha);
            x += 15;
        }
        // 将sb存储到session
        HttpSession session = request.getSession();
        session.setAttribute("codes", sb);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

}
