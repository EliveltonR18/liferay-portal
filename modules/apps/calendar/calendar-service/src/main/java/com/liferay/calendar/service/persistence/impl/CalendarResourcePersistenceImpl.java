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

package com.liferay.calendar.service.persistence.impl;

import com.liferay.calendar.exception.NoSuchResourceException;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.model.impl.CalendarResourceImpl;
import com.liferay.calendar.model.impl.CalendarResourceModelImpl;
import com.liferay.calendar.service.persistence.CalendarResourcePersistence;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
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

import java.util.Arrays;
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
 * The persistence implementation for the calendar resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Lundgren
 * @generated
 */
public class CalendarResourcePersistenceImpl
	extends BasePersistenceImpl<CalendarResource>
	implements CalendarResourcePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CalendarResourceUtil</code> to access the calendar resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CalendarResourceImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the calendar resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if (!uuid.equals(calendarResource.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
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
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByUuid_First(
			String uuid, OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByUuid_First(
			uuid, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUuid_First(
		String uuid, OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByUuid_Last(
			String uuid, OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByUuid_Last(
			uuid, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUuid_Last(
		String uuid, OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where uuid = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByUuid_PrevAndNext(
			long calendarResourceId, String uuid,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		uuid = Objects.toString(uuid, "");

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, calendarResource, uuid, orderByComparator, true);

			array[1] = calendarResource;

			array[2] = getByUuid_PrevAndNext(
				session, calendarResource, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByUuid_PrevAndNext(
		Session session, CalendarResource calendarResource, String uuid,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CalendarResource calendarResource :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"calendarResource.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(calendarResource.uuid IS NULL OR calendarResource.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the calendar resource where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchResourceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByUUID_G(String uuid, long groupId)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByUUID_G(uuid, groupId);

		if (calendarResource == null) {
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

			throw new NoSuchResourceException(msg.toString());
		}

		return calendarResource;
	}

	/**
	 * Returns the calendar resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the calendar resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof CalendarResource) {
			CalendarResource calendarResource = (CalendarResource)result;

			if (!Objects.equals(uuid, calendarResource.getUuid()) ||
				(groupId != calendarResource.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

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

				List<CalendarResource> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CalendarResource calendarResource = list.get(0);

					result = calendarResource;

					cacheResult(calendarResource);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (CalendarResource)result;
		}
	}

	/**
	 * Removes the calendar resource where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the calendar resource that was removed
	 */
	@Override
	public CalendarResource removeByUUID_G(String uuid, long groupId)
		throws NoSuchResourceException {

		CalendarResource calendarResource = findByUUID_G(uuid, groupId);

		return remove(calendarResource);
	}

	/**
	 * Returns the number of calendar resources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"calendarResource.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(calendarResource.uuid IS NULL OR calendarResource.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"calendarResource.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the calendar resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if (!uuid.equals(calendarResource.getUuid()) ||
						(companyId != calendarResource.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

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
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
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
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByUuid_C_PrevAndNext(
			long calendarResourceId, String uuid, long companyId,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		uuid = Objects.toString(uuid, "");

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, calendarResource, uuid, companyId, orderByComparator,
				true);

			array[1] = calendarResource;

			array[2] = getByUuid_C_PrevAndNext(
				session, calendarResource, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByUuid_C_PrevAndNext(
		Session session, CalendarResource calendarResource, String uuid,
		long companyId, OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

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
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CalendarResource calendarResource :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"calendarResource.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(calendarResource.uuid IS NULL OR calendarResource.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"calendarResource.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the calendar resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if ((groupId != calendarResource.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByGroupId_First(
			long groupId, OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByGroupId_First(
			groupId, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByGroupId_First(
		long groupId, OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByGroupId_Last(
			long groupId, OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByGroupId_Last(
		long groupId, OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where groupId = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByGroupId_PrevAndNext(
			long calendarResourceId, long groupId,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, calendarResource, groupId, orderByComparator, true);

			array[1] = calendarResource;

			array[2] = getByGroupId_PrevAndNext(
				session, calendarResource, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByGroupId_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the calendar resources that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CalendarResource>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set of calendar resources that the user has permission to view where groupId = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] filterFindByGroupId_PrevAndNext(
			long calendarResourceId, long groupId,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				calendarResourceId, groupId, orderByComparator);
		}

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, calendarResource, groupId, orderByComparator, true);

			array[1] = calendarResource;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, calendarResource, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource filterGetByGroupId_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CalendarResource calendarResource :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

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

	/**
	 * Returns the number of calendar resources that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching calendar resources that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"calendarResource.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByActive;
	private FinderPath _finderPathWithoutPaginationFindByActive;
	private FinderPath _finderPathCountByActive;

	/**
	 * Returns all the calendar resources where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByActive(boolean active) {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByActive(
		boolean active, int start, int end) {

		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByActive(
		boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByActive(active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByActive(
		boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByActive;
				finderArgs = new Object[] {active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByActive;
			finderArgs = new Object[] {active, start, end, orderByComparator};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if ((active != calendarResource.isActive())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByActive_First(
			boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByActive_First(
			active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByActive_First(
		boolean active, OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByActive(
			active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByActive_Last(
			boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByActive_Last(
			active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByActive_Last(
		boolean active, OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByActive(active);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByActive(
			active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where active = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByActive_PrevAndNext(
			long calendarResourceId, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByActive_PrevAndNext(
				session, calendarResource, active, orderByComparator, true);

			array[1] = calendarResource;

			array[2] = getByActive_PrevAndNext(
				session, calendarResource, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByActive_PrevAndNext(
		Session session, CalendarResource calendarResource, boolean active,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		for (CalendarResource calendarResource :
				findByActive(
					active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByActive(boolean active) {
		FinderPath finderPath = _finderPathCountByActive;

		Object[] finderArgs = new Object[] {active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 =
		"calendarResource.active = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;
	private FinderPath _finderPathWithPaginationCountByG_C;

	/**
	 * Returns all the calendar resources where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(long groupId, String code) {
		return findByG_C(
			groupId, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where groupId = &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long groupId, String code, int start, int end) {

		return findByG_C(groupId, code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long groupId, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByG_C(groupId, code, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long groupId, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		code = Objects.toString(code, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, code};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, code, start, end, orderByComparator
			};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if ((groupId != calendarResource.getGroupId()) ||
						!code.equals(calendarResource.getCode())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_G_C_CODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindCode) {
					qPos.add(code);
				}

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByG_C_First(
			long groupId, String code,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByG_C_First(
			groupId, code, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", code=");
		msg.append(code);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByG_C_First(
		long groupId, String code,
		OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByG_C(
			groupId, code, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByG_C_Last(
			long groupId, String code,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByG_C_Last(
			groupId, code, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", code=");
		msg.append(code);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByG_C_Last(
		long groupId, String code,
		OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByG_C(groupId, code);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByG_C(
			groupId, code, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where groupId = &#63; and code = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByG_C_PrevAndNext(
			long calendarResourceId, long groupId, String code,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		code = Objects.toString(code, "");

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, calendarResource, groupId, code, orderByComparator,
				true);

			array[1] = calendarResource;

			array[2] = getByG_C_PrevAndNext(
				session, calendarResource, groupId, code, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByG_C_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		String code, OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindCode) {
			qPos.add(code);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the calendar resources that the user has permission to view where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @return the matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(long groupId, String code) {
		return filterFindByG_C(
			groupId, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources that the user has permission to view where groupId = &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(
		long groupId, String code, int start, int end) {

		return filterFindByG_C(groupId, code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources that the user has permissions to view where groupId = &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(
		long groupId, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, code, start, end, orderByComparator);
		}

		code = Objects.toString(code, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3_SQL);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindCode) {
				qPos.add(code);
			}

			return (List<CalendarResource>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set of calendar resources that the user has permission to view where groupId = &#63; and code = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] filterFindByG_C_PrevAndNext(
			long calendarResourceId, long groupId, String code,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(
				calendarResourceId, groupId, code, orderByComparator);
		}

		code = Objects.toString(code, "");

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(
				session, calendarResource, groupId, code, orderByComparator,
				true);

			array[1] = calendarResource;

			array[2] = filterGetByG_C_PrevAndNext(
				session, calendarResource, groupId, code, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource filterGetByG_C_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		String code, OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3_SQL);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindCode) {
			qPos.add(code);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the calendar resources that the user has permission to view where groupId = any &#63; and code = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @return the matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(
		long[] groupIds, String code) {

		return filterFindByG_C(
			groupIds, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources that the user has permission to view where groupId = any &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(
		long[] groupIds, String code, int start, int end) {

		return filterFindByG_C(groupIds, code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources that the user has permission to view where groupId = any &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_C(
		long[] groupIds, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_C(groupIds, code, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		code = Objects.toString(code, "");

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3_SQL);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2_SQL);
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindCode) {
				qPos.add(code);
			}

			return (List<CalendarResource>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the calendar resources where groupId = any &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(long[] groupIds, String code) {
		return findByG_C(
			groupIds, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where groupId = any &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long[] groupIds, String code, int start, int end) {

		return findByG_C(groupIds, code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = any &#63; and code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long[] groupIds, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByG_C(groupIds, code, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63; and code = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_C(
		long[] groupIds, String code, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		code = Objects.toString(code, "");

		if (groupIds.length == 1) {
			return findByG_C(groupIds[0], code, start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), code};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), code, start, end, orderByComparator
			};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				_finderPathWithPaginationFindByG_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if (!ArrayUtil.contains(
							groupIds, calendarResource.getGroupId()) ||
						!code.equals(calendarResource.getCode())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			if (groupIds.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_G_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_G_C_CODE_2);
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCode) {
					qPos.add(code);
				}

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_C, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByG_C, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the calendar resources where groupId = &#63; and code = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 */
	@Override
	public void removeByG_C(long groupId, String code) {
		for (CalendarResource calendarResource :
				findByG_C(
					groupId, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByG_C(long groupId, String code) {
		code = Objects.toString(code, "");

		FinderPath finderPath = _finderPathCountByG_C;

		Object[] finderArgs = new Object[] {groupId, code};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_G_C_CODE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindCode) {
					qPos.add(code);
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

	/**
	 * Returns the number of calendar resources where groupId = any &#63; and code = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByG_C(long[] groupIds, String code) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		code = Objects.toString(code, "");

		Object[] finderArgs = new Object[] {StringUtil.merge(groupIds), code};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_C, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			if (groupIds.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_G_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_G_C_CODE_2);
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCode) {
					qPos.add(code);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_C, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByG_C, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of calendar resources that the user has permission to view where groupId = &#63; and code = &#63;.
	 *
	 * @param groupId the group ID
	 * @param code the code
	 * @return the number of matching calendar resources that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, String code) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, code);
		}

		code = Objects.toString(code, "");

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3_SQL);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindCode) {
				qPos.add(code);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of calendar resources that the user has permission to view where groupId = any &#63; and code = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param code the code
	 * @return the number of matching calendar resources that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long[] groupIds, String code) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_C(groupIds, code);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		code = Objects.toString(code, "");

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_CALENDARRESOURCE_WHERE);

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_G_C_CODE_3_SQL);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_G_C_CODE_2_SQL);
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindCode) {
				qPos.add(code);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"calendarResource.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_GROUPID_7 =
		"calendarResource.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_CODE_2 =
		"calendarResource.code = ?";

	private static final String _FINDER_COLUMN_G_C_CODE_3 =
		"(calendarResource.code IS NULL OR calendarResource.code = '')";

	private static final String _FINDER_COLUMN_G_C_CODE_2_SQL =
		"calendarResource.code_ = ?";

	private static final String _FINDER_COLUMN_G_C_CODE_3_SQL =
		"(calendarResource.code_ IS NULL OR calendarResource.code_ = '')";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the calendar resources where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_A(long groupId, boolean active) {
		return findByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_A(
		long groupId, boolean active, int start, int end) {

		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, active, start, end, orderByComparator
			};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if ((groupId != calendarResource.getGroupId()) ||
						(active != calendarResource.isActive())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByG_A_First(
			groupId, active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByG_A(
			groupId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByG_A_Last(
			long groupId, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByG_A_Last(
			groupId, active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByG_A_Last(
		long groupId, boolean active,
		OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByG_A(
			groupId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByG_A_PrevAndNext(
			long calendarResourceId, long groupId, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, calendarResource, groupId, active, orderByComparator,
				true);

			array[1] = calendarResource;

			array[2] = getByG_A_PrevAndNext(
				session, calendarResource, groupId, active, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource getByG_A_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		boolean active, OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the calendar resources that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_A(
		long groupId, boolean active) {

		return filterFindByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_A(
		long groupId, boolean active, int start, int end) {

		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources that the user has permission to view
	 */
	@Override
	public List<CalendarResource> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<CalendarResource>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set of calendar resources that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] filterFindByG_A_PrevAndNext(
			long calendarResourceId, long groupId, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(
				calendarResourceId, groupId, active, orderByComparator);
		}

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(
				session, calendarResource, groupId, active, orderByComparator,
				true);

			array[1] = calendarResource;

			array[2] = filterGetByG_A_PrevAndNext(
				session, calendarResource, groupId, active, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalendarResource filterGetByG_A_PrevAndNext(
		Session session, CalendarResource calendarResource, long groupId,
		boolean active, OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalendarResourceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalendarResourceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalendarResourceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (CalendarResource calendarResource :
				findByG_A(
					groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] {groupId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	/**
	 * Returns the number of calendar resources that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching calendar resources that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), CalendarResource.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"calendarResource.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 =
		"calendarResource.active = ?";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2_SQL =
		"calendarResource.active_ = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the calendar resource where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchResourceException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByC_C(long classNameId, long classPK)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByC_C(classNameId, classPK);

		if (calendarResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchResourceException(msg.toString());
		}

		return calendarResource;
	}

	/**
	 * Returns the calendar resource where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the calendar resource where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof CalendarResource) {
			CalendarResource calendarResource = (CalendarResource)result;

			if ((classNameId != calendarResource.getClassNameId()) ||
				(classPK != calendarResource.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<CalendarResource> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CalendarResource calendarResource = list.get(0);

					result = calendarResource;

					cacheResult(calendarResource);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_C, finderArgs);
				}

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
			return (CalendarResource)result;
		}
	}

	/**
	 * Removes the calendar resource where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the calendar resource that was removed
	 */
	@Override
	public CalendarResource removeByC_C(long classNameId, long classPK)
		throws NoSuchResourceException {

		CalendarResource calendarResource = findByC_C(classNameId, classPK);

		return remove(calendarResource);
	}

	/**
	 * Returns the number of calendar resources where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"calendarResource.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"calendarResource.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_A;
	private FinderPath _finderPathWithPaginationCountByC_C_A;

	/**
	 * Returns all the calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @return the matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByC_C_A(
		long companyId, String code, boolean active) {

		return findByC_C_A(
			companyId, code, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByC_C_A(
		long companyId, String code, boolean active, int start, int end) {

		return findByC_C_A(companyId, code, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByC_C_A(
		long companyId, String code, boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findByC_C_A(
			companyId, code, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar resources
	 */
	@Override
	public List<CalendarResource> findByC_C_A(
		long companyId, String code, boolean active, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		code = Objects.toString(code, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_A;
		finderArgs = new Object[] {
			companyId, code, active, start, end, orderByComparator
		};

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarResource calendarResource : list) {
					if ((companyId != calendarResource.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							calendarResource.getCode(), code, '_', '%', '\\',
							true) ||
						(active != calendarResource.isActive())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_C_A_COMPANYID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_A_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_C_C_A_CODE_2);
			}

			query.append(_FINDER_COLUMN_C_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCode) {
					qPos.add(code);
				}

				qPos.add(active);

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first calendar resource in the ordered set where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByC_C_A_First(
			long companyId, String code, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByC_C_A_First(
			companyId, code, active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", codeLIKE");
		msg.append(code);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the first calendar resource in the ordered set where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByC_C_A_First(
		long companyId, String code, boolean active,
		OrderByComparator<CalendarResource> orderByComparator) {

		List<CalendarResource> list = findByC_C_A(
			companyId, code, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar resource in the ordered set where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource
	 * @throws NoSuchResourceException if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource findByC_C_A_Last(
			long companyId, String code, boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByC_C_A_Last(
			companyId, code, active, orderByComparator);

		if (calendarResource != null) {
			return calendarResource;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", codeLIKE");
		msg.append(code);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchResourceException(msg.toString());
	}

	/**
	 * Returns the last calendar resource in the ordered set where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar resource, or <code>null</code> if a matching calendar resource could not be found
	 */
	@Override
	public CalendarResource fetchByC_C_A_Last(
		long companyId, String code, boolean active,
		OrderByComparator<CalendarResource> orderByComparator) {

		int count = countByC_C_A(companyId, code, active);

		if (count == 0) {
			return null;
		}

		List<CalendarResource> list = findByC_C_A(
			companyId, code, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar resources before and after the current calendar resource in the ordered set where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param calendarResourceId the primary key of the current calendar resource
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource[] findByC_C_A_PrevAndNext(
			long calendarResourceId, long companyId, String code,
			boolean active,
			OrderByComparator<CalendarResource> orderByComparator)
		throws NoSuchResourceException {

		code = Objects.toString(code, "");

		CalendarResource calendarResource = findByPrimaryKey(
			calendarResourceId);

		Session session = null;

		try {
			session = openSession();

			CalendarResource[] array = new CalendarResourceImpl[3];

			array[0] = getByC_C_A_PrevAndNext(
				session, calendarResource, companyId, code, active,
				orderByComparator, true);

			array[1] = calendarResource;

			array[2] = getByC_C_A_PrevAndNext(
				session, calendarResource, companyId, code, active,
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

	protected CalendarResource getByC_C_A_PrevAndNext(
		Session session, CalendarResource calendarResource, long companyId,
		String code, boolean active,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_C_C_A_COMPANYID_2);

		boolean bindCode = false;

		if (code.isEmpty()) {
			query.append(_FINDER_COLUMN_C_C_A_CODE_3);
		}
		else {
			bindCode = true;

			query.append(_FINDER_COLUMN_C_C_A_CODE_2);
		}

		query.append(_FINDER_COLUMN_C_C_A_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CalendarResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindCode) {
			qPos.add(code);
		}

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarResource)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CalendarResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 */
	@Override
	public void removeByC_C_A(long companyId, String code, boolean active) {
		for (CalendarResource calendarResource :
				findByC_C_A(
					companyId, code, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources where companyId = &#63; and code LIKE &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param active the active
	 * @return the number of matching calendar resources
	 */
	@Override
	public int countByC_C_A(long companyId, String code, boolean active) {
		code = Objects.toString(code, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_C_A;

		Object[] finderArgs = new Object[] {companyId, code, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CALENDARRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_C_A_COMPANYID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_A_CODE_3);
			}
			else {
				bindCode = true;

				query.append(_FINDER_COLUMN_C_C_A_CODE_2);
			}

			query.append(_FINDER_COLUMN_C_C_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCode) {
					qPos.add(code);
				}

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_C_C_A_COMPANYID_2 =
		"calendarResource.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_A_CODE_2 =
		"calendarResource.code LIKE ? AND ";

	private static final String _FINDER_COLUMN_C_C_A_CODE_3 =
		"(calendarResource.code IS NULL OR calendarResource.code LIKE '') AND ";

	private static final String _FINDER_COLUMN_C_C_A_ACTIVE_2 =
		"calendarResource.active = ?";

	public CalendarResourcePersistenceImpl() {
		setModelClass(CalendarResource.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("code", "code_");
		dbColumnNames.put("active", "active_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the calendar resource in the entity cache if it is enabled.
	 *
	 * @param calendarResource the calendar resource
	 */
	@Override
	public void cacheResult(CalendarResource calendarResource) {
		entityCache.putResult(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceImpl.class, calendarResource.getPrimaryKey(),
			calendarResource);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				calendarResource.getUuid(), calendarResource.getGroupId()
			},
			calendarResource);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				calendarResource.getClassNameId(), calendarResource.getClassPK()
			},
			calendarResource);

		calendarResource.resetOriginalValues();
	}

	/**
	 * Caches the calendar resources in the entity cache if it is enabled.
	 *
	 * @param calendarResources the calendar resources
	 */
	@Override
	public void cacheResult(List<CalendarResource> calendarResources) {
		for (CalendarResource calendarResource : calendarResources) {
			if (entityCache.getResult(
					CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
					CalendarResourceImpl.class,
					calendarResource.getPrimaryKey()) == null) {

				cacheResult(calendarResource);
			}
			else {
				calendarResource.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all calendar resources.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CalendarResourceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the calendar resource.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CalendarResource calendarResource) {
		entityCache.removeResult(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceImpl.class, calendarResource.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CalendarResourceModelImpl)calendarResource, true);
	}

	@Override
	public void clearCache(List<CalendarResource> calendarResources) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CalendarResource calendarResource : calendarResources) {
			entityCache.removeResult(
				CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
				CalendarResourceImpl.class, calendarResource.getPrimaryKey());

			clearUniqueFindersCache(
				(CalendarResourceModelImpl)calendarResource, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CalendarResourceModelImpl calendarResourceModelImpl) {

		Object[] args = new Object[] {
			calendarResourceModelImpl.getUuid(),
			calendarResourceModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, calendarResourceModelImpl, false);

		args = new Object[] {
			calendarResourceModelImpl.getClassNameId(),
			calendarResourceModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, calendarResourceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CalendarResourceModelImpl calendarResourceModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				calendarResourceModelImpl.getUuid(),
				calendarResourceModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((calendarResourceModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				calendarResourceModelImpl.getOriginalUuid(),
				calendarResourceModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				calendarResourceModelImpl.getClassNameId(),
				calendarResourceModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((calendarResourceModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				calendarResourceModelImpl.getOriginalClassNameId(),
				calendarResourceModelImpl.getOriginalClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new calendar resource with the primary key. Does not add the calendar resource to the database.
	 *
	 * @param calendarResourceId the primary key for the new calendar resource
	 * @return the new calendar resource
	 */
	@Override
	public CalendarResource create(long calendarResourceId) {
		CalendarResource calendarResource = new CalendarResourceImpl();

		calendarResource.setNew(true);
		calendarResource.setPrimaryKey(calendarResourceId);

		String uuid = PortalUUIDUtil.generate();

		calendarResource.setUuid(uuid);

		calendarResource.setCompanyId(CompanyThreadLocal.getCompanyId());

		return calendarResource;
	}

	/**
	 * Removes the calendar resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarResourceId the primary key of the calendar resource
	 * @return the calendar resource that was removed
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource remove(long calendarResourceId)
		throws NoSuchResourceException {

		return remove((Serializable)calendarResourceId);
	}

	/**
	 * Removes the calendar resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the calendar resource
	 * @return the calendar resource that was removed
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource remove(Serializable primaryKey)
		throws NoSuchResourceException {

		Session session = null;

		try {
			session = openSession();

			CalendarResource calendarResource = (CalendarResource)session.get(
				CalendarResourceImpl.class, primaryKey);

			if (calendarResource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(calendarResource);
		}
		catch (NoSuchResourceException nsee) {
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
	protected CalendarResource removeImpl(CalendarResource calendarResource) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(calendarResource)) {
				calendarResource = (CalendarResource)session.get(
					CalendarResourceImpl.class,
					calendarResource.getPrimaryKeyObj());
			}

			if (calendarResource != null) {
				session.delete(calendarResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (calendarResource != null) {
			clearCache(calendarResource);
		}

		return calendarResource;
	}

	@Override
	public CalendarResource updateImpl(CalendarResource calendarResource) {
		boolean isNew = calendarResource.isNew();

		if (!(calendarResource instanceof CalendarResourceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(calendarResource.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					calendarResource);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in calendarResource proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CalendarResource implementation " +
					calendarResource.getClass());
		}

		CalendarResourceModelImpl calendarResourceModelImpl =
			(CalendarResourceModelImpl)calendarResource;

		if (Validator.isNull(calendarResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			calendarResource.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (calendarResource.getCreateDate() == null)) {
			if (serviceContext == null) {
				calendarResource.setCreateDate(now);
			}
			else {
				calendarResource.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!calendarResourceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				calendarResource.setModifiedDate(now);
			}
			else {
				calendarResource.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (calendarResource.isNew()) {
				session.save(calendarResource);

				calendarResource.setNew(false);
			}
			else {
				calendarResource = (CalendarResource)session.merge(
					calendarResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CalendarResourceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {calendarResourceModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				calendarResourceModelImpl.getUuid(),
				calendarResourceModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {calendarResourceModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {calendarResourceModelImpl.isActive()};

			finderCache.removeResult(_finderPathCountByActive, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByActive, args);

			args = new Object[] {
				calendarResourceModelImpl.getGroupId(),
				calendarResourceModelImpl.getCode()
			};

			finderCache.removeResult(_finderPathCountByG_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C, args);

			args = new Object[] {
				calendarResourceModelImpl.getGroupId(),
				calendarResourceModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByG_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {calendarResourceModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalUuid(),
					calendarResourceModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					calendarResourceModelImpl.getUuid(),
					calendarResourceModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {calendarResourceModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByActive.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByActive, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByActive, args);

				args = new Object[] {calendarResourceModelImpl.isActive()};

				finderCache.removeResult(_finderPathCountByActive, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByActive, args);
			}

			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalGroupId(),
					calendarResourceModelImpl.getOriginalCode()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);

				args = new Object[] {
					calendarResourceModelImpl.getGroupId(),
					calendarResourceModelImpl.getCode()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);
			}

			if ((calendarResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarResourceModelImpl.getOriginalGroupId(),
					calendarResourceModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);

				args = new Object[] {
					calendarResourceModelImpl.getGroupId(),
					calendarResourceModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);
			}
		}

		entityCache.putResult(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceImpl.class, calendarResource.getPrimaryKey(),
			calendarResource, false);

		clearUniqueFindersCache(calendarResourceModelImpl, false);
		cacheUniqueFindersCache(calendarResourceModelImpl);

		calendarResource.resetOriginalValues();

		return calendarResource;
	}

	/**
	 * Returns the calendar resource with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the calendar resource
	 * @return the calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceException {

		CalendarResource calendarResource = fetchByPrimaryKey(primaryKey);

		if (calendarResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return calendarResource;
	}

	/**
	 * Returns the calendar resource with the primary key or throws a <code>NoSuchResourceException</code> if it could not be found.
	 *
	 * @param calendarResourceId the primary key of the calendar resource
	 * @return the calendar resource
	 * @throws NoSuchResourceException if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource findByPrimaryKey(long calendarResourceId)
		throws NoSuchResourceException {

		return findByPrimaryKey((Serializable)calendarResourceId);
	}

	/**
	 * Returns the calendar resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the calendar resource
	 * @return the calendar resource, or <code>null</code> if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CalendarResource calendarResource = (CalendarResource)serializable;

		if (calendarResource == null) {
			Session session = null;

			try {
				session = openSession();

				calendarResource = (CalendarResource)session.get(
					CalendarResourceImpl.class, primaryKey);

				if (calendarResource != null) {
					cacheResult(calendarResource);
				}
				else {
					entityCache.putResult(
						CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
						CalendarResourceImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
					CalendarResourceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return calendarResource;
	}

	/**
	 * Returns the calendar resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param calendarResourceId the primary key of the calendar resource
	 * @return the calendar resource, or <code>null</code> if a calendar resource with the primary key could not be found
	 */
	@Override
	public CalendarResource fetchByPrimaryKey(long calendarResourceId) {
		return fetchByPrimaryKey((Serializable)calendarResourceId);
	}

	@Override
	public Map<Serializable, CalendarResource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CalendarResource> map =
			new HashMap<Serializable, CalendarResource>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CalendarResource calendarResource = fetchByPrimaryKey(primaryKey);

			if (calendarResource != null) {
				map.put(primaryKey, calendarResource);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
				CalendarResourceImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CalendarResource)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_CALENDARRESOURCE_WHERE_PKS_IN);

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

			for (CalendarResource calendarResource :
					(List<CalendarResource>)q.list()) {

				map.put(calendarResource.getPrimaryKeyObj(), calendarResource);

				cacheResult(calendarResource);

				uncachedPrimaryKeys.remove(calendarResource.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
					CalendarResourceImpl.class, primaryKey, nullModel);
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
	 * Returns all the calendar resources.
	 *
	 * @return the calendar resources
	 */
	@Override
	public List<CalendarResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @return the range of calendar resources
	 */
	@Override
	public List<CalendarResource> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of calendar resources
	 */
	@Override
	public List<CalendarResource> findAll(
		int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CalendarResourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar resources
	 * @param end the upper bound of the range of calendar resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of calendar resources
	 */
	@Override
	public List<CalendarResource> findAll(
		int start, int end,
		OrderByComparator<CalendarResource> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CalendarResource> list = null;

		if (useFinderCache) {
			list = (List<CalendarResource>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CALENDARRESOURCE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CALENDARRESOURCE;

				if (pagination) {
					sql = sql.concat(CalendarResourceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalendarResource>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the calendar resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CalendarResource calendarResource : findAll()) {
			remove(calendarResource);
		}
	}

	/**
	 * Returns the number of calendar resources.
	 *
	 * @return the number of calendar resources
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CALENDARRESOURCE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return CalendarResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the calendar resource persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			CalendarResourceModelImpl.UUID_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			CalendarResourceModelImpl.UUID_COLUMN_BITMASK |
			CalendarResourceModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			CalendarResourceModelImpl.UUID_COLUMN_BITMASK |
			CalendarResourceModelImpl.COMPANYID_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			CalendarResourceModelImpl.GROUPID_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByActive = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByActive",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByActive = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] {Boolean.class.getName()},
			CalendarResourceModelImpl.ACTIVE_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByActive = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] {Boolean.class.getName()});

		_finderPathWithPaginationFindByG_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), String.class.getName()},
			CalendarResourceModelImpl.GROUPID_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByG_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationCountByG_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_A = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			CalendarResourceModelImpl.GROUPID_COLUMN_BITMASK |
			CalendarResourceModelImpl.ACTIVE_COLUMN_BITMASK |
			CalendarResourceModelImpl.CODE_COLUMN_BITMASK);

		_finderPathCountByG_A = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			CalendarResourceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CalendarResourceModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_A = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED,
			CalendarResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_C_A = new FinderPath(
			CalendarResourceModelImpl.ENTITY_CACHE_ENABLED,
			CalendarResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(CalendarResourceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CALENDARRESOURCE =
		"SELECT calendarResource FROM CalendarResource calendarResource";

	private static final String _SQL_SELECT_CALENDARRESOURCE_WHERE_PKS_IN =
		"SELECT calendarResource FROM CalendarResource calendarResource WHERE calendarResourceId IN (";

	private static final String _SQL_SELECT_CALENDARRESOURCE_WHERE =
		"SELECT calendarResource FROM CalendarResource calendarResource WHERE ";

	private static final String _SQL_COUNT_CALENDARRESOURCE =
		"SELECT COUNT(calendarResource) FROM CalendarResource calendarResource";

	private static final String _SQL_COUNT_CALENDARRESOURCE_WHERE =
		"SELECT COUNT(calendarResource) FROM CalendarResource calendarResource WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"calendarResource.calendarResourceId";

	private static final String _FILTER_SQL_SELECT_CALENDARRESOURCE_WHERE =
		"SELECT DISTINCT {calendarResource.*} FROM CalendarResource calendarResource WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CalendarResource.*} FROM (SELECT DISTINCT calendarResource.calendarResourceId FROM CalendarResource calendarResource WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CALENDARRESOURCE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CalendarResource ON TEMP_TABLE.calendarResourceId = CalendarResource.calendarResourceId";

	private static final String _FILTER_SQL_COUNT_CALENDARRESOURCE_WHERE =
		"SELECT COUNT(DISTINCT calendarResource.calendarResourceId) AS COUNT_VALUE FROM CalendarResource calendarResource WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "calendarResource";

	private static final String _FILTER_ENTITY_TABLE = "CalendarResource";

	private static final String _ORDER_BY_ENTITY_ALIAS = "calendarResource.";

	private static final String _ORDER_BY_ENTITY_TABLE = "CalendarResource.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CalendarResource exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CalendarResource exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarResourcePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "code", "active"});

}