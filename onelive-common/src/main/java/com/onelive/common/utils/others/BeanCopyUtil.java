package com.onelive.common.utils.others;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * @author mao
 *
 */
public class BeanCopyUtil extends BeanUtils {

	/**
	 * list copyProperties
	 * @param sources
	 * @param clazz
	 * @return
	 */
	public static <I, T> List<I> copyCollection(Collection<T> sources, Class<I> clazz) {
		if(CollectionUtil.isEmpty(sources)) {
			return new ArrayList<I>(0);
		}
		List<I> targetList = new ArrayList<>(sources.size());
		for (T domain : sources) {
			I info = null;
			try {
				info = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			BeanUtils.copyProperties(domain, info);
			if (info != null) {
				targetList.add(info);
			}
		}
		return targetList;
	}

}
