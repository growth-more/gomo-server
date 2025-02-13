package com.gomo.app.common.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="auth")
public class AuthenticationFilterConfiguration {

    private List<ExcludedPath> excludedPaths;

    public List<ExcludedPath> getExcludedPaths(){
        return excludedPaths;
    }

    public void setExcludedPaths(List<ExcludedPath> excludedPaths){
        this.excludedPaths = excludedPaths;
    }

    public boolean isExcluded(String requestURI, String requestMethod){
        return excludedPaths.stream().anyMatch(path -> requestURI.startsWith(path.getPath()) && path.getMethods().contains(requestMethod));
    }

    @Getter
    @Setter
    public static class ExcludedPath{
        private String path;
        private List<String> methods;
    }
}
