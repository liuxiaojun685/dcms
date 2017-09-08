package apiGenerator;

import cn.bestsec.dcms.platform.consts.TokenConsts.UserRole;
import cn.bestsec.dcms.platform.utils.apiGenerator.ApiMeta;
import cn.bestsec.dcms.platform.utils.apiGenerator.ApiParser;

public class GenApiEnumTest {
    public static void main(String[] args) throws Exception {
        ApiParser apiParser = new ApiParser();
        apiParser.setRamlLocation("src/main/webapp/raml/api.raml");
        apiParser.setRootPackageName("cn.bestsec.dcms.platform.api");
        apiParser.init();
        apiParser.parseModels();
        apiParser.parseApis();
        String format = "%s_%s(\"%s\", \"%s\", UserRole.%s), // %s";
        for (ApiMeta apiMeta : apiParser.getApiMetas()) {
            String[] lines = apiMeta.getDescription().split("\\n");
            if (lines.length < 1 || !lines[0].matches("权限:.+")) {
                throw new Exception("缺失权限说明, apiMeta=" + apiMeta);
            }
            String p = lines[0];
            UserRole userRole = null;
            if (p.startsWith("权限:系统管理员")) {
                userRole = UserRole.sysadmin;
            } else if (p.startsWith("权限:安全管理员")) {
                userRole = UserRole.secadmin;
            } else if (p.startsWith("权限:日志审计员")) {
                userRole = UserRole.logadmin;
            } else if (p.startsWith("权限:管理员")) {
                userRole = UserRole.anyadmin;
            } else if (p.startsWith("权限:终端用户")) {
                userRole = UserRole.user;
            } else if (p.startsWith("权限:任意")) {
                userRole = UserRole.any;
            }
            System.out.println(String.format(format, apiMeta.getApiGroupName(), apiMeta.getApiName(),
                    apiMeta.getApiGroupName(), apiMeta.getApiName(), userRole, p));
        }
    }
}
