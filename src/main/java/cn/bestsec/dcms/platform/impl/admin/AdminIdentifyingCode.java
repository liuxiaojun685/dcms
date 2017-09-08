package cn.bestsec.dcms.platform.impl.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_IdentifyingCodeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Admin_IdentifyingCodeRequest;
import cn.bestsec.dcms.platform.api.model.Admin_IdentifyingCodeResponse;

/**
 * 生成动态验证码
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月5日 下午4:10:30
 */
@Component
public class AdminIdentifyingCode implements Admin_IdentifyingCodeApi {

    private static final int width = 70;
    private static final int height = 25;

    @Override
    public Admin_IdentifyingCodeResponse execute(Admin_IdentifyingCodeRequest admin_IdentifyingCodeRequest)
            throws ApiException {

        char[] codeSequence = { 'a', 'c', 'd', 'e', 'f', 'j', 'k', 'm', 'p', 'r', 's', 't', 'w', 'x', 'y', 'A', 'C',
                'D', 'E', 'F', 'J', 'K', 'M', 'P', 'R', 'S', 'T', 'W', 'X', 'Z', '3', '4', '5', '6', '7', '8' };
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        // 设置背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width - 1, height - 1);
        // 设置边框
        g.setColor(Color.blue);
        g.drawRect(0, 0, width, height);
        // 画干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i < 4; i++) {
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
            red = rd.nextInt(100);
            green = rd.nextInt(100);
            blue = rd.nextInt(100);
            g.setColor(new Color(red, green, blue));
            g.drawString(cha, x, 20);
            sb.append(cha);
            x += 15;
        }
        // 将sb存储到session
        HttpServletRequest request = admin_IdentifyingCodeRequest.httpServletRequest();
        Admin_IdentifyingCodeResponse resp = new Admin_IdentifyingCodeResponse();
        HttpSession session = request.getSession();
        session.setAttribute("codes", sb);
        resp.setImage(image);

        return resp;
    }

}
