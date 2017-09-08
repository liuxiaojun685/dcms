package cn.bestsec.dcms.platform.utils.watermarker;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;

public interface IWaterMarkApi extends Library {
    IWaterMarkApi instanceDll = (IWaterMarkApi) Native.loadLibrary("FileHeadApi", IWaterMarkApi.class);

    public int Add(int a, int b);

    public String readFromFileHead(String path);

    public int writeToFileHead(String path, //
            NativeLong left, NativeLong top, NativeLong right, NativeLong bottom, //
            NativeLong fontSize, //
            byte colorA, byte colorR, byte colorG, byte colorB, //
            NativeLong fontEscapement, //
            int fontTilt, int markLocal, int markLayout, int markRows, int markCols, //
            int MarkTop, int MarkLeft, int MarkRight, int MarkBottom, int MarkQuality, //
            int MarkType, int PrintTime, int CompNameType, int UserNameType, int HostAddrType, //
            int CustInfoType, int PrinTimeType, //
            WString fontName, WString textMark, WString pictMark, WString compName, //
            WString userName, WString hostAddr, WString custInfo);

}
