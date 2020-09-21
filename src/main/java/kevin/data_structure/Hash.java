package kevin.data_structure;

import java.util.HashMap;

/**
 * <p>说明:
 * <p>创建时间: 2020/7/20 18:15
 * <p>创  建  人: 华尚科技 —— 锐凯
 **/
public class Hash {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>(20);
        map.put("aaa","123");
        System.out.println("aaa".hashCode() >>> 16);

    }
}
