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

package com.liferay.asset.display.page.service.base;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset display page entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.asset.display.page.service.impl.AssetDisplayPageEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.display.page.service.impl.AssetDisplayPageEntryLocalServiceImpl
 * @generated
 */
public abstract class AssetDisplayPageEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AssetDisplayPageEntryLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>AssetDisplayPageEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the asset display page entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		assetDisplayPageEntry.setNew(true);

		return assetDisplayPageEntryPersistence.update(assetDisplayPageEntry);
	}

	/**
	 * Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	 *
	 * @param assetDisplayPageEntryId the primary key for the new asset display page entry
	 * @return the new asset display page entry
	 */
	@Override
	@Transactional(enabled = false)
	public AssetDisplayPageEntry createAssetDisplayPageEntry(
		long assetDisplayPageEntryId) {

		return assetDisplayPageEntryPersistence.create(assetDisplayPageEntryId);
	}

	/**
	 * Deletes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws PortalException if a asset display page entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetDisplayPageEntry deleteAssetDisplayPageEntry(
			long assetDisplayPageEntryId)
		throws PortalException {

		return assetDisplayPageEntryPersistence.remove(assetDisplayPageEntryId);
	}

	/**
	 * Deletes the asset display page entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetDisplayPageEntry deleteAssetDisplayPageEntry(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		return assetDisplayPageEntryPersistence.remove(assetDisplayPageEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return assetDisplayPageEntryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return assetDisplayPageEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return assetDisplayPageEntryPersistence.findWithDynamicQuery(
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
		return assetDisplayPageEntryPersistence.countWithDynamicQuery(
			dynamicQuery);
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

		return assetDisplayPageEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntry(
		long assetDisplayPageEntryId) {

		return assetDisplayPageEntryPersistence.fetchByPrimaryKey(
			assetDisplayPageEntryId);
	}

	/**
	 * Returns the asset display page entry matching the UUID and group.
	 *
	 * @param uuid the asset display page entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return assetDisplayPageEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset display page entry with the primary key.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws PortalException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry getAssetDisplayPageEntry(
			long assetDisplayPageEntryId)
		throws PortalException {

		return assetDisplayPageEntryPersistence.findByPrimaryKey(
			assetDisplayPageEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			assetDisplayPageEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AssetDisplayPageEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"assetDisplayPageEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			assetDisplayPageEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			AssetDisplayPageEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"assetDisplayPageEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			assetDisplayPageEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AssetDisplayPageEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"assetDisplayPageEntryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");

					StagedModelType stagedModelType =
						exportActionableDynamicQuery.getStagedModelType();

					long referrerClassNameId =
						stagedModelType.getReferrerClassNameId();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					if ((referrerClassNameId !=
							StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
						(referrerClassNameId !=
							StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {

						dynamicQuery.add(
							classNameIdProperty.eq(
								stagedModelType.getReferrerClassNameId()));
					}
					else if (referrerClassNameId ==
								StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {

						dynamicQuery.add(classNameIdProperty.isNotNull());
					}
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetDisplayPageEntry>() {

				@Override
				public void performAction(
						AssetDisplayPageEntry assetDisplayPageEntry)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, assetDisplayPageEntry);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					AssetDisplayPageEntry.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
			(AssetDisplayPageEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return assetDisplayPageEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the asset display page entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset display page entries
	 * @param companyId the primary key of the company
	 * @return the matching asset display page entries, or an empty list if no matches were found
	 */
	@Override
	public List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByUuidAndCompanyId(
			String uuid, long companyId) {

		return assetDisplayPageEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of asset display page entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset display page entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset display page entries, or an empty list if no matches were found
	 */
	@Override
	public List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return assetDisplayPageEntryPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the asset display page entry matching the UUID and group.
	 *
	 * @param uuid the asset display page entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset display page entry
	 * @throws PortalException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry getAssetDisplayPageEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return assetDisplayPageEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> getAssetDisplayPageEntries(
		int start, int end) {

		return assetDisplayPageEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of asset display page entries.
	 *
	 * @return the number of asset display page entries
	 */
	@Override
	public int getAssetDisplayPageEntriesCount() {
		return assetDisplayPageEntryPersistence.countAll();
	}

	/**
	 * Updates the asset display page entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetDisplayPageEntry updateAssetDisplayPageEntry(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		return assetDisplayPageEntryPersistence.update(assetDisplayPageEntry);
	}

	/**
	 * Returns the asset display page entry local service.
	 *
	 * @return the asset display page entry local service
	 */
	public AssetDisplayPageEntryLocalService
		getAssetDisplayPageEntryLocalService() {

		return assetDisplayPageEntryLocalService;
	}

	/**
	 * Sets the asset display page entry local service.
	 *
	 * @param assetDisplayPageEntryLocalService the asset display page entry local service
	 */
	public void setAssetDisplayPageEntryLocalService(
		AssetDisplayPageEntryLocalService assetDisplayPageEntryLocalService) {

		this.assetDisplayPageEntryLocalService =
			assetDisplayPageEntryLocalService;
	}

	/**
	 * Returns the asset display page entry persistence.
	 *
	 * @return the asset display page entry persistence
	 */
	public AssetDisplayPageEntryPersistence
		getAssetDisplayPageEntryPersistence() {

		return assetDisplayPageEntryPersistence;
	}

	/**
	 * Sets the asset display page entry persistence.
	 *
	 * @param assetDisplayPageEntryPersistence the asset display page entry persistence
	 */
	public void setAssetDisplayPageEntryPersistence(
		AssetDisplayPageEntryPersistence assetDisplayPageEntryPersistence) {

		this.assetDisplayPageEntryPersistence =
			assetDisplayPageEntryPersistence;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.asset.display.page.model.AssetDisplayPageEntry",
			assetDisplayPageEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.asset.display.page.model.AssetDisplayPageEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return AssetDisplayPageEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AssetDisplayPageEntry.class;
	}

	protected String getModelClassName() {
		return AssetDisplayPageEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				assetDisplayPageEntryPersistence.getDataSource();

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

	@BeanReference(type = AssetDisplayPageEntryLocalService.class)
	protected AssetDisplayPageEntryLocalService
		assetDisplayPageEntryLocalService;

	@BeanReference(type = AssetDisplayPageEntryPersistence.class)
	protected AssetDisplayPageEntryPersistence assetDisplayPageEntryPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}