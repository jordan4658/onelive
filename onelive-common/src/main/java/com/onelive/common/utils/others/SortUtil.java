package com.onelive.common.utils.others;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lorenzo
 * @Description 排序工具类
 * @Date 2021/5/19 10:22
 */
public class SortUtil {


    /**
     * 传入一个实体集合,根据集合里面的最小的排序值按照集合循序重新设置排序值(默认排序值字段为'sortBy')
     *
     * @param dataList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> sorted(List<T> dataList) throws Exception {
        return sorted(dataList, "sortby");
    }

    /**
     * 传入一个实体集合,根据集合里面的最小的排序值按照集合循序重新设置排序值
     *
     * @param dataList  实体集合
     * @param sortByKey 排序值字段名称
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> sorted(List<T> dataList, String sortByKey) throws Exception {
        // 首先,从集合里面拿到最小的排序值
        List<Integer> sortByList = new ArrayList<>();
        for (T t : dataList) {
            Field field = t.getClass().getDeclaredField(sortByKey);
            field.setAccessible(true);
            Integer sortBy = (Integer) field.get(t);
            sortByList.add(sortBy);
        }
        int minSortBy = sortByList.stream().mapToInt(Integer::intValue).min().getAsInt();

        // 然后,按顺序重新累加排序值
        for (T t : dataList) {
            Field field = t.getClass().getDeclaredField(sortByKey);
            field.setAccessible(true);
            field.set(t, minSortBy);
            minSortBy++;
        }
        return dataList;
    }

    /*@Data
    @AllArgsConstructor
    public static class SortBean {
        private Long id;
        private Integer sortBy;
    }*/

    /*public static void main(String[] args) throws Exception {
        List<SortBean> sortBeans = Arrays.asList(
                new SortBean(10L, 10),
                new SortBean(1L, 1),
                new SortBean(2L, 2),
                new SortBean(3L, 3),
                new SortBean(4L, 4),
                new SortBean(5L, 5),
                new SortBean(6L, 6),
                new SortBean(7L, 7),
                new SortBean(8L, 8),
                new SortBean(9L, 9)
        );
        List<SortBean> sorted = sorted(sortBeans);
    }*/


}
