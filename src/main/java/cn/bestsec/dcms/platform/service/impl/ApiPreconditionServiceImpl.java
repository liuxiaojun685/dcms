package cn.bestsec.dcms.platform.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.service.ApiPreconditionService;
import cn.bestsec.dcms.platform.utils.apiGenerator.ApiMeta;
import cn.bestsec.dcms.platform.utils.apiGenerator.ApiParser;
import cn.bestsec.dcms.platform.utils.apiGenerator.ModelMeta;
import cn.bestsec.dcms.platform.utils.apiGenerator.ModelMeta.FieldMeta;

@Service
public class ApiPreconditionServiceImpl implements ApiPreconditionService {
    private static final ApiParser apiParser = new ApiParser();

    @Override
    public void initApiParser(String ramlLocation) {
        apiParser.setRamlLocation(ramlLocation);
        apiParser.setRootPackageName("cn.bestsec.dcms.platform.api");
        apiParser.init();
        apiParser.parseModels();
        apiParser.parseApis();
    }

    @Override
    public boolean isValidRequest(JSONObject apiRequest, String apiGroupName, String apiName) {
        ApiMeta apiMeta = getApiMeta(apiGroupName, apiName);
        if (apiMeta == null) {
            return false;
        }
        ModelMeta modelMeta = getModelMeta(apiMeta.getRequestName());
        if (existNullRequiredField(apiRequest, modelMeta)) {
            return false;
        }
        return true;
    }

    private static boolean existNullRequiredField(JSONObject apiRequest, ModelMeta modelMeta) {
        if (modelMeta == null) {
            return false;
        }
        for (FieldMeta fieldMeta : modelMeta.getFieldMetas()) {
            if (!fieldMeta.getRequired()) {
                continue;
            }
            String fieldType = fieldMeta.getFieldType();
            String fieldName = fieldMeta.getFieldName();
            if (!apiRequest.containsKey(fieldName) || apiRequest.get(fieldName) == null) {
                return true;
            }
            if (fieldType.startsWith("List<")) {
                // 深入检查嵌套数组
                String eleType = fieldType.replaceFirst("List<", "").replaceFirst(">", "");
                if ("String".equals(eleType) || "Integer".equals(eleType) || "Long".equals(eleType)) {
                    continue;
                }
                ModelMeta eleModelMeta = getModelMeta(eleType);
                JSONArray jsonArray = apiRequest.getJSONArray(fieldName);
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (existNullRequiredField(jsonArray.getJSONObject(i), eleModelMeta)) {
                        return true;
                    }
                }
            } else if (!"String".equals(fieldType) && !"Integer".equals(fieldType) && !"Long".equals(fieldType)) {
                // 深入检查嵌套对象
                ModelMeta subModelMeta = getModelMeta(fieldType);
                if (existNullRequiredField(apiRequest.getJSONObject(fieldName), subModelMeta)) {
                    return true;
                }
            } else if ("Integer".equals(fieldType)) {
                // 深入检查数字有效性
                try {
                    Integer.parseInt(apiRequest.getString(fieldName));
                } catch (NumberFormatException e) {
                    return true;
                }
            } else if ("Long".equals(fieldType)) {
                // 深入检查数字有效性
                try {
                    Long.parseLong(apiRequest.getString(fieldName));
                } catch (NumberFormatException e) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ModelMeta getModelMeta(String type) {
        for (ModelMeta modelMeta : apiParser.getModelMetas()) {
            if (modelMeta.getClassName().equals(type)) {
                return modelMeta;
            }
        }
        return null;
    }

    private static ApiMeta getApiMeta(String apiGroupName, String apiName) {
        for (ApiMeta apiMeta : apiParser.getApiMetas()) {
            if (apiMeta.getApiGroupName().equals(apiGroupName) && apiMeta.getApiName().equals(apiName)) {
                return apiMeta;
            }
        }
        return null;
    }
}
