package com.zzw.common.core.util;

import java.util.UUID;

public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式时间戳+随机数
     */
    public static synchronized String genUniqueKey(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
