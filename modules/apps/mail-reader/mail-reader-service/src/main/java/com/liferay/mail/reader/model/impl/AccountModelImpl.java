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

package com.liferay.mail.reader.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.model.AccountModel;
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
 * The base model implementation for the Account service. Represents a row in the &quot;Mail_Account&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>AccountModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AccountImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountImpl
 * @generated
 */
public class AccountModelImpl
	extends BaseModelImpl<Account> implements AccountModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account model instance should use the <code>Account</code> interface instead.
	 */
	public static final String TABLE_NAME = "Mail_Account";

	public static final Object[][] TABLE_COLUMNS = {
		{"accountId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"address", Types.VARCHAR}, {"personalName", Types.VARCHAR},
		{"protocol", Types.VARCHAR}, {"incomingHostName", Types.VARCHAR},
		{"incomingPort", Types.INTEGER}, {"incomingSecure", Types.BOOLEAN},
		{"outgoingHostName", Types.VARCHAR}, {"outgoingPort", Types.INTEGER},
		{"outgoingSecure", Types.BOOLEAN}, {"login", Types.VARCHAR},
		{"password_", Types.VARCHAR}, {"savePassword", Types.BOOLEAN},
		{"signature", Types.VARCHAR}, {"useSignature", Types.BOOLEAN},
		{"folderPrefix", Types.VARCHAR}, {"inboxFolderId", Types.BIGINT},
		{"draftFolderId", Types.BIGINT}, {"sentFolderId", Types.BIGINT},
		{"trashFolderId", Types.BIGINT}, {"defaultSender", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("accountId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("address", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("personalName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("protocol", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("incomingHostName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("incomingPort", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("incomingSecure", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("outgoingHostName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("outgoingPort", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("outgoingSecure", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("login", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("password_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("savePassword", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("signature", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("useSignature", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("folderPrefix", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("inboxFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("draftFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("sentFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("trashFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("defaultSender", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Mail_Account (accountId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,address VARCHAR(75) null,personalName VARCHAR(75) null,protocol VARCHAR(75) null,incomingHostName VARCHAR(75) null,incomingPort INTEGER,incomingSecure BOOLEAN,outgoingHostName VARCHAR(75) null,outgoingPort INTEGER,outgoingSecure BOOLEAN,login VARCHAR(75) null,password_ VARCHAR(75) null,savePassword BOOLEAN,signature VARCHAR(75) null,useSignature BOOLEAN,folderPrefix VARCHAR(75) null,inboxFolderId LONG,draftFolderId LONG,sentFolderId LONG,trashFolderId LONG,defaultSender BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Mail_Account";

	public static final String ORDER_BY_JPQL = " ORDER BY account.address ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Mail_Account.address ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.mail.reader.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.mail.reader.model.Account"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.mail.reader.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.mail.reader.model.Account"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.mail.reader.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.mail.reader.model.Account"),
		true);

	public static final long ADDRESS_COLUMN_BITMASK = 1L;

	public static final long USERID_COLUMN_BITMASK = 2L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.mail.reader.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.mail.reader.model.Account"));

	public AccountModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _accountId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAccountId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _accountId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Account.class;
	}

	@Override
	public String getModelClassName() {
		return Account.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Account, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Account, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Account, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Account)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Account, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Account, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Account)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Account, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Account, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Account>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Account.class.getClassLoader(), Account.class, ModelWrapper.class);

		try {
			Constructor<Account> constructor =
				(Constructor<Account>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Account, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Account, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Account, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Account, Object>>();
		Map<String, BiConsumer<Account, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Account, ?>>();

		attributeGetterFunctions.put(
			"accountId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getAccountId();
				}

			});
		attributeSetterBiConsumers.put(
			"accountId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object accountId) {
					account.setAccountId((Long)accountId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object companyId) {
					account.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"userId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getUserId();
				}

			});
		attributeSetterBiConsumers.put(
			"userId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object userId) {
					account.setUserId((Long)userId);
				}

			});
		attributeGetterFunctions.put(
			"userName",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getUserName();
				}

			});
		attributeSetterBiConsumers.put(
			"userName",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object userName) {
					account.setUserName((String)userName);
				}

			});
		attributeGetterFunctions.put(
			"createDate",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getCreateDate();
				}

			});
		attributeSetterBiConsumers.put(
			"createDate",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object createDate) {
					account.setCreateDate((Date)createDate);
				}

			});
		attributeGetterFunctions.put(
			"modifiedDate",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getModifiedDate();
				}

			});
		attributeSetterBiConsumers.put(
			"modifiedDate",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object modifiedDate) {
					account.setModifiedDate((Date)modifiedDate);
				}

			});
		attributeGetterFunctions.put(
			"address",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getAddress();
				}

			});
		attributeSetterBiConsumers.put(
			"address",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object address) {
					account.setAddress((String)address);
				}

			});
		attributeGetterFunctions.put(
			"personalName",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getPersonalName();
				}

			});
		attributeSetterBiConsumers.put(
			"personalName",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object personalName) {
					account.setPersonalName((String)personalName);
				}

			});
		attributeGetterFunctions.put(
			"protocol",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getProtocol();
				}

			});
		attributeSetterBiConsumers.put(
			"protocol",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object protocol) {
					account.setProtocol((String)protocol);
				}

			});
		attributeGetterFunctions.put(
			"incomingHostName",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getIncomingHostName();
				}

			});
		attributeSetterBiConsumers.put(
			"incomingHostName",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object incomingHostName) {
					account.setIncomingHostName((String)incomingHostName);
				}

			});
		attributeGetterFunctions.put(
			"incomingPort",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getIncomingPort();
				}

			});
		attributeSetterBiConsumers.put(
			"incomingPort",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object incomingPort) {
					account.setIncomingPort((Integer)incomingPort);
				}

			});
		attributeGetterFunctions.put(
			"incomingSecure",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getIncomingSecure();
				}

			});
		attributeSetterBiConsumers.put(
			"incomingSecure",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object incomingSecure) {
					account.setIncomingSecure((Boolean)incomingSecure);
				}

			});
		attributeGetterFunctions.put(
			"outgoingHostName",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getOutgoingHostName();
				}

			});
		attributeSetterBiConsumers.put(
			"outgoingHostName",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object outgoingHostName) {
					account.setOutgoingHostName((String)outgoingHostName);
				}

			});
		attributeGetterFunctions.put(
			"outgoingPort",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getOutgoingPort();
				}

			});
		attributeSetterBiConsumers.put(
			"outgoingPort",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object outgoingPort) {
					account.setOutgoingPort((Integer)outgoingPort);
				}

			});
		attributeGetterFunctions.put(
			"outgoingSecure",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getOutgoingSecure();
				}

			});
		attributeSetterBiConsumers.put(
			"outgoingSecure",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object outgoingSecure) {
					account.setOutgoingSecure((Boolean)outgoingSecure);
				}

			});
		attributeGetterFunctions.put(
			"login",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getLogin();
				}

			});
		attributeSetterBiConsumers.put(
			"login",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object login) {
					account.setLogin((String)login);
				}

			});
		attributeGetterFunctions.put(
			"password",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getPassword();
				}

			});
		attributeSetterBiConsumers.put(
			"password",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object password) {
					account.setPassword((String)password);
				}

			});
		attributeGetterFunctions.put(
			"savePassword",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getSavePassword();
				}

			});
		attributeSetterBiConsumers.put(
			"savePassword",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object savePassword) {
					account.setSavePassword((Boolean)savePassword);
				}

			});
		attributeGetterFunctions.put(
			"signature",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getSignature();
				}

			});
		attributeSetterBiConsumers.put(
			"signature",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object signature) {
					account.setSignature((String)signature);
				}

			});
		attributeGetterFunctions.put(
			"useSignature",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getUseSignature();
				}

			});
		attributeSetterBiConsumers.put(
			"useSignature",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object useSignature) {
					account.setUseSignature((Boolean)useSignature);
				}

			});
		attributeGetterFunctions.put(
			"folderPrefix",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getFolderPrefix();
				}

			});
		attributeSetterBiConsumers.put(
			"folderPrefix",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object folderPrefix) {
					account.setFolderPrefix((String)folderPrefix);
				}

			});
		attributeGetterFunctions.put(
			"inboxFolderId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getInboxFolderId();
				}

			});
		attributeSetterBiConsumers.put(
			"inboxFolderId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object inboxFolderId) {
					account.setInboxFolderId((Long)inboxFolderId);
				}

			});
		attributeGetterFunctions.put(
			"draftFolderId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getDraftFolderId();
				}

			});
		attributeSetterBiConsumers.put(
			"draftFolderId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object draftFolderId) {
					account.setDraftFolderId((Long)draftFolderId);
				}

			});
		attributeGetterFunctions.put(
			"sentFolderId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getSentFolderId();
				}

			});
		attributeSetterBiConsumers.put(
			"sentFolderId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object sentFolderId) {
					account.setSentFolderId((Long)sentFolderId);
				}

			});
		attributeGetterFunctions.put(
			"trashFolderId",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getTrashFolderId();
				}

			});
		attributeSetterBiConsumers.put(
			"trashFolderId",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object trashFolderId) {
					account.setTrashFolderId((Long)trashFolderId);
				}

			});
		attributeGetterFunctions.put(
			"defaultSender",
			new Function<Account, Object>() {

				@Override
				public Object apply(Account account) {
					return account.getDefaultSender();
				}

			});
		attributeSetterBiConsumers.put(
			"defaultSender",
			new BiConsumer<Account, Object>() {

				@Override
				public void accept(Account account, Object defaultSender) {
					account.setDefaultSender((Boolean)defaultSender);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getAccountId() {
		return _accountId;
	}

	@Override
	public void setAccountId(long accountId) {
		_accountId = accountId;
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
	public String getAddress() {
		if (_address == null) {
			return "";
		}
		else {
			return _address;
		}
	}

	@Override
	public void setAddress(String address) {
		_columnBitmask = -1L;

		if (_originalAddress == null) {
			_originalAddress = _address;
		}

		_address = address;
	}

	public String getOriginalAddress() {
		return GetterUtil.getString(_originalAddress);
	}

	@Override
	public String getPersonalName() {
		if (_personalName == null) {
			return "";
		}
		else {
			return _personalName;
		}
	}

	@Override
	public void setPersonalName(String personalName) {
		_personalName = personalName;
	}

	@Override
	public String getProtocol() {
		if (_protocol == null) {
			return "";
		}
		else {
			return _protocol;
		}
	}

	@Override
	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	@Override
	public String getIncomingHostName() {
		if (_incomingHostName == null) {
			return "";
		}
		else {
			return _incomingHostName;
		}
	}

	@Override
	public void setIncomingHostName(String incomingHostName) {
		_incomingHostName = incomingHostName;
	}

	@Override
	public int getIncomingPort() {
		return _incomingPort;
	}

	@Override
	public void setIncomingPort(int incomingPort) {
		_incomingPort = incomingPort;
	}

	@Override
	public boolean getIncomingSecure() {
		return _incomingSecure;
	}

	@Override
	public boolean isIncomingSecure() {
		return _incomingSecure;
	}

	@Override
	public void setIncomingSecure(boolean incomingSecure) {
		_incomingSecure = incomingSecure;
	}

	@Override
	public String getOutgoingHostName() {
		if (_outgoingHostName == null) {
			return "";
		}
		else {
			return _outgoingHostName;
		}
	}

	@Override
	public void setOutgoingHostName(String outgoingHostName) {
		_outgoingHostName = outgoingHostName;
	}

	@Override
	public int getOutgoingPort() {
		return _outgoingPort;
	}

	@Override
	public void setOutgoingPort(int outgoingPort) {
		_outgoingPort = outgoingPort;
	}

	@Override
	public boolean getOutgoingSecure() {
		return _outgoingSecure;
	}

	@Override
	public boolean isOutgoingSecure() {
		return _outgoingSecure;
	}

	@Override
	public void setOutgoingSecure(boolean outgoingSecure) {
		_outgoingSecure = outgoingSecure;
	}

	@Override
	public String getLogin() {
		if (_login == null) {
			return "";
		}
		else {
			return _login;
		}
	}

	@Override
	public void setLogin(String login) {
		_login = login;
	}

	@Override
	public String getPassword() {
		if (_password == null) {
			return "";
		}
		else {
			return _password;
		}
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public boolean getSavePassword() {
		return _savePassword;
	}

	@Override
	public boolean isSavePassword() {
		return _savePassword;
	}

	@Override
	public void setSavePassword(boolean savePassword) {
		_savePassword = savePassword;
	}

	@Override
	public String getSignature() {
		if (_signature == null) {
			return "";
		}
		else {
			return _signature;
		}
	}

	@Override
	public void setSignature(String signature) {
		_signature = signature;
	}

	@Override
	public boolean getUseSignature() {
		return _useSignature;
	}

	@Override
	public boolean isUseSignature() {
		return _useSignature;
	}

	@Override
	public void setUseSignature(boolean useSignature) {
		_useSignature = useSignature;
	}

	@Override
	public String getFolderPrefix() {
		if (_folderPrefix == null) {
			return "";
		}
		else {
			return _folderPrefix;
		}
	}

	@Override
	public void setFolderPrefix(String folderPrefix) {
		_folderPrefix = folderPrefix;
	}

	@Override
	public long getInboxFolderId() {
		return _inboxFolderId;
	}

	@Override
	public void setInboxFolderId(long inboxFolderId) {
		_inboxFolderId = inboxFolderId;
	}

	@Override
	public long getDraftFolderId() {
		return _draftFolderId;
	}

	@Override
	public void setDraftFolderId(long draftFolderId) {
		_draftFolderId = draftFolderId;
	}

	@Override
	public long getSentFolderId() {
		return _sentFolderId;
	}

	@Override
	public void setSentFolderId(long sentFolderId) {
		_sentFolderId = sentFolderId;
	}

	@Override
	public long getTrashFolderId() {
		return _trashFolderId;
	}

	@Override
	public void setTrashFolderId(long trashFolderId) {
		_trashFolderId = trashFolderId;
	}

	@Override
	public boolean getDefaultSender() {
		return _defaultSender;
	}

	@Override
	public boolean isDefaultSender() {
		return _defaultSender;
	}

	@Override
	public void setDefaultSender(boolean defaultSender) {
		_defaultSender = defaultSender;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Account.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Account toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Account>
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
		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setAccountId(getAccountId());
		accountImpl.setCompanyId(getCompanyId());
		accountImpl.setUserId(getUserId());
		accountImpl.setUserName(getUserName());
		accountImpl.setCreateDate(getCreateDate());
		accountImpl.setModifiedDate(getModifiedDate());
		accountImpl.setAddress(getAddress());
		accountImpl.setPersonalName(getPersonalName());
		accountImpl.setProtocol(getProtocol());
		accountImpl.setIncomingHostName(getIncomingHostName());
		accountImpl.setIncomingPort(getIncomingPort());
		accountImpl.setIncomingSecure(isIncomingSecure());
		accountImpl.setOutgoingHostName(getOutgoingHostName());
		accountImpl.setOutgoingPort(getOutgoingPort());
		accountImpl.setOutgoingSecure(isOutgoingSecure());
		accountImpl.setLogin(getLogin());
		accountImpl.setPassword(getPassword());
		accountImpl.setSavePassword(isSavePassword());
		accountImpl.setSignature(getSignature());
		accountImpl.setUseSignature(isUseSignature());
		accountImpl.setFolderPrefix(getFolderPrefix());
		accountImpl.setInboxFolderId(getInboxFolderId());
		accountImpl.setDraftFolderId(getDraftFolderId());
		accountImpl.setSentFolderId(getSentFolderId());
		accountImpl.setTrashFolderId(getTrashFolderId());
		accountImpl.setDefaultSender(isDefaultSender());

		accountImpl.resetOriginalValues();

		return accountImpl;
	}

	@Override
	public int compareTo(Account account) {
		int value = 0;

		value = getAddress().compareTo(account.getAddress());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Account)) {
			return false;
		}

		Account account = (Account)obj;

		long primaryKey = account.getPrimaryKey();

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
		AccountModelImpl accountModelImpl = this;

		accountModelImpl._originalUserId = accountModelImpl._userId;

		accountModelImpl._setOriginalUserId = false;

		accountModelImpl._setModifiedDate = false;

		accountModelImpl._originalAddress = accountModelImpl._address;

		accountModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Account> toCacheModel() {
		AccountCacheModel accountCacheModel = new AccountCacheModel();

		accountCacheModel.accountId = getAccountId();

		accountCacheModel.companyId = getCompanyId();

		accountCacheModel.userId = getUserId();

		accountCacheModel.userName = getUserName();

		String userName = accountCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			accountCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			accountCacheModel.createDate = createDate.getTime();
		}
		else {
			accountCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			accountCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			accountCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		accountCacheModel.address = getAddress();

		String address = accountCacheModel.address;

		if ((address != null) && (address.length() == 0)) {
			accountCacheModel.address = null;
		}

		accountCacheModel.personalName = getPersonalName();

		String personalName = accountCacheModel.personalName;

		if ((personalName != null) && (personalName.length() == 0)) {
			accountCacheModel.personalName = null;
		}

		accountCacheModel.protocol = getProtocol();

		String protocol = accountCacheModel.protocol;

		if ((protocol != null) && (protocol.length() == 0)) {
			accountCacheModel.protocol = null;
		}

		accountCacheModel.incomingHostName = getIncomingHostName();

		String incomingHostName = accountCacheModel.incomingHostName;

		if ((incomingHostName != null) && (incomingHostName.length() == 0)) {
			accountCacheModel.incomingHostName = null;
		}

		accountCacheModel.incomingPort = getIncomingPort();

		accountCacheModel.incomingSecure = isIncomingSecure();

		accountCacheModel.outgoingHostName = getOutgoingHostName();

		String outgoingHostName = accountCacheModel.outgoingHostName;

		if ((outgoingHostName != null) && (outgoingHostName.length() == 0)) {
			accountCacheModel.outgoingHostName = null;
		}

		accountCacheModel.outgoingPort = getOutgoingPort();

		accountCacheModel.outgoingSecure = isOutgoingSecure();

		accountCacheModel.login = getLogin();

		String login = accountCacheModel.login;

		if ((login != null) && (login.length() == 0)) {
			accountCacheModel.login = null;
		}

		accountCacheModel.password = getPassword();

		String password = accountCacheModel.password;

		if ((password != null) && (password.length() == 0)) {
			accountCacheModel.password = null;
		}

		accountCacheModel.savePassword = isSavePassword();

		accountCacheModel.signature = getSignature();

		String signature = accountCacheModel.signature;

		if ((signature != null) && (signature.length() == 0)) {
			accountCacheModel.signature = null;
		}

		accountCacheModel.useSignature = isUseSignature();

		accountCacheModel.folderPrefix = getFolderPrefix();

		String folderPrefix = accountCacheModel.folderPrefix;

		if ((folderPrefix != null) && (folderPrefix.length() == 0)) {
			accountCacheModel.folderPrefix = null;
		}

		accountCacheModel.inboxFolderId = getInboxFolderId();

		accountCacheModel.draftFolderId = getDraftFolderId();

		accountCacheModel.sentFolderId = getSentFolderId();

		accountCacheModel.trashFolderId = getTrashFolderId();

		accountCacheModel.defaultSender = isDefaultSender();

		return accountCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Account, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Account, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Account, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Account)this));
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
		Map<String, Function<Account, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Account, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Account, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Account)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Account>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _accountId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _address;
	private String _originalAddress;
	private String _personalName;
	private String _protocol;
	private String _incomingHostName;
	private int _incomingPort;
	private boolean _incomingSecure;
	private String _outgoingHostName;
	private int _outgoingPort;
	private boolean _outgoingSecure;
	private String _login;
	private String _password;
	private boolean _savePassword;
	private String _signature;
	private boolean _useSignature;
	private String _folderPrefix;
	private long _inboxFolderId;
	private long _draftFolderId;
	private long _sentFolderId;
	private long _trashFolderId;
	private boolean _defaultSender;
	private long _columnBitmask;
	private Account _escapedModel;

}