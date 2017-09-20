package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/12/3
 */
public class ListAdvice implements Serializable {

    public List<AdviceList> opinion;

    @Override
    public String toString() {
        return "ListAdvice{" +
                "opinion=" + opinion +
                '}';
    }
}
