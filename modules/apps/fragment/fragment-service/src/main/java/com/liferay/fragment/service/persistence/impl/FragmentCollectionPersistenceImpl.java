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

package com.liferay.fragment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.impl.FragmentCollectionImpl;
import com.liferay.fragment.model.impl.FragmentCollectionModelImpl;
import com.liferay.fragment.service.persistence.FragmentCollectionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the fragment collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionPersistence
 * @see com.liferay.fragment.service.persistence.FragmentCollectionUtil
 * @generated
 */
@ProviderType
public class FragmentCollectionPersistenceImpl extends BasePersistenceImpl<FragmentCollection>
	implements FragmentCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FragmentCollectionUtil} to access the fragment collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FragmentCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private final FinderPath _finderPathWithPaginationFindAll = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	private final FinderPath _finderPathWithoutPaginationFindAll = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	private final FinderPath _finderPathCountAll = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	private final FinderPath _finderPathWithPaginationFindByUuid = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	private final FinderPath _finderPathWithoutPaginationFindByUuid = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			FragmentCollectionModelImpl.UUID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	private final FinderPath _finderPathCountByUuid = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the fragment collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid(String uuid, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid(String uuid, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if (!uuid.equals(fragmentCollection.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByUuid_First(String uuid,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByUuid_First(uuid,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUuid_First(String uuid,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByUuid_Last(String uuid,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByUuid_Last(uuid,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUuid_Last(String uuid,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByUuid_PrevAndNext(
		long fragmentCollectionId, String uuid,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		uuid = Objects.toString(uuid, "");

		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, fragmentCollection, uuid,
					orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByUuid_PrevAndNext(session, fragmentCollection, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection getByUuid_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, String uuid,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					fragmentCollection)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FragmentCollection fragmentCollection : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "fragmentCollection.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(fragmentCollection.uuid IS NULL OR fragmentCollection.uuid = '')";
	private final FinderPath _finderPathFetchByUUID_G = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			FragmentCollectionModelImpl.UUID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK);
	private final FinderPath _finderPathCountByUUID_G = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByUUID_G(String uuid, long groupId)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByUUID_G(uuid, groupId);

		if (fragmentCollection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCollectionException(msg.toString());
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByUUID_G,
					finderArgs, this);
		}

		if (result instanceof FragmentCollection) {
			FragmentCollection fragmentCollection = (FragmentCollection)result;

			if (!Objects.equals(uuid, fragmentCollection.getUuid()) ||
					(groupId != fragmentCollection.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<FragmentCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByUUID_G, finderArgs,
						list);
				}
				else {
					FragmentCollection fragmentCollection = list.get(0);

					result = fragmentCollection;

					cacheResult(fragmentCollection);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByUUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (FragmentCollection)result;
		}
	}

	/**
	 * Removes the fragment collection where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the fragment collection that was removed
	 */
	@Override
	public FragmentCollection removeByUUID_G(String uuid, long groupId)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByUUID_G(uuid, groupId);

		return remove(fragmentCollection);
	}

	/**
	 * Returns the number of fragment collections where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "fragmentCollection.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(fragmentCollection.uuid IS NULL OR fragmentCollection.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "fragmentCollection.groupId = ?";
	private final FinderPath _finderPathWithPaginationFindByUuid_C = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	private final FinderPath _finderPathWithoutPaginationFindByUuid_C = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			FragmentCollectionModelImpl.UUID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	private final FinderPath _finderPathCountByUuid_C = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if (!uuid.equals(fragmentCollection.getUuid()) ||
							(companyId != fragmentCollection.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByUuid_C_PrevAndNext(
		long fragmentCollectionId, String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		uuid = Objects.toString(uuid, "");

		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, fragmentCollection,
					uuid, companyId, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByUuid_C_PrevAndNext(session, fragmentCollection,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection getByUuid_C_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, String uuid, long companyId,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					fragmentCollection)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FragmentCollection fragmentCollection : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "fragmentCollection.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(fragmentCollection.uuid IS NULL OR fragmentCollection.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "fragmentCollection.companyId = ?";
	private final FinderPath _finderPathWithPaginationFindByGroupId = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	private final FinderPath _finderPathWithoutPaginationFindByGroupId = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			FragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	private final FinderPath _finderPathCountByGroupId = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentCollection> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if ((groupId != fragmentCollection.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByGroupId_First(groupId,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByGroupId_PrevAndNext(
		long fragmentCollectionId, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, fragmentCollection,
					groupId, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByGroupId_PrevAndNext(session, fragmentCollection,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection getByGroupId_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					fragmentCollection)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentCollection fragmentCollection : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "fragmentCollection.groupId = ?";
	private final FinderPath _finderPathFetchByG_FCK = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_FCK",
			new String[] { Long.class.getName(), String.class.getName() },
			FragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.FRAGMENTCOLLECTIONKEY_COLUMN_BITMASK);
	private final FinderPath _finderPathCountByG_FCK = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCK",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_FCK(long groupId,
		String fragmentCollectionKey) throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_FCK(groupId,
				fragmentCollectionKey);

		if (fragmentCollection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", fragmentCollectionKey=");
			msg.append(fragmentCollectionKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCollectionException(msg.toString());
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_FCK(long groupId,
		String fragmentCollectionKey) {
		return fetchByG_FCK(groupId, fragmentCollectionKey, true);
	}

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_FCK(long groupId,
		String fragmentCollectionKey, boolean retrieveFromCache) {
		fragmentCollectionKey = Objects.toString(fragmentCollectionKey, "");

		Object[] finderArgs = new Object[] { groupId, fragmentCollectionKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByG_FCK, finderArgs,
					this);
		}

		if (result instanceof FragmentCollection) {
			FragmentCollection fragmentCollection = (FragmentCollection)result;

			if ((groupId != fragmentCollection.getGroupId()) ||
					!Objects.equals(fragmentCollectionKey,
						fragmentCollection.getFragmentCollectionKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_FCK_GROUPID_2);

			boolean bindFragmentCollectionKey = false;

			if (fragmentCollectionKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3);
			}
			else {
				bindFragmentCollectionKey = true;

				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindFragmentCollectionKey) {
					qPos.add(fragmentCollectionKey);
				}

				List<FragmentCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByG_FCK, finderArgs,
						list);
				}
				else {
					FragmentCollection fragmentCollection = list.get(0);

					result = fragmentCollection;

					cacheResult(fragmentCollection);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByG_FCK, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (FragmentCollection)result;
		}
	}

	/**
	 * Removes the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the fragment collection that was removed
	 */
	@Override
	public FragmentCollection removeByG_FCK(long groupId,
		String fragmentCollectionKey) throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByG_FCK(groupId,
				fragmentCollectionKey);

		return remove(fragmentCollection);
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63; and fragmentCollectionKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByG_FCK(long groupId, String fragmentCollectionKey) {
		fragmentCollectionKey = Objects.toString(fragmentCollectionKey, "");

		FinderPath finderPath = _finderPathCountByG_FCK;

		Object[] finderArgs = new Object[] { groupId, fragmentCollectionKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_FCK_GROUPID_2);

			boolean bindFragmentCollectionKey = false;

			if (fragmentCollectionKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3);
			}
			else {
				bindFragmentCollectionKey = true;

				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindFragmentCollectionKey) {
					qPos.add(fragmentCollectionKey);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_FCK_GROUPID_2 = "fragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2 = "fragmentCollection.fragmentCollectionKey = ?";
	private static final String _FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3 = "(fragmentCollection.fragmentCollectionKey IS NULL OR fragmentCollection.fragmentCollectionKey = '')";
	private final FinderPath _finderPathWithPaginationFindByG_LikeN = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	private final FinderPath _finderPathWithPaginationCountByG_LikeN = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if ((groupId != fragmentCollection.getGroupId()) ||
							!StringUtil.wildcardMatches(
								fragmentCollection.getName(), name, '_', '%',
								'\\', false)) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_LikeN_First(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_LikeN_First(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByG_LikeN(groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_LikeN_Last(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_LikeN_Last(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByG_LikeN(groupId, name, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByG_LikeN_PrevAndNext(
		long fragmentCollectionId, long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		name = Objects.toString(name, "");

		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session, fragmentCollection,
					groupId, name, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByG_LikeN_PrevAndNext(session, fragmentCollection,
					groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection getByG_LikeN_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					fragmentCollection)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (FragmentCollection fragmentCollection : findByG_LikeN(groupId,
				name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_LikeN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "fragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(fragmentCollection.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(fragmentCollection.name IS NULL OR fragmentCollection.name LIKE '')";

	public FragmentCollectionPersistenceImpl() {
		setModelClass(FragmentCollection.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the fragment collection in the entity cache if it is enabled.
	 *
	 * @param fragmentCollection the fragment collection
	 */
	@Override
	public void cacheResult(FragmentCollection fragmentCollection) {
		entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey(),
			fragmentCollection);

		finderCache.putResult(_finderPathFetchByUUID_G,
			new Object[] {
				fragmentCollection.getUuid(), fragmentCollection.getGroupId()
			}, fragmentCollection);

		finderCache.putResult(_finderPathFetchByG_FCK,
			new Object[] {
				fragmentCollection.getGroupId(),
				fragmentCollection.getFragmentCollectionKey()
			}, fragmentCollection);

		fragmentCollection.resetOriginalValues();
	}

	/**
	 * Caches the fragment collections in the entity cache if it is enabled.
	 *
	 * @param fragmentCollections the fragment collections
	 */
	@Override
	public void cacheResult(List<FragmentCollection> fragmentCollections) {
		for (FragmentCollection fragmentCollection : fragmentCollections) {
			if (entityCache.getResult(
						FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						FragmentCollectionImpl.class,
						fragmentCollection.getPrimaryKey()) == null) {
				cacheResult(fragmentCollection);
			}
			else {
				fragmentCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentCollection fragmentCollection) {
		entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FragmentCollectionModelImpl)fragmentCollection,
			true);
	}

	@Override
	public void clearCache(List<FragmentCollection> fragmentCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey());

			clearUniqueFindersCache((FragmentCollectionModelImpl)fragmentCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentCollectionModelImpl fragmentCollectionModelImpl) {
		Object[] args = new Object[] {
				fragmentCollectionModelImpl.getUuid(),
				fragmentCollectionModelImpl.getGroupId()
			};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByUUID_G, args,
			fragmentCollectionModelImpl, false);

		args = new Object[] {
				fragmentCollectionModelImpl.getGroupId(),
				fragmentCollectionModelImpl.getFragmentCollectionKey()
			};

		finderCache.putResult(_finderPathCountByG_FCK, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByG_FCK, args,
			fragmentCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FragmentCollectionModelImpl fragmentCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getUuid(),
					fragmentCollectionModelImpl.getGroupId()
				};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((fragmentCollectionModelImpl.getColumnBitmask() &
				_finderPathFetchByUUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getOriginalUuid(),
					fragmentCollectionModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getGroupId(),
					fragmentCollectionModelImpl.getFragmentCollectionKey()
				};

			finderCache.removeResult(_finderPathCountByG_FCK, args);
			finderCache.removeResult(_finderPathFetchByG_FCK, args);
		}

		if ((fragmentCollectionModelImpl.getColumnBitmask() &
				_finderPathFetchByG_FCK.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getOriginalGroupId(),
					fragmentCollectionModelImpl.getOriginalFragmentCollectionKey()
				};

			finderCache.removeResult(_finderPathCountByG_FCK, args);
			finderCache.removeResult(_finderPathFetchByG_FCK, args);
		}
	}

	/**
	 * Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	 *
	 * @param fragmentCollectionId the primary key for the new fragment collection
	 * @return the new fragment collection
	 */
	@Override
	public FragmentCollection create(long fragmentCollectionId) {
		FragmentCollection fragmentCollection = new FragmentCollectionImpl();

		fragmentCollection.setNew(true);
		fragmentCollection.setPrimaryKey(fragmentCollectionId);

		String uuid = PortalUUIDUtil.generate();

		fragmentCollection.setUuid(uuid);

		fragmentCollection.setCompanyId(companyProvider.getCompanyId());

		return fragmentCollection;
	}

	/**
	 * Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection that was removed
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection remove(long fragmentCollectionId)
		throws NoSuchCollectionException {
		return remove((Serializable)fragmentCollectionId);
	}

	/**
	 * Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection that was removed
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {
		Session session = null;

		try {
			session = openSession();

			FragmentCollection fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
					primaryKey);

			if (fragmentCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(fragmentCollection);
		}
		catch (NoSuchCollectionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected FragmentCollection removeImpl(
		FragmentCollection fragmentCollection) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentCollection)) {
				fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
						fragmentCollection.getPrimaryKeyObj());
			}

			if (fragmentCollection != null) {
				session.delete(fragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (fragmentCollection != null) {
			clearCache(fragmentCollection);
		}

		return fragmentCollection;
	}

	@Override
	public FragmentCollection updateImpl(FragmentCollection fragmentCollection) {
		boolean isNew = fragmentCollection.isNew();

		if (!(fragmentCollection instanceof FragmentCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(fragmentCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentCollection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentCollection implementation " +
				fragmentCollection.getClass());
		}

		FragmentCollectionModelImpl fragmentCollectionModelImpl = (FragmentCollectionModelImpl)fragmentCollection;

		if (Validator.isNull(fragmentCollection.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fragmentCollection.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (fragmentCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentCollection.setCreateDate(now);
			}
			else {
				fragmentCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!fragmentCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentCollection.setModifiedDate(now);
			}
			else {
				fragmentCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (fragmentCollection.isNew()) {
				session.save(fragmentCollection);

				fragmentCollection.setNew(false);
			}
			else {
				fragmentCollection = (FragmentCollection)session.merge(fragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FragmentCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { fragmentCollectionModelImpl.getUuid() };

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
				args);

			args = new Object[] {
					fragmentCollectionModelImpl.getUuid(),
					fragmentCollectionModelImpl.getCompanyId()
				};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
				args);

			args = new Object[] { fragmentCollectionModelImpl.getGroupId() };

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((fragmentCollectionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentCollectionModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);

				args = new Object[] { fragmentCollectionModelImpl.getUuid() };

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);
			}

			if ((fragmentCollectionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentCollectionModelImpl.getOriginalUuid(),
						fragmentCollectionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);

				args = new Object[] {
						fragmentCollectionModelImpl.getUuid(),
						fragmentCollectionModelImpl.getCompanyId()
					};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);
			}

			if ((fragmentCollectionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentCollectionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { fragmentCollectionModelImpl.getGroupId() };

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}
		}

		entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey(),
			fragmentCollection, false);

		clearUniqueFindersCache(fragmentCollectionModelImpl, false);
		cacheUniqueFindersCache(fragmentCollectionModelImpl);

		fragmentCollection.resetOriginalValues();

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByPrimaryKey(primaryKey);

		if (fragmentCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection findByPrimaryKey(long fragmentCollectionId)
		throws NoSuchCollectionException {
		return findByPrimaryKey((Serializable)fragmentCollectionId);
	}

	/**
	 * Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				FragmentCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FragmentCollection fragmentCollection = (FragmentCollection)serializable;

		if (fragmentCollection == null) {
			Session session = null;

			try {
				session = openSession();

				fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
						primaryKey);

				if (fragmentCollection != null) {
					cacheResult(fragmentCollection);
				}
				else {
					entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						FragmentCollectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection fetchByPrimaryKey(long fragmentCollectionId) {
		return fetchByPrimaryKey((Serializable)fragmentCollectionId);
	}

	@Override
	public Map<Serializable, FragmentCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentCollection> map = new HashMap<Serializable, FragmentCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentCollection fragmentCollection = fetchByPrimaryKey(primaryKey);

			if (fragmentCollection != null) {
				map.put(primaryKey, fragmentCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FragmentCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (FragmentCollection fragmentCollection : (List<FragmentCollection>)q.list()) {
				map.put(fragmentCollection.getPrimaryKeyObj(),
					fragmentCollection);

				cacheResult(fragmentCollection);

				uncachedPrimaryKeys.remove(fragmentCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the fragment collections.
	 *
	 * @return the fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRAGMENTCOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTCOLLECTION;

				if (pagination) {
					sql = sql.concat(FragmentCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the fragment collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentCollection fragmentCollection : findAll()) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections.
	 *
	 * @return the number of fragment collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRAGMENTCOLLECTION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FragmentCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fragment collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FragmentCollectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection";
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION_WHERE_PKS_IN = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection WHERE fragmentCollectionId IN (";
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION_WHERE = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _SQL_COUNT_FRAGMENTCOLLECTION = "SELECT COUNT(fragmentCollection) FROM FragmentCollection fragmentCollection";
	private static final String _SQL_COUNT_FRAGMENTCOLLECTION_WHERE = "SELECT COUNT(fragmentCollection) FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FragmentCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FragmentCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FragmentCollectionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}