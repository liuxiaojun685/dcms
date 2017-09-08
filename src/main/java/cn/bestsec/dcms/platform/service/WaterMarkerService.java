package cn.bestsec.dcms.platform.service;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;

import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.watermarker.IWaterMarkApi;
import cn.bestsec.dcms.platform.utils.watermarker.WatermarkerEntity;

@Service
public class WaterMarkerService {
    public void genWatermarkerConfig4Print(WatermarkerEntity watermarkerEntity) {
        IWaterMarkApi.instanceDll.writeToFileHead(SystemProperties.getInstance().getCacheDir() + "/config4Print.dat",
                new NativeLong(watermarkerEntity.getLeft()), //
                new NativeLong(watermarkerEntity.getTop()), //
                new NativeLong(watermarkerEntity.getRight()), //
                new NativeLong(watermarkerEntity.getBottom()), //
                new NativeLong(watermarkerEntity.getFontSize()), //
                (byte) watermarkerEntity.getColorA(), //
                (byte) Integer.parseInt(watermarkerEntity.getColorR(), 16), //
                (byte) Integer.parseInt(watermarkerEntity.getColorG(), 16), //
                (byte) Integer.parseInt(watermarkerEntity.getColorB(), 16), //
                new NativeLong(watermarkerEntity.getFontEscapement()), //
                watermarkerEntity.getFontTilt(), watermarkerEntity.getMarkLocal(), watermarkerEntity.getMarkLayout(),
                watermarkerEntity.getMarkRows(), watermarkerEntity.getMarkCols(), watermarkerEntity.getMarkTop(),
                watermarkerEntity.getMarkLeft(), watermarkerEntity.getMarkRight(), watermarkerEntity.getMarkBottom(),
                watermarkerEntity.getMarkQuality(), watermarkerEntity.getMarkType(),
                (int) watermarkerEntity.getPrintTime(), watermarkerEntity.getCompNameType(),
                watermarkerEntity.getUserNameType(), watermarkerEntity.getHostAddrType(),
                watermarkerEntity.getCustInfoType(), watermarkerEntity.getPrintTimeType(),
                new WString(watermarkerEntity.getFontName()), //
                new WString(watermarkerEntity.getText()), //
                new WString(watermarkerEntity.getPictPath()), //
                new WString(watermarkerEntity.getCompName()), //
                new WString(watermarkerEntity.getUserName()), //
                new WString(watermarkerEntity.getHostAddr()), //
                new WString(watermarkerEntity.getCustInfo())//
        );
    }

    public void genWatermarkerConfig4Screen(WatermarkerEntity watermarkerEntity) {
        IWaterMarkApi.instanceDll.writeToFileHead(SystemProperties.getInstance().getCacheDir() + "/config4Screen.dat",
                new NativeLong(watermarkerEntity.getLeft()), //
                new NativeLong(watermarkerEntity.getTop()), //
                new NativeLong(watermarkerEntity.getRight()), //
                new NativeLong(watermarkerEntity.getBottom()), //
                new NativeLong(watermarkerEntity.getFontSize()), //
                (byte) watermarkerEntity.getColorA(), //
                (byte) Integer.parseInt(watermarkerEntity.getColorR(), 16), //
                (byte) Integer.parseInt(watermarkerEntity.getColorG(), 16), //
                (byte) Integer.parseInt(watermarkerEntity.getColorB(), 16), //
                new NativeLong(watermarkerEntity.getFontEscapement()), //
                watermarkerEntity.getFontTilt(), watermarkerEntity.getMarkLocal(), watermarkerEntity.getMarkLayout(),
                watermarkerEntity.getMarkRows(), watermarkerEntity.getMarkCols(), watermarkerEntity.getMarkTop(),
                watermarkerEntity.getMarkLeft(), watermarkerEntity.getMarkRight(), watermarkerEntity.getMarkBottom(),
                watermarkerEntity.getMarkQuality(), watermarkerEntity.getMarkType(),
                (int) watermarkerEntity.getPrintTime(), watermarkerEntity.getCompNameType(),
                watermarkerEntity.getUserNameType(), watermarkerEntity.getHostAddrType(),
                watermarkerEntity.getCustInfoType(), watermarkerEntity.getPrintTimeType(),
                new WString(watermarkerEntity.getFontName()), //
                new WString(watermarkerEntity.getText()), //
                new WString(watermarkerEntity.getPictPath()), //
                new WString(watermarkerEntity.getCompName()), //
                new WString(watermarkerEntity.getUserName()), //
                new WString(watermarkerEntity.getHostAddr()), //
                new WString(watermarkerEntity.getCustInfo())//
        );
    }

    public String initConfigWhileQuery() {
        WatermarkerEntity watermarkerEntity = new WatermarkerEntity();

        watermarkerEntity.setLeft(1);
        watermarkerEntity.setTop(1);
        watermarkerEntity.setRight(1);
        watermarkerEntity.setBottom(1);
        watermarkerEntity.setFontSize(22);
        watermarkerEntity.setColorA(25);
        watermarkerEntity.setColorR("55");
        watermarkerEntity.setColorG("55");
        watermarkerEntity.setColorB("55");
        watermarkerEntity.setFontEscapement(-45);
        watermarkerEntity.setMarkLocal(0);
        watermarkerEntity.setMarkRows(8);
        watermarkerEntity.setMarkCols(8);
        watermarkerEntity.setMarkTop(1);
        watermarkerEntity.setMarkLeft(1);
        watermarkerEntity.setMarkRight(1);
        watermarkerEntity.setMarkBottom(1);
        watermarkerEntity.setPrintTime(4294967295L);
        watermarkerEntity.setCompNameType(0);
        watermarkerEntity.setUserNameType(0);
        watermarkerEntity.setHostAddrType(0);
        watermarkerEntity.setCustInfoType(0);
        watermarkerEntity.setPrintTimeType(0);
        watermarkerEntity.setFontName("宋体");
        watermarkerEntity.setText("保密");
        watermarkerEntity.setCustInfo("");

        Gson gson = new Gson();
        String json = gson.toJson(watermarkerEntity);
        // System.out.println(json);
        return json;
    }
}
