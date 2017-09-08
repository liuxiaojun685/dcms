package cn.bestsec.dcms.platform.utils.apiGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class ApiGenerator {
    private ApiParser apiParser;
    private String outputDir;
    private Configuration templateConf;

    public void init() throws IOException {
        templateConf = new Configuration(Configuration.VERSION_2_3_25);
        templateConf.setDirectoryForTemplateLoading(new File(ApiGenerator.class.getResource("").getFile()));
        templateConf.setDefaultEncoding("UTF-8");
    }

    public ApiParser getApiParser() {
        return apiParser;
    }

    public void setApiParser(ApiParser apiParser) {
        this.apiParser = apiParser;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    private String _getApiOutputDir(String rootPackageName) {
        return outputDir + "/" + rootPackageName.replaceAll("\\.", "/");
    }

    private String _getModelOutputDir(String rootPackageName) {
        return outputDir + "/" + rootPackageName.replaceAll("\\.", "/") + "/model";
    }

    public void writeModels() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException,
            IOException, TemplateException {
        Template template = templateConf.getTemplate("model.java.ftl");
        for (ModelMeta modelMeta : apiParser.getModelMetas()) {
            String rootPackageName = modelMeta.getRootPackageName();
            FileWriter out = new FileWriter(
                    new File(_getModelOutputDir(rootPackageName), modelMeta.getClassName() + ".java"));
            template.process(modelMeta, out);
        }
    }

    public void writeApis() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException,
            IOException, TemplateException {
        Template template = templateConf.getTemplate("api.java.ftl");
        for (ApiMeta apiMeta : apiParser.getApiMetas()) {
            String rootPackageName = apiMeta.getRootPackageName();
            FileWriter out = new FileWriter(
                    new File(_getApiOutputDir(rootPackageName), apiMeta.getClassName() + ".java"));
            template.process(apiMeta, out);
        }
    }
}
