package com.bwgl.ztf.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("upload")
public class UploadProperties {

    // 获取存放位置
    private Map<String, String> location;

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    /**
     * 获取保存路径，自动判断当前系统类型，然后获取对应的保存路径
     * @return
     */
    public String getBasePath() {
        String location = "";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            location = this.getLocation().get("windows");
        } else {
            location = this.getLocation().get("linux");
        }
        return location;
    }
}
