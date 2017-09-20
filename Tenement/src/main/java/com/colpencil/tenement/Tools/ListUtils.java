package com.colpencil.tenement.Tools;

import java.util.Collection;

public class ListUtils {

    /**
     * 判断List是否为空
     * @param collection
     * @return
     */
    public static boolean listIsNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
