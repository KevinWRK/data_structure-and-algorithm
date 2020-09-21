package kevin.data_structure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>说明:
 * <p>创建时间: 2020/7/16 15:12
 * <p>创  建  人: 华尚科技 —— 锐凯
 **/
public class Array {
    public static void main(String[] args) throws Exception {
        Class<ArrayList> clazz = ArrayList.class;
        Constructor<? extends ArrayList> constructor = clazz.getConstructor();
        ArrayList arrayList = constructor.newInstance();
        Method method1 = clazz.getDeclaredMethod("add",Object.class);
        int i = 0;
        while (i < 11){
            method1.invoke(arrayList,"a");
            i++;
        }

        Field field = clazz.getDeclaredField("elementData");
        field.setAccessible(true);
        Object[] o = (Object[])field.get(arrayList);
        System.out.println(Arrays.toString(o));
        System.out.println(o.length);
        System.out.println(arrayList);
    }
}
