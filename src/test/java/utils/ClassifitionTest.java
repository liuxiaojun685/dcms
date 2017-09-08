package utils;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import cn.bestsec.dcms.platform.utils.classification.IClassifitionJni;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月5日 下午3:02:49
 */
public class ClassifitionTest {

    public static void main(String[] args) {
        // String filePath = "D:\\work\\DCMS\\custom\\My_enc_12.doc";
        String filePath = "C:\\Users\\Hardsun\\Desktop\\33.doc";
        Pointer ptr = new Memory(4096);
        ptr.clear(4096);
        try {
            boolean ret = IClassifitionJni.instance.KfGetJosnFromEncFile(new String(filePath.getBytes(), "GB2312"), ptr,
                    4096);
            byte[] data = ptr.getByteArray(0, 4096);
            String str = new String(data, "UTF-8");
            System.out.println(data.length);
            System.out.println(str);
            System.out.println(str.length());
            String json = "{\"classificationHistory\":[{\"durationBefore\":{\"classificationLevelBefore\":1,\"classificationPeriod\":\"300000\",\"classifiedTime\":1491909434046,\"declassifiedTime\":0,\"durationDescription\":\"\",\"longterm\":1},\"historyDescription\":\"111\",\"historyType\":1,\"time\":1491909434046,\"userID\":9813046}],\"accessDescription\":\"111111111111111\",\"accessScope\":[{\"organizationID\":10001,\"userID\":8662774},{\"organizationID\":10001,\"userID\":5520593},{\"organizationID\":10001,\"userID\":2249249}],\"basisDescription\":[{\"basisContent\":\"11\",\"basisLevel\":1},{\"basisContent\":\"2222\",\"basisLevel\":1}],\"basisType\":1,\"classificationAuthority\":{\"personId\":9813046,\"personName\":\"刘晓军\"},\"classificationDuration\":{\"classificationPeriod\":\"300000\",\"classifiedTime\":1491909434046,\"declassifiedTime\":0,\"durationDescription\":\"\",\"longterm\":0},\"classificationIdentifier\":\"223456789\",\"classificationLevel\":1,\"classificationOrganizationMajor\":{\"organizationID\":10001,\"organizationName\":\"北京xxx集团公司\"},\"classificationOrganizationMinor\":[{\"organizationID\":10002,\"organizationName\":\"辅助定密单位1\"},{\"organizationID\":10003,\"organizationName\":\"辅助定密单位2\"},{\"organizationID\":10004,\"organizationName\":\"辅助定密单位3\"}],\"classificationStatus\":3,\"docNumber\":1,\"duplicationAmount\":1,\"issueNumber\":\"111\",\"urgency\":1,\"operationControl\":{\"copyPolicy\":0,\"modifyPolicy\":1,\"pastePolicy\":0,\"printHideWaterPolicy\":1,\"printPolicy\":1,\"readPolicy\":1,\"savePolicy\":0,\"screenShotPolicy\":1},\"version\":1}";
//            String json2 = "{\"token\":\"testToken\",\"issueNumber\":\"111\",\"docNumber\":1,\"duplicationAmount\":1,\"operationControl\":{\"copyPolicy\":1,\"pastePolicy\":1,\"printPolicy\":1,\"printHideWaterPolicy\":1,\"modifyPolicy\":1,\"screenShotPolicy\":1,\"savePolicy\":1,\"readPolicy\":1},\"version\":1,\"classificationLevel\":1,\"classificationIdentifier\":\"12345678\",\"classificationDuration\":{\"classifiedTime\":1234,\"longterm\":1,\"classificationPeriod\":\"003212\",\"declassifiedTime\":1234,\"durationDescription\":\"µ½ÆÚ½âÃÜ\"},\"drafter\":{\"personId\":12,\"personName\":\"ÈËÔ±ÐÕÃû1\"},\"classificationAuthority\":{\"personId\":123,\"personName\":\"ÈËÔ±ÐÕÃû2\"},\"issuer\":{\"personId\":1234,\"personName\":\"ÈËÔ±ÐÕÃû3\"},\"classificationOrganizationMajor\":{\"organizationID\":1,\"organizationName\":\"Ö÷¶¨ÃÜ1\"},\"classificationOrganizationMinor\":[{\"organizationID\":2,\"organizationName\":\"¸¨Öú¶¨ÃÜ2\"},{\"organizationID\":3,\"organizationName\":\"¸¨Öú¶¨ÃÜ3\"}],\"accessDescription\":\"ÒÔÏÂÈËÔ±¿ÉÓÃ\",\"accessScope\":[{\"organizationID\":1,\"userID\":1},{\"organizationID\":1,\"userID\":12},{\"organizationID\":1,\"userID\":1222},{\"organizationID\":222,\"userID\":22222}],\"basisType\":2,\"basisDescription\":[{\"basisLevel\":1,\"basisContent\":\"11\"},{\"basisLevel\":1,\"basisContent\":\"2222\"}],\"classificationStatus\":1,\"classificationHistory\":[{\"time\":12,\"userID\":12,\"historyType\":1,\"classificationLevelBefore\":1,\"historyDescription\":\"ÕâÊÇÀúÊ·¼ÇÂ¼\",\"durationBefore\":{\"classifiedTime\":1234,\"longterm\":1,\"classificationPeriod\":\"003212\",\"declassifiedTime\":1234,\"durationDescription\":\"µ½ÆÚ½âÃÜ\"}},{\"time\":12,\"userID\":12,\"historyType\":1,\"classificationLevelBefore\":1,\"historyDescription\":\"ÕâÊÇÀúÊ·¼ÇÂ¼\",\"durationBefore\":{\"classifiedTime\":1234,\"longterm\":1,\"classificationPeriod\":\"003212\",\"declassifiedTime\":1234,\"durationDescription\":\"µ½ÆÚ½âÃÜ\"}}]}";
            System.out.println(json);
            // ClassificationNativeEntity entity = JSON.parseObject(json,
            // ClassificationNativeEntity.class);
            // System.out.println(entity.classificationIdentifier);
            //
            // entity.classificationStatus =
            // ClassificationStatus.preclassified.value;
            IClassifitionJni.instance.KfWriteClassificationEntityToFileHead(new String(filePath.getBytes(), "GB2312"),
                    json, new IntByReference());

        } catch (Throwable e) {
            e.printStackTrace();
        }
        // ClassificationEntity entity = ClassificationTool.readProperties(new
        // File(filePath));
        // System.out.println(entity.classificationAttribute.classificationStatus);
        // entity.classificationAttribute.classificationStatus =
        // ClassificationStatus.preclassified;
        // ClassificationHistory h = new ClassificationHistory();
        // h.time = 123L;
        // entity.classificationAttribute.classificationHistory.add(h);
        // ClassificationTool.writeProperties(new File(filePath), entity);
    }
}
