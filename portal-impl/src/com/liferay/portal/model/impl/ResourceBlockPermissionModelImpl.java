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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.model.ResourceBlockPermissionModel;
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
 * The base model implementation for the ResourceBlockPermission service. Represents a row in the &quot;ResourceBlockPermission&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>ResourceBlockPermissionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ResourceBlockPermissionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionImpl
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ResourceBlockPermissionModelImpl
	extends BaseModelImpl<ResourceBlockPermission>
	implements ResourceBlockPermissionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a resource block permission model instance should use the <code>ResourceBlockPermission</code> interface instead.
	 */
	public static final String TABLE_NAME = "ResourceBlockPermission";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"resourceBlockPermissionId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"resourceBlockId", Types.BIGINT},
		{"roleId", Types.BIGINT}, {"actionIds", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("resourceBlockPermissionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("resourceBlockId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("actionIds", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table ResourceBlockPermission (mvccVersion LONG default 0 not null,resourceBlockPermissionId LONG not null primary key,companyId LONG,resourceBlockId LONG,roleId LONG,actionIds LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table ResourceBlockPermission";

	public static final String ORDER_BY_JPQL =
		" ORDER BY resourceBlockPermission.resourceBlockPermissionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY ResourceBlockPermission.resourceBlockPermissionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.ResourceBlockPermission"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.ResourceBlockPermission"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.ResourceBlockPermission"),
		true);

	public static final long RESOURCEBLOCKID_COLUMN_BITMASK = 1L;

	public static final long ROLEID_COLUMN_BITMASK = 2L;

	public static final long RESOURCEBLOCKPERMISSIONID_COLUMN_BITMASK = 4L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.ResourceBlockPermission"));

	public ResourceBlockPermissionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _resourceBlockPermissionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setResourceBlockPermissionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _resourceBlockPermissionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ResourceBlockPermission.class;
	}

	@Override
	public String getModelClassName() {
		return ResourceBlockPermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<ResourceBlockPermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<ResourceBlockPermission, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourceBlockPermission, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((ResourceBlockPermission)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<ResourceBlockPermission, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<ResourceBlockPermission, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(ResourceBlockPermission)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<ResourceBlockPermission, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<ResourceBlockPermission, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, ResourceBlockPermission>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			ResourceBlockPermission.class.getClassLoader(),
			ResourceBlockPermission.class, ModelWrapper.class);

		try {
			Constructor<ResourceBlockPermission> constructor =
				(Constructor<ResourceBlockPermission>)proxyClass.getConstructor(
					InvocationHandler.class);

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

	private static final Map<String, Function<ResourceBlockPermission, Object>>
		_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<ResourceBlockPermission, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<ResourceBlockPermission, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<ResourceBlockPermission, Object>>();
		Map<String, BiConsumer<ResourceBlockPermission, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<ResourceBlockPermission, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.getMvccVersion();
				}

			});
		attributeSetterBiConsumers.put(
			"mvccVersion",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object mvccVersion) {

					resourceBlockPermission.setMvccVersion((Long)mvccVersion);
				}

			});
		attributeGetterFunctions.put(
			"resourceBlockPermissionId",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.
						getResourceBlockPermissionId();
				}

			});
		attributeSetterBiConsumers.put(
			"resourceBlockPermissionId",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object resourceBlockPermissionId) {

					resourceBlockPermission.setResourceBlockPermissionId(
						(Long)resourceBlockPermissionId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object companyId) {

					resourceBlockPermission.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"resourceBlockId",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.getResourceBlockId();
				}

			});
		attributeSetterBiConsumers.put(
			"resourceBlockId",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object resourceBlockId) {

					resourceBlockPermission.setResourceBlockId(
						(Long)resourceBlockId);
				}

			});
		attributeGetterFunctions.put(
			"roleId",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.getRoleId();
				}

			});
		attributeSetterBiConsumers.put(
			"roleId",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object roleId) {

					resourceBlockPermission.setRoleId((Long)roleId);
				}

			});
		attributeGetterFunctions.put(
			"actionIds",
			new Function<ResourceBlockPermission, Object>() {

				@Override
				public Object apply(
					ResourceBlockPermission resourceBlockPermission) {

					return resourceBlockPermission.getActionIds();
				}

			});
		attributeSetterBiConsumers.put(
			"actionIds",
			new BiConsumer<ResourceBlockPermission, Object>() {

				@Override
				public void accept(
					ResourceBlockPermission resourceBlockPermission,
					Object actionIds) {

					resourceBlockPermission.setActionIds((Long)actionIds);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getResourceBlockPermissionId() {
		return _resourceBlockPermissionId;
	}

	@Override
	public void setResourceBlockPermissionId(long resourceBlockPermissionId) {
		_resourceBlockPermissionId = resourceBlockPermissionId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getResourceBlockId() {
		return _resourceBlockId;
	}

	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_columnBitmask |= RESOURCEBLOCKID_COLUMN_BITMASK;

		if (!_setOriginalResourceBlockId) {
			_setOriginalResourceBlockId = true;

			_originalResourceBlockId = _resourceBlockId;
		}

		_resourceBlockId = resourceBlockId;
	}

	public long getOriginalResourceBlockId() {
		return _originalResourceBlockId;
	}

	@Override
	public long getRoleId() {
		return _roleId;
	}

	@Override
	public void setRoleId(long roleId) {
		_columnBitmask |= ROLEID_COLUMN_BITMASK;

		if (!_setOriginalRoleId) {
			_setOriginalRoleId = true;

			_originalRoleId = _roleId;
		}

		_roleId = roleId;
	}

	public long getOriginalRoleId() {
		return _originalRoleId;
	}

	@Override
	public long getActionIds() {
		return _actionIds;
	}

	@Override
	public void setActionIds(long actionIds) {
		_actionIds = actionIds;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), ResourceBlockPermission.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ResourceBlockPermission toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, ResourceBlockPermission>
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
		ResourceBlockPermissionImpl resourceBlockPermissionImpl =
			new ResourceBlockPermissionImpl();

		resourceBlockPermissionImpl.setMvccVersion(getMvccVersion());
		resourceBlockPermissionImpl.setResourceBlockPermissionId(
			getResourceBlockPermissionId());
		resourceBlockPermissionImpl.setCompanyId(getCompanyId());
		resourceBlockPermissionImpl.setResourceBlockId(getResourceBlockId());
		resourceBlockPermissionImpl.setRoleId(getRoleId());
		resourceBlockPermissionImpl.setActionIds(getActionIds());

		resourceBlockPermissionImpl.resetOriginalValues();

		return resourceBlockPermissionImpl;
	}

	@Override
	public int compareTo(ResourceBlockPermission resourceBlockPermission) {
		long primaryKey = resourceBlockPermission.getPrimaryKey();

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

		if (!(obj instanceof ResourceBlockPermission)) {
			return false;
		}

		ResourceBlockPermission resourceBlockPermission =
			(ResourceBlockPermission)obj;

		long primaryKey = resourceBlockPermission.getPrimaryKey();

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
		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl =
			this;

		resourceBlockPermissionModelImpl._originalResourceBlockId =
			resourceBlockPermissionModelImpl._resourceBlockId;

		resourceBlockPermissionModelImpl._setOriginalResourceBlockId = false;

		resourceBlockPermissionModelImpl._originalRoleId =
			resourceBlockPermissionModelImpl._roleId;

		resourceBlockPermissionModelImpl._setOriginalRoleId = false;

		resourceBlockPermissionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<ResourceBlockPermission> toCacheModel() {
		ResourceBlockPermissionCacheModel resourceBlockPermissionCacheModel =
			new ResourceBlockPermissionCacheModel();

		resourceBlockPermissionCacheModel.mvccVersion = getMvccVersion();

		resourceBlockPermissionCacheModel.resourceBlockPermissionId =
			getResourceBlockPermissionId();

		resourceBlockPermissionCacheModel.companyId = getCompanyId();

		resourceBlockPermissionCacheModel.resourceBlockId =
			getResourceBlockId();

		resourceBlockPermissionCacheModel.roleId = getRoleId();

		resourceBlockPermissionCacheModel.actionIds = getActionIds();

		return resourceBlockPermissionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<ResourceBlockPermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<ResourceBlockPermission, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourceBlockPermission, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((ResourceBlockPermission)this));
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
		Map<String, Function<ResourceBlockPermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<ResourceBlockPermission, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourceBlockPermission, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((ResourceBlockPermission)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, ResourceBlockPermission>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _resourceBlockPermissionId;
	private long _companyId;
	private long _resourceBlockId;
	private long _originalResourceBlockId;
	private boolean _setOriginalResourceBlockId;
	private long _roleId;
	private long _originalRoleId;
	private boolean _setOriginalRoleId;
	private long _actionIds;
	private long _columnBitmask;
	private ResourceBlockPermission _escapedModel;

}