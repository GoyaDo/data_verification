package com.ysmjjsy.verification.tool;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author cj
 * @since 2022-11-10
 */
@NoArgsConstructor(staticName = "private")
public class MyStringUtils {

    public static void main(String[] args) {
        String s1 = "SX00_3,1,SX_001,SX00_2,4,SX";
        String s2 = "SX00_3,1,SX_001,SX00_2,4,SX,SX00_3,1,SX_001,SX00_2,4,SX,SX00_3,1,SX_001,SX00_2,4,SX";
        boolean b = compareStringContains(s1, s2);
        System.out.println(b);
    }

    public static boolean compareStringContains(String v1, String v2) {
        if (StringUtils.isNoneEmpty(v1, v2)) {
            List<String> s1 = Arrays.asList(StringUtils.split(v1, ","));
            List<String> s2 = Arrays.asList(StringUtils.split(v2, ","));
            return s1.stream().anyMatch(s2::contains) || s2.stream().anyMatch(s1::contains);
        }
        return Boolean.FALSE;
    }

}
