package br.com.simpleworks.foundation.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simpleworks.foundation.entity.BaseEntity;

public class EntityUtil {

	/**
	 * Transforma uma lista de entidades em um Map<Id, Entidade>
	 *
	 * @author João Dimas
	 * @since 03/05/2012
	 * @param list
	 * @return
	 */
	public static <T extends BaseEntity<ID>, ID extends Serializable> Map<ID, T> getEntityListAsMap(List<T> list) {
		Map<ID, T> result = new HashMap<ID, T>();
		
		for(T entity : list) {
			result.put(entity.getId(), entity);
		}
		
		return result;
	}
}
