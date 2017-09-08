package cn.bestsec.dcms.platform.utils.apiGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.datamodel.ObjectTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.api.model.v10.system.types.MarkdownString;

import cn.bestsec.dcms.platform.utils.apiGenerator.ModelMeta.FieldMeta;

public class ApiParser {
    private String ramlLocation;
    private String rootPackageName;
    private Api apiv10;
    private List<ApiMeta> apiMetas;
    private List<ModelMeta> modelMetas;

    public String getRamlLocation() {
        return ramlLocation;
    }

    public void setRamlLocation(String ramlLocation) {
        this.ramlLocation = ramlLocation;
    }

    public String getRootPackageName() {
        return rootPackageName;
    }

    public void setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
    }

    public List<ApiMeta> getApiMetas() {
        return apiMetas;
    }

    public void setApiMetas(List<ApiMeta> apiMetas) {
        this.apiMetas = apiMetas;
    }

    public List<ModelMeta> getModelMetas() {
        return modelMetas;
    }

    public void setModelMetas(List<ModelMeta> modelMetas) {
        this.modelMetas = modelMetas;
    }

    public void init() {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(ramlLocation);
        if (ramlModelResult.hasErrors()) {
            throw new RuntimeException("parse raml error");
        }
        apiv10 = ramlModelResult.getApiV10();
    }

    public void parseModels() {
        modelMetas = new ArrayList<>();
        parseTypes();
        parseLibraries();
    }

    private void parseTypes() {
        for (TypeDeclaration type : apiv10.types()) {
            if (type instanceof ObjectTypeDeclaration) {
                parseModel((ObjectTypeDeclaration) type);
            }
        }
    }

    private void parseLibraries() {
        for (Library library : apiv10.uses()) {
            for (TypeDeclaration type : library.types()) {
                if (type instanceof ObjectTypeDeclaration) {
                    parseModel((ObjectTypeDeclaration) type);
                }
            }
        }
    }

    private void parseModel(ObjectTypeDeclaration objType) {
        ModelMeta modelMeta = new ModelMeta();
        modelMeta.setRootPackageName(rootPackageName);
        modelMeta.setClassName(objType.name());
        modelMeta.setFieldMetas(new ArrayList<FieldMeta>());
        modelMeta.setImports(new HashSet<String>());
        appendSupportImports(modelMeta);
        for (TypeDeclaration propType : objType.properties()) {
            FieldMeta fieldMeta = new FieldMeta();
            MarkdownString markdownString = propType.description();
            if (markdownString != null) {
                fieldMeta.setDescription(markdownString.value());
            }
            fieldMeta.setFieldName(propType.name());
            fieldMeta.setFieldType(parseJavaFieldTypeAndImports(propType.type(), modelMeta));
            Boolean required = propType.required();
            if (required != null) {
                fieldMeta.setRequired(required);
            } else {
                fieldMeta.setRequired(Boolean.TRUE);
            }
            modelMeta.getFieldMetas().add(fieldMeta);
        }
        modelMetas.add(modelMeta);
    }

    private void appendSupportImports(ModelMeta modelMeta) {
        if (modelMeta.getClassName().endsWith("Request")) {
            modelMeta.getImports().add(rootPackageName + ".support.CommonRequestFieldsSupport");
        } else if (modelMeta.getClassName().endsWith("Response")) {
            modelMeta.getImports().add(rootPackageName + ".support.CommonResponseFieldsSupport");
        }
    }

    private String parseJavaFieldTypeAndImports(String ramlFieldType, ModelMeta modelMeta) {
        if ("string".equals(ramlFieldType)) {
            return "String";
        }
        if ("integer".equals(ramlFieldType)) {
            return "Integer";
        }
        if ("number".equals(ramlFieldType)) {
            return "Long";
        }
        if (ramlFieldType.endsWith("[]")) {
            modelMeta.getImports().add("java.util.List");
            String type = ramlFieldType.substring(0, ramlFieldType.length() - 2);
            String javaType = parseJavaFieldTypeAndImports(type, modelMeta);
            return String.format("List<%s>", javaType);
        }
        return ramlFieldType;
    }

    public void parseApis() {
        apiMetas = new ArrayList<>();
        for (Resource res : apiv10.resources()) {
            ApiMeta apiMeta = new ApiMeta();
            String[] parts = res.resourcePath().split("/");
            String apiGroupName = parts[1];
            String apiName = parts[2];
            apiMeta.setApiGroupName(apiGroupName);
            apiMeta.setApiName(apiName);
            apiMeta.setClassName(
                    String.format("%s_%sApi", StringUtils.capitalize(apiGroupName), StringUtils.capitalize(apiName)));
            apiMeta.setRootPackageName(rootPackageName);
            apiMeta.setRequestName(res.methods().get(0).body().get(0).type());
            apiMeta.setResponseName(res.methods().get(0).responses().get(0).body().get(0).type());
            if (res.displayName() != null) {
                apiMeta.setDisplayName(res.displayName().value());
            }
            if (res.description() != null) {
                apiMeta.setDescription(res.description().value());
            }
            apiMetas.add(apiMeta);
        }
    }
}
