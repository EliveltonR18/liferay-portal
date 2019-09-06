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

package com.liferay.polls.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.polls.service.persistence.PollsChoicePersistence;
import com.liferay.polls.service.persistence.PollsQuestionFinder;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.polls.service.persistence.PollsVotePersistence;
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
 * Provides the base implementation for the polls question local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.polls.service.impl.PollsQuestionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.polls.service.impl.PollsQuestionLocalServiceImpl
 * @generated
 */
public abstract class PollsQuestionLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements PollsQuestionLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>PollsQuestionLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.polls.service.PollsQuestionLocalServiceUtil</code>.
	 */

	/**
	 * Adds the polls question to the database. Also notifies the appropriate model listeners.
	 *
	 * @param pollsQuestion the polls question
	 * @return the polls question that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PollsQuestion addPollsQuestion(PollsQuestion pollsQuestion) {
		pollsQuestion.setNew(true);

		return pollsQuestionPersistence.update(pollsQuestion);
	}

	/**
	 * Creates a new polls question with the primary key. Does not add the polls question to the database.
	 *
	 * @param questionId the primary key for the new polls question
	 * @return the new polls question
	 */
	@Override
	@Transactional(enabled = false)
	public PollsQuestion createPollsQuestion(long questionId) {
		return pollsQuestionPersistence.create(questionId);
	}

	/**
	 * Deletes the polls question with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param questionId the primary key of the polls question
	 * @return the polls question that was removed
	 * @throws PortalException if a polls question with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public PollsQuestion deletePollsQuestion(long questionId)
		throws PortalException {

		return pollsQuestionPersistence.remove(questionId);
	}

	/**
	 * Deletes the polls question from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pollsQuestion the polls question
	 * @return the polls question that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public PollsQuestion deletePollsQuestion(PollsQuestion pollsQuestion) {
		return pollsQuestionPersistence.remove(pollsQuestion);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			PollsQuestion.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return pollsQuestionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsQuestionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return pollsQuestionPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsQuestionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return pollsQuestionPersistence.findWithDynamicQuery(
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
		return pollsQuestionPersistence.countWithDynamicQuery(dynamicQuery);
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

		return pollsQuestionPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public PollsQuestion fetchPollsQuestion(long questionId) {
		return pollsQuestionPersistence.fetchByPrimaryKey(questionId);
	}

	/**
	 * Returns the polls question matching the UUID and group.
	 *
	 * @param uuid the polls question's UUID
	 * @param groupId the primary key of the group
	 * @return the matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	@Override
	public PollsQuestion fetchPollsQuestionByUuidAndGroupId(
		String uuid, long groupId) {

		return pollsQuestionPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the polls question with the primary key.
	 *
	 * @param questionId the primary key of the polls question
	 * @return the polls question
	 * @throws PortalException if a polls question with the primary key could not be found
	 */
	@Override
	public PollsQuestion getPollsQuestion(long questionId)
		throws PortalException {

		return pollsQuestionPersistence.findByPrimaryKey(questionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(pollsQuestionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(PollsQuestion.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("questionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			pollsQuestionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(PollsQuestion.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("questionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(pollsQuestionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(PollsQuestion.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("questionId");
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
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<PollsQuestion>() {

				@Override
				public void performAction(PollsQuestion pollsQuestion)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, pollsQuestion);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(PollsQuestion.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return pollsQuestionLocalService.deletePollsQuestion(
			(PollsQuestion)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return pollsQuestionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the polls questions matching the UUID and company.
	 *
	 * @param uuid the UUID of the polls questions
	 * @param companyId the primary key of the company
	 * @return the matching polls questions, or an empty list if no matches were found
	 */
	@Override
	public List<PollsQuestion> getPollsQuestionsByUuidAndCompanyId(
		String uuid, long companyId) {

		return pollsQuestionPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of polls questions matching the UUID and company.
	 *
	 * @param uuid the UUID of the polls questions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching polls questions, or an empty list if no matches were found
	 */
	@Override
	public List<PollsQuestion> getPollsQuestionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsQuestion> orderByComparator) {

		return pollsQuestionPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the polls question matching the UUID and group.
	 *
	 * @param uuid the polls question's UUID
	 * @param groupId the primary key of the group
	 * @return the matching polls question
	 * @throws PortalException if a matching polls question could not be found
	 */
	@Override
	public PollsQuestion getPollsQuestionByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return pollsQuestionPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the polls questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsQuestionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of polls questions
	 */
	@Override
	public List<PollsQuestion> getPollsQuestions(int start, int end) {
		return pollsQuestionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of polls questions.
	 *
	 * @return the number of polls questions
	 */
	@Override
	public int getPollsQuestionsCount() {
		return pollsQuestionPersistence.countAll();
	}

	/**
	 * Updates the polls question in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param pollsQuestion the polls question
	 * @return the polls question that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PollsQuestion updatePollsQuestion(PollsQuestion pollsQuestion) {
		return pollsQuestionPersistence.update(pollsQuestion);
	}

	/**
	 * Returns the polls question local service.
	 *
	 * @return the polls question local service
	 */
	public PollsQuestionLocalService getPollsQuestionLocalService() {
		return pollsQuestionLocalService;
	}

	/**
	 * Sets the polls question local service.
	 *
	 * @param pollsQuestionLocalService the polls question local service
	 */
	public void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {

		this.pollsQuestionLocalService = pollsQuestionLocalService;
	}

	/**
	 * Returns the polls question persistence.
	 *
	 * @return the polls question persistence
	 */
	public PollsQuestionPersistence getPollsQuestionPersistence() {
		return pollsQuestionPersistence;
	}

	/**
	 * Sets the polls question persistence.
	 *
	 * @param pollsQuestionPersistence the polls question persistence
	 */
	public void setPollsQuestionPersistence(
		PollsQuestionPersistence pollsQuestionPersistence) {

		this.pollsQuestionPersistence = pollsQuestionPersistence;
	}

	/**
	 * Returns the polls question finder.
	 *
	 * @return the polls question finder
	 */
	public PollsQuestionFinder getPollsQuestionFinder() {
		return pollsQuestionFinder;
	}

	/**
	 * Sets the polls question finder.
	 *
	 * @param pollsQuestionFinder the polls question finder
	 */
	public void setPollsQuestionFinder(
		PollsQuestionFinder pollsQuestionFinder) {

		this.pollsQuestionFinder = pollsQuestionFinder;
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
	 * Returns the polls choice local service.
	 *
	 * @return the polls choice local service
	 */
	public com.liferay.polls.service.PollsChoiceLocalService
		getPollsChoiceLocalService() {

		return pollsChoiceLocalService;
	}

	/**
	 * Sets the polls choice local service.
	 *
	 * @param pollsChoiceLocalService the polls choice local service
	 */
	public void setPollsChoiceLocalService(
		com.liferay.polls.service.PollsChoiceLocalService
			pollsChoiceLocalService) {

		this.pollsChoiceLocalService = pollsChoiceLocalService;
	}

	/**
	 * Returns the polls choice persistence.
	 *
	 * @return the polls choice persistence
	 */
	public PollsChoicePersistence getPollsChoicePersistence() {
		return pollsChoicePersistence;
	}

	/**
	 * Sets the polls choice persistence.
	 *
	 * @param pollsChoicePersistence the polls choice persistence
	 */
	public void setPollsChoicePersistence(
		PollsChoicePersistence pollsChoicePersistence) {

		this.pollsChoicePersistence = pollsChoicePersistence;
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
	 * Returns the polls vote local service.
	 *
	 * @return the polls vote local service
	 */
	public com.liferay.polls.service.PollsVoteLocalService
		getPollsVoteLocalService() {

		return pollsVoteLocalService;
	}

	/**
	 * Sets the polls vote local service.
	 *
	 * @param pollsVoteLocalService the polls vote local service
	 */
	public void setPollsVoteLocalService(
		com.liferay.polls.service.PollsVoteLocalService pollsVoteLocalService) {

		this.pollsVoteLocalService = pollsVoteLocalService;
	}

	/**
	 * Returns the polls vote persistence.
	 *
	 * @return the polls vote persistence
	 */
	public PollsVotePersistence getPollsVotePersistence() {
		return pollsVotePersistence;
	}

	/**
	 * Sets the polls vote persistence.
	 *
	 * @param pollsVotePersistence the polls vote persistence
	 */
	public void setPollsVotePersistence(
		PollsVotePersistence pollsVotePersistence) {

		this.pollsVotePersistence = pollsVotePersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.polls.model.PollsQuestion", pollsQuestionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.polls.model.PollsQuestion");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return PollsQuestionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return PollsQuestion.class;
	}

	protected String getModelClassName() {
		return PollsQuestion.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = pollsQuestionPersistence.getDataSource();

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

	@BeanReference(type = PollsQuestionLocalService.class)
	protected PollsQuestionLocalService pollsQuestionLocalService;

	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;

	@BeanReference(type = PollsQuestionFinder.class)
	protected PollsQuestionFinder pollsQuestionFinder;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(
		type = com.liferay.polls.service.PollsChoiceLocalService.class
	)
	protected com.liferay.polls.service.PollsChoiceLocalService
		pollsChoiceLocalService;

	@BeanReference(type = PollsChoicePersistence.class)
	protected PollsChoicePersistence pollsChoicePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@BeanReference(type = com.liferay.polls.service.PollsVoteLocalService.class)
	protected com.liferay.polls.service.PollsVoteLocalService
		pollsVoteLocalService;

	@BeanReference(type = PollsVotePersistence.class)
	protected PollsVotePersistence pollsVotePersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}