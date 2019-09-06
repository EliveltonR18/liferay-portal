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

package com.liferay.portal.lock.model.impl;

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
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.lock.model.LockModel;

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
 * The base model implementation for the Lock service. Represents a row in the &quot;Lock_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>LockModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LockImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LockImpl
 * @generated
 */
public class LockModelImpl extends BaseModelImpl<Lock> implements LockModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a lock model instance should use the <code>Lock</code> interface instead.
	 */
	public static final String TABLE_NAME = "Lock_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"lockId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"className", Types.VARCHAR},
		{"key_", Types.VARCHAR}, {"owner", Types.VARCHAR},
		{"inheritable", Types.BOOLEAN}, {"expirationDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lockId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("owner", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("inheritable", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Lock_ (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,lockId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,className VARCHAR(75) null,key_ VARCHAR(200) null,owner VARCHAR(1024) null,inheritable BOOLEAN,expirationDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Lock_";

	public static final String ORDER_BY_JPQL = " ORDER BY lock.lockId ASC";

	public static final String ORDER_BY_SQL = " ORDER BY Lock_.lockId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.lock.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.portal.lock.model.Lock"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.lock.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.portal.lock.model.Lock"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.lock.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.lock.model.Lock"),
		true);

	public static final long CLASSNAME_COLUMN_BITMASK = 1L;

	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	public static final long EXPIRATIONDATE_COLUMN_BITMASK = 4L;

	public static final long KEY_COLUMN_BITMASK = 8L;

	public static final long UUID_COLUMN_BITMASK = 16L;

	public static final long LOCKID_COLUMN_BITMASK = 32L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.lock.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.portal.lock.model.Lock"));

	public LockModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _lockId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLockId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lockId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Lock.class;
	}

	@Override
	public String getModelClassName() {
		return Lock.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Lock, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Lock, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Lock, Object> attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Lock)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Lock, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Lock, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((Lock)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Lock, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Lock, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Lock>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Lock.class.getClassLoader(), Lock.class, ModelWrapper.class);

		try {
			Constructor<Lock> constructor =
				(Constructor<Lock>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Lock, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Lock, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Lock, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Lock, Object>>();
		Map<String, BiConsumer<Lock, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Lock, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getMvccVersion();
				}

			});
		attributeSetterBiConsumers.put(
			"mvccVersion",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object mvccVersion) {
					lock.setMvccVersion((Long)mvccVersion);
				}

			});
		attributeGetterFunctions.put(
			"uuid",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getUuid();
				}

			});
		attributeSetterBiConsumers.put(
			"uuid",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object uuid) {
					lock.setUuid((String)uuid);
				}

			});
		attributeGetterFunctions.put(
			"lockId",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getLockId();
				}

			});
		attributeSetterBiConsumers.put(
			"lockId",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object lockId) {
					lock.setLockId((Long)lockId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object companyId) {
					lock.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"userId",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getUserId();
				}

			});
		attributeSetterBiConsumers.put(
			"userId",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object userId) {
					lock.setUserId((Long)userId);
				}

			});
		attributeGetterFunctions.put(
			"userName",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getUserName();
				}

			});
		attributeSetterBiConsumers.put(
			"userName",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object userName) {
					lock.setUserName((String)userName);
				}

			});
		attributeGetterFunctions.put(
			"createDate",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getCreateDate();
				}

			});
		attributeSetterBiConsumers.put(
			"createDate",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object createDate) {
					lock.setCreateDate((Date)createDate);
				}

			});
		attributeGetterFunctions.put(
			"className",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getClassName();
				}

			});
		attributeSetterBiConsumers.put(
			"className",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object className) {
					lock.setClassName((String)className);
				}

			});
		attributeGetterFunctions.put(
			"key",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getKey();
				}

			});
		attributeSetterBiConsumers.put(
			"key",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object key) {
					lock.setKey((String)key);
				}

			});
		attributeGetterFunctions.put(
			"owner",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getOwner();
				}

			});
		attributeSetterBiConsumers.put(
			"owner",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object owner) {
					lock.setOwner((String)owner);
				}

			});
		attributeGetterFunctions.put(
			"inheritable",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getInheritable();
				}

			});
		attributeSetterBiConsumers.put(
			"inheritable",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object inheritable) {
					lock.setInheritable((Boolean)inheritable);
				}

			});
		attributeGetterFunctions.put(
			"expirationDate",
			new Function<Lock, Object>() {

				@Override
				public Object apply(Lock lock) {
					return lock.getExpirationDate();
				}

			});
		attributeSetterBiConsumers.put(
			"expirationDate",
			new BiConsumer<Lock, Object>() {

				@Override
				public void accept(Lock lock, Object expirationDate) {
					lock.setExpirationDate((Date)expirationDate);
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
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getLockId() {
		return _lockId;
	}

	@Override
	public void setLockId(long lockId) {
		_lockId = lockId;
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
	public String getClassName() {
		if (_className == null) {
			return "";
		}
		else {
			return _className;
		}
	}

	@Override
	public void setClassName(String className) {
		_columnBitmask |= CLASSNAME_COLUMN_BITMASK;

		if (_originalClassName == null) {
			_originalClassName = _className;
		}

		_className = className;
	}

	public String getOriginalClassName() {
		return GetterUtil.getString(_originalClassName);
	}

	@Override
	public String getKey() {
		if (_key == null) {
			return "";
		}
		else {
			return _key;
		}
	}

	@Override
	public void setKey(String key) {
		_columnBitmask |= KEY_COLUMN_BITMASK;

		if (_originalKey == null) {
			_originalKey = _key;
		}

		_key = key;
	}

	public String getOriginalKey() {
		return GetterUtil.getString(_originalKey);
	}

	@Override
	public String getOwner() {
		if (_owner == null) {
			return "";
		}
		else {
			return _owner;
		}
	}

	@Override
	public void setOwner(String owner) {
		_owner = owner;
	}

	@Override
	public boolean getInheritable() {
		return _inheritable;
	}

	@Override
	public boolean isInheritable() {
		return _inheritable;
	}

	@Override
	public void setInheritable(boolean inheritable) {
		_inheritable = inheritable;
	}

	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		_columnBitmask |= EXPIRATIONDATE_COLUMN_BITMASK;

		if (_originalExpirationDate == null) {
			_originalExpirationDate = _expirationDate;
		}

		_expirationDate = expirationDate;
	}

	public Date getOriginalExpirationDate() {
		return _originalExpirationDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Lock.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Lock toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Lock>
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
		LockImpl lockImpl = new LockImpl();

		lockImpl.setMvccVersion(getMvccVersion());
		lockImpl.setUuid(getUuid());
		lockImpl.setLockId(getLockId());
		lockImpl.setCompanyId(getCompanyId());
		lockImpl.setUserId(getUserId());
		lockImpl.setUserName(getUserName());
		lockImpl.setCreateDate(getCreateDate());
		lockImpl.setClassName(getClassName());
		lockImpl.setKey(getKey());
		lockImpl.setOwner(getOwner());
		lockImpl.setInheritable(isInheritable());
		lockImpl.setExpirationDate(getExpirationDate());

		lockImpl.resetOriginalValues();

		return lockImpl;
	}

	@Override
	public int compareTo(Lock lock) {
		long primaryKey = lock.getPrimaryKey();

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

		if (!(obj instanceof Lock)) {
			return false;
		}

		Lock lock = (Lock)obj;

		long primaryKey = lock.getPrimaryKey();

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
		LockModelImpl lockModelImpl = this;

		lockModelImpl._originalUuid = lockModelImpl._uuid;

		lockModelImpl._originalCompanyId = lockModelImpl._companyId;

		lockModelImpl._setOriginalCompanyId = false;

		lockModelImpl._originalClassName = lockModelImpl._className;

		lockModelImpl._originalKey = lockModelImpl._key;

		lockModelImpl._originalExpirationDate = lockModelImpl._expirationDate;

		lockModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Lock> toCacheModel() {
		LockCacheModel lockCacheModel = new LockCacheModel();

		lockCacheModel.mvccVersion = getMvccVersion();

		lockCacheModel.uuid = getUuid();

		String uuid = lockCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			lockCacheModel.uuid = null;
		}

		lockCacheModel.lockId = getLockId();

		lockCacheModel.companyId = getCompanyId();

		lockCacheModel.userId = getUserId();

		lockCacheModel.userName = getUserName();

		String userName = lockCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			lockCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			lockCacheModel.createDate = createDate.getTime();
		}
		else {
			lockCacheModel.createDate = Long.MIN_VALUE;
		}

		lockCacheModel.className = getClassName();

		String className = lockCacheModel.className;

		if ((className != null) && (className.length() == 0)) {
			lockCacheModel.className = null;
		}

		lockCacheModel.key = getKey();

		String key = lockCacheModel.key;

		if ((key != null) && (key.length() == 0)) {
			lockCacheModel.key = null;
		}

		lockCacheModel.owner = getOwner();

		String owner = lockCacheModel.owner;

		if ((owner != null) && (owner.length() == 0)) {
			lockCacheModel.owner = null;
		}

		lockCacheModel.inheritable = isInheritable();

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			lockCacheModel.expirationDate = expirationDate.getTime();
		}
		else {
			lockCacheModel.expirationDate = Long.MIN_VALUE;
		}

		return lockCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Lock, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Lock, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Lock, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Lock)this));
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
		Map<String, Function<Lock, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Lock, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Lock, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Lock)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Lock>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _lockId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _className;
	private String _originalClassName;
	private String _key;
	private String _originalKey;
	private String _owner;
	private boolean _inheritable;
	private Date _expirationDate;
	private Date _originalExpirationDate;
	private long _columnBitmask;
	private Lock _escapedModel;

}