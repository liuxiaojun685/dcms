package apiGenerator;

import cn.bestsec.dcms.platform.utils.apiGenerator.ApiGenerator;
import cn.bestsec.dcms.platform.utils.apiGenerator.ApiParser;

public class ApiGeneratorTest {
    public static void main(String[] args) throws Exception {
        ApiParser apiParser = new ApiParser();
        apiParser.setRamlLocation("src/main/webapp/raml/api.raml");
        apiParser.setRootPackageName("cn.bestsec.dcms.platform.api");
        apiParser.init();
        apiParser.parseModels();
        apiParser.parseApis();
        ApiGenerator apiGenerator = new ApiGenerator();
        apiGenerator.setOutputDir("src/main/java/");
        apiGenerator.setApiParser(apiParser);
        apiGenerator.init();
        apiGenerator.writeModels();
        apiGenerator.writeApis();
    }
}
