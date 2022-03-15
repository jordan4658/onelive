package com.onelive.common.utils.Login;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author
 * @Description:
 * @date
 */
@Data
@Component
@ConfigurationProperties(prefix = "tools")
public class IPdatabaseConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    public String ipDatabase;

}
