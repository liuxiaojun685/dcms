package cn.bestsec.dcms.platform.utils.apiGenerator;

import java.util.List;
import java.util.Set;

public class ModelMeta {
    private Set<String> imports;
    private String rootPackageName;
    private String className;
    private String description;
    private List<FieldMeta> fieldMetas;

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public String getRootPackageName() {
        return rootPackageName;
    }

    public void setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FieldMeta> getFieldMetas() {
        return fieldMetas;
    }

    public void setFieldMetas(List<FieldMeta> fieldMetas) {
        this.fieldMetas = fieldMetas;
    }

    public static class FieldMeta {
        private String fieldType;
        private String fieldName;
        private Boolean required;
        private String description;

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
