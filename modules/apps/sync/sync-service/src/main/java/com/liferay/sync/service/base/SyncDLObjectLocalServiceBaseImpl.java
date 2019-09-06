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

package com.liferay.sync.service.base;

import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.RepositoryPersistence;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffPersistence;
import com.liferay.sync.service.persistence.SyncDLObjectFinder;
import com.liferay.sync.service.persistence.SyncDLObjectPersistence;
import com.liferay.sync.service.persistence.SyncDevicePersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the sync dl object local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.sync.service.impl.SyncDLObjectLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.sync.service.impl.SyncDLObjectLocalServiceImpl
 * @generated
 */
public abstract class SyncDLObjectLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements SyncDLObjectLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>SyncDLObjectLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.sync.service.SyncDLObjectLocalServiceUtil</code>.
	 */

	/**
	 * Adds the sync dl object to the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SyncDLObject addSyncDLObject(SyncDLObject syncDLObject) {
		syncDLObject.setNew(true);

		return syncDLObjectPersistence.update(syncDLObject);
	}

	/**
	 * Creates a new sync dl object with the primary key. Does not add the sync dl object to the database.
	 *
	 * @param syncDLObjectId the primary key for the new sync dl object
	 * @return the new sync dl object
	 */
	@Override
	@Transactional(enabled = false)
	public SyncDLObject createSyncDLObject(long syncDLObjectId) {
		return syncDLObjectPersistence.create(syncDLObjectId);
	}

	/**
	 * Deletes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws PortalException if a sync dl object with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SyncDLObject deleteSyncDLObject(long syncDLObjectId)
		throws PortalException {

		return syncDLObjectPersistence.remove(syncDLObjectId);
	}

	/**
	 * Deletes the sync dl object from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SyncDLObject deleteSyncDLObject(SyncDLObject syncDLObject) {
		return syncDLObjectPersistence.remove(syncDLObject);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			SyncDLObject.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return syncDLObjectPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return syncDLObjectPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return syncDLObjectPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return syncDLObjectPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return syncDLObjectPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public SyncDLObject fetchSyncDLObject(long syncDLObjectId) {
		return syncDLObjectPersistence.fetchByPrimaryKey(syncDLObjectId);
	}

	/**
	 * Returns the sync dl object with the primary key.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws PortalException if a sync dl object with the primary key could not be found
	 */
	@Override
	public SyncDLObject getSyncDLObject(long syncDLObjectId)
		throws PortalException {

		return syncDLObjectPersistence.findByPrimaryKey(syncDLObjectId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(syncDLObjectLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SyncDLObject.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("syncDLObjectId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			syncDLObjectLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(SyncDLObject.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"syncDLObjectId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(syncDLObjectLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SyncDLObject.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("syncDLObjectId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return syncDLObjectLocalService.deleteSyncDLObject(
			(SyncDLObject)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return syncDLObjectPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of sync dl objects
	 */
	@Override
	public List<SyncDLObject> getSyncDLObjects(int start, int end) {
		return syncDLObjectPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of sync dl objects.
	 *
	 * @return the number of sync dl objects
	 */
	@Override
	public int getSyncDLObjectsCount() {
		return syncDLObjectPersistence.countAll();
	}

	/**
	 * Updates the sync dl object in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SyncDLObject updateSyncDLObject(SyncDLObject syncDLObject) {
		return syncDLObjectPersistence.update(syncDLObject);
	}

	/**
	 * Returns the sync device local service.
	 *
	 * @return the sync device local service
	 */
	public com.liferay.sync.service.SyncDeviceLocalService
		getSyncDeviceLocalService() {

		return syncDeviceLocalService;
	}

	/**
	 * Sets the sync device local service.
	 *
	 * @param syncDeviceLocalService the sync device local service
	 */
	public void setSyncDeviceLocalService(
		com.liferay.sync.service.SyncDeviceLocalService
			syncDeviceLocalService) {

		this.syncDeviceLocalService = syncDeviceLocalService;
	}

	/**
	 * Returns the sync device persistence.
	 *
	 * @return the sync device persistence
	 */
	public SyncDevicePersistence getSyncDevicePersistence() {
		return syncDevicePersistence;
	}

	/**
	 * Sets the sync device persistence.
	 *
	 * @param syncDevicePersistence the sync device persistence
	 */
	public void setSyncDevicePersistence(
		SyncDevicePersistence syncDevicePersistence) {

		this.syncDevicePersistence = syncDevicePersistence;
	}

	/**
	 * Returns the sync dl file version diff local service.
	 *
	 * @return the sync dl file version diff local service
	 */
	public com.liferay.sync.service.SyncDLFileVersionDiffLocalService
		getSyncDLFileVersionDiffLocalService() {

		return syncDLFileVersionDiffLocalService;
	}

	/**
	 * Sets the sync dl file version diff local service.
	 *
	 * @param syncDLFileVersionDiffLocalService the sync dl file version diff local service
	 */
	public void setSyncDLFileVersionDiffLocalService(
		com.liferay.sync.service.SyncDLFileVersionDiffLocalService
			syncDLFileVersionDiffLocalService) {

		this.syncDLFileVersionDiffLocalService =
			syncDLFileVersionDiffLocalService;
	}

	/**
	 * Returns the sync dl file version diff persistence.
	 *
	 * @return the sync dl file version diff persistence
	 */
	public SyncDLFileVersionDiffPersistence
		getSyncDLFileVersionDiffPersistence() {

		return syncDLFileVersionDiffPersistence;
	}

	/**
	 * Sets the sync dl file version diff persistence.
	 *
	 * @param syncDLFileVersionDiffPersistence the sync dl file version diff persistence
	 */
	public void setSyncDLFileVersionDiffPersistence(
		SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence) {

		this.syncDLFileVersionDiffPersistence =
			syncDLFileVersionDiffPersistence;
	}

	/**
	 * Returns the sync dl object local service.
	 *
	 * @return the sync dl object local service
	 */
	public SyncDLObjectLocalService getSyncDLObjectLocalService() {
		return syncDLObjectLocalService;
	}

	/**
	 * Sets the sync dl object local service.
	 *
	 * @param syncDLObjectLocalService the sync dl object local service
	 */
	public void setSyncDLObjectLocalService(
		SyncDLObjectLocalService syncDLObjectLocalService) {

		this.syncDLObjectLocalService = syncDLObjectLocalService;
	}

	/**
	 * Returns the sync dl object persistence.
	 *
	 * @return the sync dl object persistence
	 */
	public SyncDLObjectPersistence getSyncDLObjectPersistence() {
		return syncDLObjectPersistence;
	}

	/**
	 * Sets the sync dl object persistence.
	 *
	 * @param syncDLObjectPersistence the sync dl object persistence
	 */
	public void setSyncDLObjectPersistence(
		SyncDLObjectPersistence syncDLObjectPersistence) {

		this.syncDLObjectPersistence = syncDLObjectPersistence;
	}

	/**
	 * Returns the sync dl object finder.
	 *
	 * @return the sync dl object finder
	 */
	public SyncDLObjectFinder getSyncDLObjectFinder() {
		return syncDLObjectFinder;
	}

	/**
	 * Sets the sync dl object finder.
	 *
	 * @param syncDLObjectFinder the sync dl object finder
	 */
	public void setSyncDLObjectFinder(SyncDLObjectFinder syncDLObjectFinder) {
		this.syncDLObjectFinder = syncDLObjectFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService
		getGroupLocalService() {

		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {

		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the organization local service.
	 *
	 * @return the organization local service
	 */
	public com.liferay.portal.kernel.service.OrganizationLocalService
		getOrganizationLocalService() {

		return organizationLocalService;
	}

	/**
	 * Sets the organization local service.
	 *
	 * @param organizationLocalService the organization local service
	 */
	public void setOrganizationLocalService(
		com.liferay.portal.kernel.service.OrganizationLocalService
			organizationLocalService) {

		this.organizationLocalService = organizationLocalService;
	}

	/**
	 * Returns the organization persistence.
	 *
	 * @return the organization persistence
	 */
	public OrganizationPersistence getOrganizationPersistence() {
		return organizationPersistence;
	}

	/**
	 * Sets the organization persistence.
	 *
	 * @param organizationPersistence the organization persistence
	 */
	public void setOrganizationPersistence(
		OrganizationPersistence organizationPersistence) {

		this.organizationPersistence = organizationPersistence;
	}

	/**
	 * Returns the repository local service.
	 *
	 * @return the repository local service
	 */
	public com.liferay.portal.kernel.service.RepositoryLocalService
		getRepositoryLocalService() {

		return repositoryLocalService;
	}

	/**
	 * Sets the repository local service.
	 *
	 * @param repositoryLocalService the repository local service
	 */
	public void setRepositoryLocalService(
		com.liferay.portal.kernel.service.RepositoryLocalService
			repositoryLocalService) {

		this.repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Returns the repository persistence.
	 *
	 * @return the repository persistence
	 */
	public RepositoryPersistence getRepositoryPersistence() {
		return repositoryPersistence;
	}

	/**
	 * Sets the repository persistence.
	 *
	 * @param repositoryPersistence the repository persistence
	 */
	public void setRepositoryPersistence(
		RepositoryPersistence repositoryPersistence) {

		this.repositoryPersistence = repositoryPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource permission local service.
	 *
	 * @return the resource permission local service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionLocalService
		getResourcePermissionLocalService() {

		return resourcePermissionLocalService;
	}

	/**
	 * Sets the resource permission local service.
	 *
	 * @param resourcePermissionLocalService the resource permission local service
	 */
	public void setResourcePermissionLocalService(
		com.liferay.portal.kernel.service.ResourcePermissionLocalService
			resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	 * Returns the resource permission persistence.
	 *
	 * @return the resource permission persistence
	 */
	public ResourcePermissionPersistence getResourcePermissionPersistence() {
		return resourcePermissionPersistence;
	}

	/**
	 * Sets the resource permission persistence.
	 *
	 * @param resourcePermissionPersistence the resource permission persistence
	 */
	public void setResourcePermissionPersistence(
		ResourcePermissionPersistence resourcePermissionPersistence) {

		this.resourcePermissionPersistence = resourcePermissionPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the dl app local service.
	 *
	 * @return the dl app local service
	 */
	public com.liferay.document.library.kernel.service.DLAppLocalService
		getDLAppLocalService() {

		return dlAppLocalService;
	}

	/**
	 * Sets the dl app local service.
	 *
	 * @param dlAppLocalService the dl app local service
	 */
	public void setDLAppLocalService(
		com.liferay.document.library.kernel.service.DLAppLocalService
			dlAppLocalService) {

		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the document library file entry local service.
	 *
	 * @return the document library file entry local service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryLocalService
		getDLFileEntryLocalService() {

		return dlFileEntryLocalService;
	}

	/**
	 * Sets the document library file entry local service.
	 *
	 * @param dlFileEntryLocalService the document library file entry local service
	 */
	public void setDLFileEntryLocalService(
		com.liferay.document.library.kernel.service.DLFileEntryLocalService
			dlFileEntryLocalService) {

		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the document library file entry persistence.
	 *
	 * @return the document library file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the document library file entry persistence.
	 *
	 * @param dlFileEntryPersistence the document library file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {

		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the document library file version local service.
	 *
	 * @return the document library file version local service
	 */
	public com.liferay.document.library.kernel.service.DLFileVersionLocalService
		getDLFileVersionLocalService() {

		return dlFileVersionLocalService;
	}

	/**
	 * Sets the document library file version local service.
	 *
	 * @param dlFileVersionLocalService the document library file version local service
	 */
	public void setDLFileVersionLocalService(
		com.liferay.document.library.kernel.service.DLFileVersionLocalService
			dlFileVersionLocalService) {

		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	/**
	 * Returns the document library file version persistence.
	 *
	 * @return the document library file version persistence
	 */
	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	/**
	 * Sets the document library file version persistence.
	 *
	 * @param dlFileVersionPersistence the document library file version persistence
	 */
	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {

		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	/**
	 * Returns the document library folder local service.
	 *
	 * @return the document library folder local service
	 */
	public com.liferay.document.library.kernel.service.DLFolderLocalService
		getDLFolderLocalService() {

		return dlFolderLocalService;
	}

	/**
	 * Sets the document library folder local service.
	 *
	 * @param dlFolderLocalService the document library folder local service
	 */
	public void setDLFolderLocalService(
		com.liferay.document.library.kernel.service.DLFolderLocalService
			dlFolderLocalService) {

		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the document library folder persistence.
	 *
	 * @return the document library folder persistence
	 */
	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	/**
	 * Sets the document library folder persistence.
	 *
	 * @param dlFolderPersistence the document library folder persistence
	 */
	public void setDLFolderPersistence(
		DLFolderPersistence dlFolderPersistence) {

		this.dlFolderPersistence = dlFolderPersistence;
	}

	/**
	 * Returns the dl trash local service.
	 *
	 * @return the dl trash local service
	 */
	public com.liferay.document.library.kernel.service.DLTrashLocalService
		getDLTrashLocalService() {

		return dlTrashLocalService;
	}

	/**
	 * Sets the dl trash local service.
	 *
	 * @param dlTrashLocalService the dl trash local service
	 */
	public void setDLTrashLocalService(
		com.liferay.document.library.kernel.service.DLTrashLocalService
			dlTrashLocalService) {

		this.dlTrashLocalService = dlTrashLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.sync.model.SyncDLObject", syncDLObjectLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.sync.model.SyncDLObject");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SyncDLObjectLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SyncDLObject.class;
	}

	protected String getModelClassName() {
		return SyncDLObject.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = syncDLObjectPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.sync.service.SyncDeviceLocalService.class)
	protected com.liferay.sync.service.SyncDeviceLocalService
		syncDeviceLocalService;

	@BeanReference(type = SyncDevicePersistence.class)
	protected SyncDevicePersistence syncDevicePersistence;

	@BeanReference(
		type = com.liferay.sync.service.SyncDLFileVersionDiffLocalService.class
	)
	protected com.liferay.sync.service.SyncDLFileVersionDiffLocalService
		syncDLFileVersionDiffLocalService;

	@BeanReference(type = SyncDLFileVersionDiffPersistence.class)
	protected SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence;

	@BeanReference(type = SyncDLObjectLocalService.class)
	protected SyncDLObjectLocalService syncDLObjectLocalService;

	@BeanReference(type = SyncDLObjectPersistence.class)
	protected SyncDLObjectPersistence syncDLObjectPersistence;

	@BeanReference(type = SyncDLObjectFinder.class)
	protected SyncDLObjectFinder syncDLObjectFinder;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.GroupLocalService.class
	)
	protected com.liferay.portal.kernel.service.GroupLocalService
		groupLocalService;

	@ServiceReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.OrganizationLocalService.class
	)
	protected com.liferay.portal.kernel.service.OrganizationLocalService
		organizationLocalService;

	@ServiceReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.RepositoryLocalService.class
	)
	protected com.liferay.portal.kernel.service.RepositoryLocalService
		repositoryLocalService;

	@ServiceReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourcePermissionLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourcePermissionLocalService
		resourcePermissionLocalService;

	@ServiceReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(
		type = com.liferay.document.library.kernel.service.DLAppLocalService.class
	)
	protected com.liferay.document.library.kernel.service.DLAppLocalService
		dlAppLocalService;

	@ServiceReference(
		type = com.liferay.document.library.kernel.service.DLFileEntryLocalService.class
	)
	protected
		com.liferay.document.library.kernel.service.DLFileEntryLocalService
			dlFileEntryLocalService;

	@ServiceReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;

	@ServiceReference(
		type = com.liferay.document.library.kernel.service.DLFileVersionLocalService.class
	)
	protected
		com.liferay.document.library.kernel.service.DLFileVersionLocalService
			dlFileVersionLocalService;

	@ServiceReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;

	@ServiceReference(
		type = com.liferay.document.library.kernel.service.DLFolderLocalService.class
	)
	protected com.liferay.document.library.kernel.service.DLFolderLocalService
		dlFolderLocalService;

	@ServiceReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;

	@ServiceReference(
		type = com.liferay.document.library.kernel.service.DLTrashLocalService.class
	)
	protected com.liferay.document.library.kernel.service.DLTrashLocalService
		dlTrashLocalService;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}