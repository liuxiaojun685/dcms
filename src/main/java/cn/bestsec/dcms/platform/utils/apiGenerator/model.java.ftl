package ${rootPackageName}.model;
<#if imports??>

<#list imports as import>
import ${import};
</#list>
</#if>

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ${className}<#if className?ends_with("Request")> extends CommonRequestFieldsSupport<#elseif className?ends_with("Response")> extends CommonResponseFieldsSupport</#if> {
<#list fieldMetas as fieldMeta>
    private ${fieldMeta.fieldType} ${fieldMeta.fieldName};
</#list>
    
    public ${className}() {
    }
<#if className?ends_with("Response") && (fieldMetas?size>2)>

    public ${className}(<#list fieldMetas as fieldMeta><#if fieldMeta.fieldName!="code" && fieldMeta.fieldName!="msg">${fieldMeta.fieldType} ${fieldMeta.fieldName}<#if (fieldMeta_has_next)>, </#if></#if></#list>) {
        <#list fieldMetas as fieldMeta><#if fieldMeta.fieldName!="code" && fieldMeta.fieldName!="msg">
        this.${fieldMeta.fieldName} = ${fieldMeta.fieldName};
        </#if></#list>
    }
</#if>
<#if (fieldMetas?size>0)>

    public ${className}(<#list fieldMetas as fieldMeta>${fieldMeta.fieldType} ${fieldMeta.fieldName}<#if (fieldMeta_has_next)>, </#if></#list>) {
        <#list fieldMetas as fieldMeta>
        this.${fieldMeta.fieldName} = ${fieldMeta.fieldName};
        </#list>
    }
</#if>
<#list fieldMetas as fieldMeta>

    /**
     * ${fieldMeta.description!}
     */
    public ${fieldMeta.fieldType} get${fieldMeta.fieldName?cap_first}() {
        return ${fieldMeta.fieldName};
    }

    public void set${fieldMeta.fieldName?cap_first}(${fieldMeta.fieldType} ${fieldMeta.fieldName}) {
        this.${fieldMeta.fieldName} = ${fieldMeta.fieldName};
    }
</#list>
}
