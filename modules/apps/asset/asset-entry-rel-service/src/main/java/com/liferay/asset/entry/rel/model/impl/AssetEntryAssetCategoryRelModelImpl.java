/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.entry.rel.model.impl;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRelModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the AssetEntryAssetCategoryRel service. Represents a row in the &quot;AssetEntryAssetCategoryRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>AssetEntryAssetCategoryRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetEntryAssetCategoryRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelImpl
 * @generated
 */
public class AssetEntryAssetCategoryRelModelImpl
	extends BaseModelImpl<AssetEntryAssetCategoryRel>
	implements AssetEntryAssetCategoryRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset entry asset category rel model instance should use the <code>AssetEntryAssetCategoryRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "AssetEntryAssetCategoryRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"assetEntryAssetCategoryRelId", Types.BIGINT},
		{"assetEntryId", Types.BIGINT}, {"assetCategoryId", Types.BIGINT},
		{"priority", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("assetEntryAssetCategoryRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assetEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assetCategoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AssetEntryAssetCategoryRel (assetEntryAssetCategoryRelId LONG not null primary key,assetEntryId LONG,assetCategoryId LONG,priority INTEGER)";

	public static final String TABLE_SQL_DROP =
		"drop table AssetEntryAssetCategoryRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY assetEntryAssetCategoryRel.assetEntryAssetCategoryRelId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AssetEntryAssetCategoryRel.assetEntryAssetCategoryRelId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.asset.entry.rel.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.asset.entry.rel.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.asset.entry.rel.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel"),
		true);

	public static final long ASSETCATEGORYID_COLUMN_BITMASK = 1L;

	public static final long ASSETENTRYID_COLUMN_BITMASK = 2L;

	public static final long ASSETENTRYASSETCATEGORYRELID_COLUMN_BITMASK = 4L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.asset.entry.rel.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel"));

	public AssetEntryAssetCategoryRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _assetEntryAssetCategoryRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAssetEntryAssetCategoryRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetEntryAssetCategoryRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntryAssetCategoryRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntryAssetCategoryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AssetEntryAssetCategoryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AssetEntryAssetCategoryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetEntryAssetCategoryRel, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(AssetEntryAssetCategoryRel)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AssetEntryAssetCategoryRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AssetEntryAssetCategoryRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AssetEntryAssetCategoryRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AssetEntryAssetCategoryRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AssetEntryAssetCategoryRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AssetEntryAssetCategoryRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AssetEntryAssetCategoryRel.class.getClassLoader(),
			AssetEntryAssetCategoryRel.class, ModelWrapper.class);

		try {
			Constructor<AssetEntryAssetCategoryRel> constructor =
				(Constructor<AssetEntryAssetCategoryRel>)
					proxyClass.getConstructor(InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map
		<String, Function<AssetEntryAssetCategoryRel, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<AssetEntryAssetCategoryRel, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<AssetEntryAssetCategoryRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<AssetEntryAssetCategoryRel, Object>>();
		Map<String, BiConsumer<AssetEntryAssetCategoryRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<AssetEntryAssetCategoryRel, ?>>();

		attributeGetterFunctions.put(
			"assetEntryAssetCategoryRelId",
			new Function<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public Object apply(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

					return assetEntryAssetCategoryRel.
						getAssetEntryAssetCategoryRelId();
				}

			});
		attributeSetterBiConsumers.put(
			"assetEntryAssetCategoryRelId",
			new BiConsumer<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public void accept(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
					Object assetEntryAssetCategoryRelId) {

					assetEntryAssetCategoryRel.setAssetEntryAssetCategoryRelId(
						(Long)assetEntryAssetCategoryRelId);
				}

			});
		attributeGetterFunctions.put(
			"assetEntryId",
			new Function<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public Object apply(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

					return assetEntryAssetCategoryRel.getAssetEntryId();
				}

			});
		attributeSetterBiConsumers.put(
			"assetEntryId",
			new BiConsumer<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public void accept(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
					Object assetEntryId) {

					assetEntryAssetCategoryRel.setAssetEntryId(
						(Long)assetEntryId);
				}

			});
		attributeGetterFunctions.put(
			"assetCategoryId",
			new Function<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public Object apply(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

					return assetEntryAssetCategoryRel.getAssetCategoryId();
				}

			});
		attributeSetterBiConsumers.put(
			"assetCategoryId",
			new BiConsumer<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public void accept(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
					Object assetCategoryId) {

					assetEntryAssetCategoryRel.setAssetCategoryId(
						(Long)assetCategoryId);
				}

			});
		attributeGetterFunctions.put(
			"priority",
			new Function<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public Object apply(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

					return assetEntryAssetCategoryRel.getPriority();
				}

			});
		attributeSetterBiConsumers.put(
			"priority",
			new BiConsumer<AssetEntryAssetCategoryRel, Object>() {

				@Override
				public void accept(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
					Object priority) {

					assetEntryAssetCategoryRel.setPriority((Integer)priority);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getAssetEntryAssetCategoryRelId() {
		return _assetEntryAssetCategoryRelId;
	}

	@Override
	public void setAssetEntryAssetCategoryRelId(
		long assetEntryAssetCategoryRelId) {

		_assetEntryAssetCategoryRelId = assetEntryAssetCategoryRelId;
	}

	@Override
	public long getAssetEntryId() {
		return _assetEntryId;
	}

	@Override
	public void setAssetEntryId(long assetEntryId) {
		_columnBitmask |= ASSETENTRYID_COLUMN_BITMASK;

		if (!_setOriginalAssetEntryId) {
			_setOriginalAssetEntryId = true;

			_originalAssetEntryId = _assetEntryId;
		}

		_assetEntryId = assetEntryId;
	}

	public long getOriginalAssetEntryId() {
		return _originalAssetEntryId;
	}

	@Override
	public long getAssetCategoryId() {
		return _assetCategoryId;
	}

	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		_columnBitmask |= ASSETCATEGORYID_COLUMN_BITMASK;

		if (!_setOriginalAssetCategoryId) {
			_setOriginalAssetCategoryId = true;

			_originalAssetCategoryId = _assetCategoryId;
		}

		_assetCategoryId = assetCategoryId;
	}

	public long getOriginalAssetCategoryId() {
		return _originalAssetCategoryId;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			0, AssetEntryAssetCategoryRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetEntryAssetCategoryRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AssetEntryAssetCategoryRel>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AssetEntryAssetCategoryRelImpl assetEntryAssetCategoryRelImpl =
			new AssetEntryAssetCategoryRelImpl();

		assetEntryAssetCategoryRelImpl.setAssetEntryAssetCategoryRelId(
			getAssetEntryAssetCategoryRelId());
		assetEntryAssetCategoryRelImpl.setAssetEntryId(getAssetEntryId());
		assetEntryAssetCategoryRelImpl.setAssetCategoryId(getAssetCategoryId());
		assetEntryAssetCategoryRelImpl.setPriority(getPriority());

		assetEntryAssetCategoryRelImpl.resetOriginalValues();

		return assetEntryAssetCategoryRelImpl;
	}

	@Override
	public int compareTo(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

		long primaryKey = assetEntryAssetCategoryRel.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetCategoryRel)) {
			return false;
		}

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			(AssetEntryAssetCategoryRel)obj;

		long primaryKey = assetEntryAssetCategoryRel.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		AssetEntryAssetCategoryRelModelImpl
			assetEntryAssetCategoryRelModelImpl = this;

		assetEntryAssetCategoryRelModelImpl._originalAssetEntryId =
			assetEntryAssetCategoryRelModelImpl._assetEntryId;

		assetEntryAssetCategoryRelModelImpl._setOriginalAssetEntryId = false;

		assetEntryAssetCategoryRelModelImpl._originalAssetCategoryId =
			assetEntryAssetCategoryRelModelImpl._assetCategoryId;

		assetEntryAssetCategoryRelModelImpl._setOriginalAssetCategoryId = false;

		assetEntryAssetCategoryRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AssetEntryAssetCategoryRel> toCacheModel() {
		AssetEntryAssetCategoryRelCacheModel
			assetEntryAssetCategoryRelCacheModel =
				new AssetEntryAssetCategoryRelCacheModel();

		assetEntryAssetCategoryRelCacheModel.assetEntryAssetCategoryRelId =
			getAssetEntryAssetCategoryRelId();

		assetEntryAssetCategoryRelCacheModel.assetEntryId = getAssetEntryId();

		assetEntryAssetCategoryRelCacheModel.assetCategoryId =
			getAssetCategoryId();

		assetEntryAssetCategoryRelCacheModel.priority = getPriority();

		return assetEntryAssetCategoryRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AssetEntryAssetCategoryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AssetEntryAssetCategoryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetEntryAssetCategoryRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(AssetEntryAssetCategoryRel)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<AssetEntryAssetCategoryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AssetEntryAssetCategoryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetEntryAssetCategoryRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(AssetEntryAssetCategoryRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, AssetEntryAssetCategoryRel>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _assetEntryAssetCategoryRelId;
	private long _assetEntryId;
	private long _originalAssetEntryId;
	private boolean _setOriginalAssetEntryId;
	private long _assetCategoryId;
	private long _originalAssetCategoryId;
	private boolean _setOriginalAssetCategoryId;
	private int _priority;
	private long _columnBitmask;
	private AssetEntryAssetCategoryRel _escapedModel;

}