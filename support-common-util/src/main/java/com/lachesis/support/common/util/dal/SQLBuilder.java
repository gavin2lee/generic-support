package com.lachesis.support.common.util.dal;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public final class SQLBuilder {
	public static final char UNDER_SCORE = '_';
	public static final String FIND_ONE = "buildFindOne";
	public static final String FIND_LIST_BY_CRITERIA = "buildFindListByCriteria";
	public static final String SAVE_ONE = "buildSaveOne";
	public static final String UPDATE_ONE = "buildUpdateOne";
	public static final String DELETE_ONE = "buildDeleteOne";
	
	public static final String P_NAME_TYPE = "clazz";
	public static final String P_NAME_ENTITY = "t";
	public static final String P_NAME_ID = "id";

	public String buildFindListByCriteria(final @Param("t") Object t, final @Param("clazz") Class<?> clazz) {
		try {
			return new SQL() {
				{
					Field[] fs = clazz.getDeclaredFields();

					for (Field f : fs) {
						SELECT(buildSelectField(f));
					}

					FROM(buildTableName(clazz));
					for (Field f : fs) {
						String s = buildWhereFromTemplate(t, f);
						if (s != null) {
							WHERE(buildWhereFromTemplate(t, f));
						}
					}
					WHERE(buildWhereIsDeleted());
				}
			}.toString();
		} catch (Exception e) {
			throw new RuntimeException("errors while buildFindListByTemplate", e);
		}
	}

	private String buildWhereFromTemplate(Object t, Field f) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		if (f.get(t) == null) {
			return null;
		} else {
			return String.format("%s = #{t.%s}", buildCamelColumnName(f.getName()), f.getName());
		}
	}

	public String buildSaveOne(final @Param("t") Object t, final @Param("clazz") Class<?> clazz) {
		try {
			return new SQL() {
				{
					INSERT_INTO(buildTableName(clazz));
					Field[] fs = clazz.getDeclaredFields();
					boolean atLeastOneFieldToPresent = false;
					for (Field f : fs) {
						if (needToPresentsInSave(t, f)) {
							atLeastOneFieldToPresent = true;
							VALUES(buildValuesLabel(f), buildValuesParamPlaceHolder(f));
						}
					}

					if (!atLeastOneFieldToPresent) {
						throw new RuntimeException("at least one field should be assigned value.");
					}
				}
			}.toString();
		} catch (Exception e) {
			throw new RuntimeException("errors while buildSaveOne", e);
		}
	}

	public String buildUpdateOne(final @Param("t") Object t, final @Param("clazz") Class<?> clazz) {
		try {
			return new SQL() {
				{
					UPDATE(buildTableName(clazz));

					Field[] fs = clazz.getDeclaredFields();
					for (Field f : fs) {
						if (!checkIfAppropriateToUpdate(f, t)) {
							throw new RuntimeException("No ID assigned.");
						}

						if (needToUpdate(f, t)) {
							SET(buildSet(f));
						}
					}

					WHERE(buildUpdateWhere());
				}
			}.toString();

		} catch (Exception e) {
			throw new RuntimeException("errors while buildUpdateOne", e);
		}
	}

	private String buildUpdateWhere() {
		return "ID = #{t.id}";
	}

	private String buildSet(Field f) {
		return String.format("%s = #{t.%s}", buildCamelColumnName(f.getName()), f.getName());
	}

	private boolean needToUpdate(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (f.getName().equals("id")) {
			return false;
		}
		f.setAccessible(true);
		if (f.get(obj) == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean checkIfAppropriateToUpdate(Field f, Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		if (f.getName().equals("id")) {
			f.setAccessible(true);
			if (f.get(obj) == null) {
				return false;
			} else {
				return true;
			}
		}

		return true;
	}

	public String buildDeleteOne(final @Param("id") Object id, final @Param("clazz") Class<?> clazz) {
		try {
			return new SQL() {
				{
					if (!checkIfAppropriateToDelete(id)) {
						throw new RuntimeException("No ID assigned.");
					}
					DELETE_FROM(buildTableName(clazz));
					WHERE("id = #{id}");
				}
			}.toString();

		} catch (Exception e) {
			throw new RuntimeException("errors while buildUpdateOne", e);
		}
	}

	private boolean checkIfAppropriateToDelete(Object id) {
		return (id != null);
	}

	private boolean needToPresentsInSave(Object t, Field f) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		if (f.get(t) == null) {
			return false;
		}
		if (f.getName().equals("id")) {
			throw new RuntimeException("ID has already been assigned.");
		}

		return true;
	}

	private String buildValuesLabel(Field f) {
		return buildCamelColumnName(f.getName());
	}

	private String buildValuesParamPlaceHolder(Field f) {
		return String.format("#{t.%s}", f.getName());
	}

	public String buildFindOne(@Param("id") final Serializable id, @Param("clazz") final Class<?> clazz) {
		return new SQL() {
			{
				Field[] fs = clazz.getDeclaredFields();

				for (Field f : fs) {
					SELECT(buildSelectField(f));
				}

				FROM(buildTableName(clazz));
				WHERE(buildWhereID());
				WHERE(buildWhereIsDeleted());
			}
		}.toString();
	}

	private String buildWhereID() {
		return "ID = #{id}";
	}

	private String buildWhereIsDeleted() {
		return "IS_DELETED = 'N'";
	}

	private String buildTableName(Class<?> clazz) {
		return "T_" + clazz.getSimpleName().toUpperCase();
	}

	private String buildSelectField(Field f) {
		return String.format("%s as %s", buildCamelColumnName(f.getName()), f.getName());
	}

	private String buildCamelColumnName(String fieldName) {
		StringBuilder sb = new StringBuilder();
		int len = fieldName.length();

		for (int index = 0; index < len; index++) {
			char c = fieldName.charAt(index);
			if (Character.isUpperCase(c)) {
				sb.append(UNDER_SCORE).append(Character.toUpperCase(c));
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}

		return sb.toString();
	}

	public SQLBuilder() {
	}
}
