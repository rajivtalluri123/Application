package com.hmhco.api.grading.controller.utils;

import com.hmhco.api.grading.views.request.SupportedVersion;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@ControllerAdvice
public class VersionBasedViewResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType,
            ServerHttpRequest request, ServerHttpResponse response) {

        Class<?> viewClass = getView(request);
        if (viewClass != null) {
            bodyContainer.setSerializationView(viewClass);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<?> getView(ServerHttpRequest request) {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Map<String, String> pathVariables = (Map<String, String>) servletRequest
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String version = pathVariables == null ? null : pathVariables.get(Constants.VERSION_PARAM_NAME);

        if (version == null) {
            return null;
        }

        SupportedVersion supportedVersion = SupportedVersion.fromStringSafe(version);
        return supportedVersion == null ? null : supportedVersion.getViewClass();
    }
}
