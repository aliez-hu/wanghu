package com.hu.wang;

import java.time.LocalDateTime;

/**
 * @author hwang30
 * @date 2024/6/5 14:48
 */
public class Test {
    public static void main(String[] args) {
        LocalDateTime localDateTime= LocalDateTime.now();
        String str = String.valueOf(localDateTime).replace("-","").replace("T","").replace(":","").replace(".","");
        System.out.println("Œ¢√Î ±º‰¥¡£∫"+str);
    }
}
