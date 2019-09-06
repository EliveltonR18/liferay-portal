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

package com.liferay.changeset.model.impl;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetCollectionModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the ChangesetCollection service. Represents a row in the &quot;ChangesetCollection&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>ChangesetCollectionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ChangesetCollectionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionImpl
 * @generated
 */
public class ChangesetCollectionModelImpl
	extends BaseModelImpl<ChangesetCollection>
	implements ChangesetCollectionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a changeset collection model instance should use the <code>ChangesetCollection</code> interface instead.
	 */
	public static final String TABLE_NAME = "ChangesetCollection";

	public static final Object[][] TABLE_COLUMNS = {
		{"changesetCollectionId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("changesetCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table ChangesetCollection (changesetCollectionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
		"drop table ChangesetCollection";

	public static final String ORDER_BY_JPQL =
		" ORDER BY changesetCollection.changesetCollectionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY ChangesetCollection.changesetCollectionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.changeset.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.changeset.model.ChangesetCollection"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.changeset.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.changeset.model.ChangesetCollection"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.changeset.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.changeset.model.ChangesetCollection"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long NAME_COLUMN_BITMASK = 4L;

	public static final long USERID_COLUMN_BITMASK = 8L;

	public static final long CHANGESETCOLLECTIONID_COLUMN_BITMASK = 16L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.changeset.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.changeset.model.ChangesetCollection"));

	public ChangesetCollectionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _changesetCollectionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setChangesetCollectionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changesetCollectionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ChangesetCollection.class;
	}

	@Override
	public String getModelClassName() {
		return ChangesetCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<ChangesetCollection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<ChangesetCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ChangesetCollection, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((ChangesetCollection)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<ChangesetCollection, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<ChangesetCollection, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(ChangesetCollection)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<ChangesetCollection, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<ChangesetCollection, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, ChangesetCollection>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			ChangesetCollection.class.getClassLoader(),
			ChangesetCollection.class, ModelWrapper.class);

		try {
			Constructor<ChangesetCollection> constructor =
				(Constructor<ChangesetCollection>)proxyClass.getConstructor(
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

	private static final Map<String, Function<ChangesetCollection, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<ChangesetCollection, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<ChangesetCollection, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<ChangesetCollection, Object>>();
		Map<String, BiConsumer<ChangesetCollection, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<ChangesetCollection, ?>>();

		attributeGetterFunctions.put(
			"changesetCollectionId",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getChangesetCollectionId();
				}

			});
		attributeSetterBiConsumers.put(
			"changesetCollectionId",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection,
					Object changesetCollectionId) {

					changesetCollection.setChangesetCollectionId(
						(Long)changesetCollectionId);
				}

			});
		attributeGetterFunctions.put(
			"groupId",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getGroupId();
				}

			});
		attributeSetterBiConsumers.put(
			"groupId",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection, Object groupId) {

					changesetCollection.setGroupId((Long)groupId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection, Object companyId) {

					changesetCollection.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"userId",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getUserId();
				}

			});
		attributeSetterBiConsumers.put(
			"userId",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection, Object userId) {

					changesetCollection.setUserId((Long)userId);
				}

			});
		attributeGetterFunctions.put(
			"userName",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getUserName();
				}

			});
		attributeSetterBiConsumers.put(
			"userName",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection, Object userName) {

					changesetCollection.setUserName((String)userName);
				}

			});
		attributeGetterFunctions.put(
			"createDate",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getCreateDate();
				}

			});
		attributeSetterBiConsumers.put(
			"createDate",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection,
					Object createDate) {

					changesetCollection.setCreateDate((Date)createDate);
				}

			});
		attributeGetterFunctions.put(
			"modifiedDate",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getModifiedDate();
				}

			});
		attributeSetterBiConsumers.put(
			"modifiedDate",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection,
					Object modifiedDate) {

					changesetCollection.setModifiedDate((Date)modifiedDate);
				}

			});
		attributeGetterFunctions.put(
			"name",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getName();
				}

			});
		attributeSetterBiConsumers.put(
			"name",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection, Object name) {

					changesetCollection.setName((String)name);
				}

			});
		attributeGetterFunctions.put(
			"description",
			new Function<ChangesetCollection, Object>() {

				@Override
				public Object apply(ChangesetCollection changesetCollection) {
					return changesetCollection.getDescription();
				}

			});
		attributeSetterBiConsumers.put(
			"description",
			new BiConsumer<ChangesetCollection, Object>() {

				@Override
				public void accept(
					ChangesetCollection changesetCollection,
					Object description) {

					changesetCollection.setDescription((String)description);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getChangesetCollectionId() {
		return _changesetCollectionId;
	}

	@Override
	public void setChangesetCollectionId(long changesetCollectionId) {
		_changesetCollectionId = changesetCollectionId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), ChangesetCollection.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ChangesetCollection toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, ChangesetCollection>
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
		ChangesetCollectionImpl changesetCollectionImpl =
			new ChangesetCollectionImpl();

		changesetCollectionImpl.setChangesetCollectionId(
			getChangesetCollectionId());
		changesetCollectionImpl.setGroupId(getGroupId());
		changesetCollectionImpl.setCompanyId(getCompanyId());
		changesetCollectionImpl.setUserId(getUserId());
		changesetCollectionImpl.setUserName(getUserName());
		changesetCollectionImpl.setCreateDate(getCreateDate());
		changesetCollectionImpl.setModifiedDate(getModifiedDate());
		changesetCollectionImpl.setName(getName());
		changesetCollectionImpl.setDescription(getDescription());

		changesetCollectionImpl.resetOriginalValues();

		return changesetCollectionImpl;
	}

	@Override
	public int compareTo(ChangesetCollection changesetCollection) {
		long primaryKey = changesetCollection.getPrimaryKey();

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

		if (!(obj instanceof ChangesetCollection)) {
			return false;
		}

		ChangesetCollection changesetCollection = (ChangesetCollection)obj;

		long primaryKey = changesetCollection.getPrimaryKey();

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
		ChangesetCollectionModelImpl changesetCollectionModelImpl = this;

		changesetCollectionModelImpl._originalGroupId =
			changesetCollectionModelImpl._groupId;

		changesetCollectionModelImpl._setOriginalGroupId = false;

		changesetCollectionModelImpl._originalCompanyId =
			changesetCollectionModelImpl._companyId;

		changesetCollectionModelImpl._setOriginalCompanyId = false;

		changesetCollectionModelImpl._originalUserId =
			changesetCollectionModelImpl._userId;

		changesetCollectionModelImpl._setOriginalUserId = false;

		changesetCollectionModelImpl._setModifiedDate = false;

		changesetCollectionModelImpl._originalName =
			changesetCollectionModelImpl._name;

		changesetCollectionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<ChangesetCollection> toCacheModel() {
		ChangesetCollectionCacheModel changesetCollectionCacheModel =
			new ChangesetCollectionCacheModel();

		changesetCollectionCacheModel.changesetCollectionId =
			getChangesetCollectionId();

		changesetCollectionCacheModel.groupId = getGroupId();

		changesetCollectionCacheModel.companyId = getCompanyId();

		changesetCollectionCacheModel.userId = getUserId();

		changesetCollectionCacheModel.userName = getUserName();

		String userName = changesetCollectionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			changesetCollectionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			changesetCollectionCacheModel.createDate = createDate.getTime();
		}
		else {
			changesetCollectionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			changesetCollectionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			changesetCollectionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		changesetCollectionCacheModel.name = getName();

		String name = changesetCollectionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			changesetCollectionCacheModel.name = null;
		}

		changesetCollectionCacheModel.description = getDescription();

		String description = changesetCollectionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			changesetCollectionCacheModel.description = null;
		}

		return changesetCollectionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<ChangesetCollection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<ChangesetCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ChangesetCollection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((ChangesetCollection)this));
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
		Map<String, Function<ChangesetCollection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<ChangesetCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ChangesetCollection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((ChangesetCollection)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, ChangesetCollection>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _changesetCollectionId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private String _description;
	private long _columnBitmask;
	private ChangesetCollection _escapedModel;

}