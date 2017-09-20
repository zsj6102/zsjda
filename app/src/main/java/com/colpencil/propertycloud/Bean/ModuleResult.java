package com.colpencil.propertycloud.Bean;

import org.codehaus.jackson.map.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaohuihui on 2017/1/12.
 */

public class ModuleResult implements Serializable {

    public int code;
    public int result;
    public String message;
    public List<List<ModuleInfo>> data;

}
