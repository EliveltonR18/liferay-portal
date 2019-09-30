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

package com.liferay.journal.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMNavigationHelper;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.comparator.FolderArticleArticleIdComparator;
import com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.FolderArticleTitleComparator;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.journal.web.internal.search.EntriesChecker;
import com.liferay.journal.web.internal.search.EntriesMover;
import com.liferay.journal.web.internal.search.JournalSearcher;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.util.JournalPortletUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletPreferences portletPreferences, TrashHelper trashHelper) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)_request.getAttribute(
				JournalWebConfiguration.class.getName());
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteEntries");

							boolean trashEnabled = _trashHelper.isTrashEnabled(
								_themeDisplay.getScopeGroupId());

							dropdownItem.setIcon(
								trashEnabled ? "trash" : "times");

							String label = "delete";

							if (trashEnabled) {
								label = "recycle-bin";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(_request, label));

							dropdownItem.setQuickAction(true);
						}));

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "expireEntries");
						dropdownItem.setIcon("time");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "expire"));
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "moveEntries");
						dropdownItem.setIcon("change");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "move"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String[] getAddMenuFavItems() throws PortalException {
		if (_addMenuFavItems != null) {
			return _addMenuFavItems;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		String key = JournalPortletUtil.getAddMenuFavItemKey(
			_liferayPortletRequest, _liferayPortletResponse);

		List<String> addMenuFavItemsList = new ArrayList<>();

		String[] addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL, key, new String[0]);

		for (DDMStructure ddmStructure : getDDMStructures()) {
			if (ArrayUtil.contains(
					addMenuFavItems, ddmStructure.getStructureKey())) {

				addMenuFavItemsList.add(ddmStructure.getStructureKey());
			}
		}

		_addMenuFavItems = ArrayUtil.toStringArray(addMenuFavItemsList);

		return _addMenuFavItems;
	}

	public int getAddMenuFavItemsLength() throws PortalException {
		String[] addMenuFavItems = getAddMenuFavItems();

		return addMenuFavItems.length;
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = ActionUtil.getArticle(_request);

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() throws Exception {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		long groupId = ParamUtil.getLong(_request, "groupId");
		String articleId = ParamUtil.getString(_request, "articleId");
		double version = ParamUtil.getDouble(_request, "version");

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			groupId, articleId, version);

		if (article == null) {
			return _articleDisplay;
		}

		int page = ParamUtil.getInteger(_request, "page");

		_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
			article, null, null, getLanguageId(), page,
			new PortletRequestModel(
				_liferayPortletRequest, _liferayPortletResponse),
			_themeDisplay);

		return _articleDisplay;
	}

	public List<Locale> getAvailableArticleLocales() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return Collections.emptyList();
		}

		List<Locale> availableLocales = new ArrayList<>();

		for (String languageId : article.getAvailableLanguageIds()) {
			availableLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return availableLocales;
	}

	public String[] getCharactersBlacklist() throws PortalException {
		JournalServiceConfiguration journalServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				JournalServiceConfiguration.class,
				_themeDisplay.getCompanyId());

		return journalServiceConfiguration.charactersblacklist();
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("navigation", StringPool.BLANK);
		clearResultsURL.setParameter(
			"status", String.valueOf(WorkflowConstants.STATUS_ANY));

		return clearResultsURL.toString();
	}

	public SearchContainer<MBMessage> getCommentsSearchContainer()
		throws PortalException {

		SearchContainer<MBMessage> searchContainer = new SearchContainer(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, null);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_liferayPortletRequest.getHttpServletRequest());

		searchContext.setAttribute(
			Field.CLASS_NAME_ID,
			PortalUtil.getClassNameId(JournalArticle.class));

		searchContext.setAttribute("discussion", Boolean.TRUE);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setStart(searchContainer.getStart());

		List<MBMessage> mbMessages = new ArrayList<>();

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(
				entryClassPK);

			mbMessages.add(mbMessage);
		}

		searchContainer.setResults(mbMessages);

		searchContainer.setTotal(hits.getLength());

		return searchContainer;
	}

	public int getCommentsTotal() throws PortalException {
		SearchContainer<MBMessage> commentsSearchContainer =
			getCommentsSearchContainer();

		return commentsSearchContainer.getTotal();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu() {
			{
				setHelpText(
					LanguageUtil.get(
						_request,
						"you-can-customize-this-menu-or-see-all-you-have-by-" +
							"clicking-more"));

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(), getFolderId(),
						ActionKeys.ADD_FOLDER)) {

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								_liferayPortletResponse.createRenderURL(),
								"mvcPath", "/edit_folder.jsp", "redirect",
								PortalUtil.getCurrentURL(_request), "groupId",
								String.valueOf(_themeDisplay.getScopeGroupId()),
								"parentFolderId",
								String.valueOf(getFolderId()));

							String label = "folder";

							if (getFolder() != null) {
								label = "subfolder";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(_request, label));
						});
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(), getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					List<DDMStructure> ddmStructures = getDDMStructures();

					for (DDMStructure ddmStructure : ddmStructures) {
						Consumer<DropdownItem> consumer = SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									_liferayPortletResponse.createRenderURL(),
									"mvcPath", "/edit_article.jsp", "redirect",
									PortalUtil.getCurrentURL(_request),
									"groupId",
									String.valueOf(
										_themeDisplay.getScopeGroupId()),
									"folderId", String.valueOf(getFolderId()),
									"ddmStructureKey",
									ddmStructure.getStructureKey());

								dropdownItem.setLabel(
									ddmStructure.getUnambiguousName(
										ddmStructures,
										_themeDisplay.getScopeGroupId(),
										_themeDisplay.getLocale()));
							});

						if (ArrayUtil.contains(
								getAddMenuFavItems(),
								ddmStructure.getStructureKey())) {

							addFavoriteDropdownItem(consumer);
						}
						else {
							addRestDropdownItem(consumer);
						}
					}
				}
			}
		};
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(_request, "ddmStructureKey");

		return _ddmStructureKey;
	}

	public String getDDMStructureName() {
		if (_ddmStructureName != null) {
			return _ddmStructureName;
		}

		_ddmStructureName = LanguageUtil.get(_request, "basic-web-content");

		if (Validator.isNull(getDDMStructureKey())) {
			return _ddmStructureName;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			_themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			getDDMStructureKey(), true);

		if (ddmStructure != null) {
			_ddmStructureName = ddmStructure.getName(_themeDisplay.getLocale());
		}

		return _ddmStructureName;
	}

	public long getDDMStructurePrimaryKey() {
		String ddmStructureKey = getDDMStructureKey();

		if (Validator.isNull(ddmStructureKey)) {
			return 0;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			_themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return 0;
		}

		return ddmStructure.getPrimaryKey();
	}

	public List<DDMStructure> getDDMStructures() throws PortalException {
		Integer restrictionType = getRestrictionType();

		return getDDMStructures(restrictionType);
	}

	public List<DDMStructure> getDDMStructures(Integer restrictionType)
		throws PortalException {

		if (_ddmStructures != null) {
			return _ddmStructures;
		}

		if (restrictionType == null) {
			restrictionType = getRestrictionType();
		}

		_ddmStructures = JournalFolderServiceUtil.getDDMStructures(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				_themeDisplay.getScopeGroupId()),
			getFolderId(), restrictionType);

		Locale locale = _themeDisplay.getLocale();

		if (_journalWebConfiguration.journalBrowseByStructuresSortedByName()) {
			_ddmStructures.sort(
				(ddmStructure1, ddmStructure2) -> {
					String name1 = ddmStructure1.getName(locale);
					String name2 = ddmStructure2.getName(locale);

					return name1.compareTo(name2);
				});
		}

		return _ddmStructures;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_request, "ddmTemplateKey");

		return _ddmTemplateKey;
	}

	public String getDefaultLanguageId() throws PortalException {
		JournalArticle article = getArticle();

		if (article != null) {
			return article.getDefaultLanguageId();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale());
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String[] displayViews = getDisplayViews();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(_displayStyle)) {
			_displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				_journalWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, _displayStyle)) {
			portalPreferences.setValue(
				JournalPortletKeys.JOURNAL, "display-style", _displayStyle);

			_request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		if (!ArrayUtil.contains(displayViews, _displayStyle)) {
			_displayStyle = displayViews[0];
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					StringUtil.merge(_journalWebConfiguration.displayViews())));
		}

		return _displayViews;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterStatusDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-status"));
					});

				if (!isNavigationRecent()) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getOrderByDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(_request, "order-by"));
						});
				}
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				if (isNavigationMine()) {
					add(
						labelItem -> {
							ThemeDisplay themeDisplay =
								(ThemeDisplay)_request.getAttribute(
									WebKeys.THEME_DISPLAY);

							User user = themeDisplay.getUser();

							labelItem.setLabel(
								LanguageUtil.get(_request, "owner") + ": " +
									user.getFullName());
						});
				}

				if (isNavigationRecent()) {
					add(
						labelItem -> labelItem.setLabel(
							LanguageUtil.get(_request, "recent")));
				}

				if (isNavigationStructure()) {
					add(
						labelItem -> labelItem.setLabel(
							LanguageUtil.get(_request, "structures") + ": " +
								getDDMStructureName()));
				}

				int status = getStatus();

				if (status != -1) {
					add(
						labelItem -> labelItem.setLabel(
							LanguageUtil.get(_request, "status") + ": " +
								_getStatusLabel(status)));
				}
			}
		};
	}

	public JournalFolder getFolder() {
		if (_folder != null) {
			return _folder;
		}

		_folder = (JournalFolder)_request.getAttribute(WebKeys.JOURNAL_FOLDER);

		if (_folder != null) {
			return _folder;
		}

		long folderId = ParamUtil.getLong(_request, "folderId");

		_folder = JournalFolderLocalServiceUtil.fetchFolder(folderId);

		return _folder;
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		JournalFolder folder = getFolder();

		_folderId = BeanParamUtil.getLong(
			folder, _request, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public JSONArray getFoldersJSONArray() {
		JSONArray jsonArray = _getFoldersJSONArray(
			_themeDisplay.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("children", jsonArray);
		jsonObject.put("icon", "folder");
		jsonObject.put("id", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		jsonObject.put(
			"name", LanguageUtil.get(_themeDisplay.getLocale(), "home"));

		JSONArray rootJSONArray = JSONFactoryUtil.createJSONArray();

		rootJSONArray.put(jsonObject);

		return rootJSONArray;
	}

	public String getFolderTitle() {
		JournalFolder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		return StringPool.BLANK;
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getLanguageId() {
		if (_languageId != null) {
			return _languageId;
		}

		_languageId = ParamUtil.getString(
			_request, "languageId", _themeDisplay.getLanguageId());

		return _languageId;
	}

	public JournalArticle getLatestArticle(JournalArticle journalArticle) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_ANY);

		if (latestArticle != null) {
			return latestArticle;
		}

		return journalArticle;
	}

	public int getMaxAddMenuItems() {
		if (_maxAddMenuItems != null) {
			return _maxAddMenuItems;
		}

		_maxAddMenuItems = _journalWebConfiguration.maxAddMenuItems();

		return _maxAddMenuItems;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		return _navigation;
	}

	public List<NavigationItem> getNavigationBarItems(String currentItem) {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							currentItem.equals("web-content"));
						navigationItem.setHref(
							_liferayPortletResponse.createRenderURL());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "web-content"));
					});

				Group group = _themeDisplay.getScopeGroup();

				if (!group.isLayout()) {
					add(
						navigationItem -> {
							navigationItem.setHref(_getStructuresURL());
							navigationItem.setLabel(
								LanguageUtil.get(_request, "structures"));
						});

					add(
						navigationItem -> {
							navigationItem.setHref(_getTemplatesURL());
							navigationItem.setLabel(
								LanguageUtil.get(_request, "templates"));
						});
				}

				if (_journalWebConfiguration.showFeeds() &&
					PortalUtil.isRSSFeedsEnabled()) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								currentItem.equals("feeds"));
							navigationItem.setHref(_getFeedsURL());
							navigationItem.setLabel(
								LanguageUtil.get(_request, "feeds"));
						});
				}
			}
		};
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-col", "modified-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		if (isNavigationRecent()) {
			return "desc";
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public String[] getOrderColumns() {
		String[] orderColumns = {"display-date", "modified-date", "title"};

		if (!_journalWebConfiguration.journalArticleForceAutogenerateId()) {
			orderColumns = ArrayUtil.append(orderColumns, "id");
		}

		return orderColumns;
	}

	public String getOriginalAuthor(JournalArticle article) {
		long classPK = JournalArticleAssetRenderer.getClassPK(article);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), classPK);

		if (assetEntry != null) {
			return assetEntry.getUserName();
		}

		return article.getUserName();
	}

	public long getParentFolderId() {
		if (_parentFolderId != null) {
			return _parentFolderId;
		}

		_parentFolderId = ParamUtil.getLong(
			_request, "parentFolderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _parentFolderId;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String navigation = ParamUtil.getString(_request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter(
				"navigation", HtmlUtil.escapeJS(getNavigation()));
		}

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		if (isNavigationStructure()) {
			portletURL.setParameter("ddmStructureKey", getDDMStructureKey());
		}

		String status = ParamUtil.getString(_request, "status");

		if (Validator.isNotNull(status)) {
			portletURL.setParameter("status", String.valueOf(getStatus()));
		}

		String delta = ParamUtil.getString(_request, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(_request, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		if (!isShowEditActions()) {
			portletURL.setParameter(
				"showEditActions", String.valueOf(isShowEditActions()));
		}

		String tabs1 = getTabs1();

		if (Validator.isNotNull(tabs1)) {
			portletURL.setParameter("tabs1", tabs1);
		}

		return portletURL;
	}

	public int getRestrictionType() {
		if (_restrictionType != null) {
			return _restrictionType;
		}

		JournalFolder folder = getFolder();

		if (folder != null) {
			_restrictionType = folder.getRestrictionType();
		}
		else {
			_restrictionType = JournalFolderConstants.RESTRICTION_TYPE_INHERIT;
		}

		return _restrictionType;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));
		portletURL.setParameter(
			"showEditActions", String.valueOf(isShowEditActions()));

		return portletURL.toString();
	}

	public SearchContainer getSearchContainer(boolean showVersions)
		throws PortalException {

		SearchContainer articleSearchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(orderByComparator);
		articleSearchContainer.setOrderByType(getOrderByType());

		if (!showVersions) {
			EntriesChecker entriesChecker = new EntriesChecker(
				_liferayPortletRequest, _liferayPortletResponse, _trashHelper);

			entriesChecker.setCssClass("entry-selector");
			entriesChecker.setRememberCheckBoxStateURLRegex(
				StringBundler.concat(
					"^(?!.*", _liferayPortletResponse.getNamespace(),
					"redirect).*(folderId=", getFolderId(), ")"));

			articleSearchContainer.setRowChecker(entriesChecker);

			if (!BrowserSnifferUtil.isMobile(_request)) {
				EntriesMover entriesMover = new EntriesMover(
					_trashHelper.isTrashEnabled(
						_themeDisplay.getScopeGroupId()));

				articleSearchContainer.setRowMover(entriesMover);
			}
		}

		if (isNavigationMine() || isNavigationRecent()) {
			boolean includeOwner = true;

			if (isNavigationMine()) {
				includeOwner = false;
			}

			if (isNavigationRecent()) {
				articleSearchContainer.setOrderByCol("modified-date");
				articleSearchContainer.setOrderByType(getOrderByType());
			}

			int total = JournalArticleServiceUtil.getGroupArticlesCount(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getGroupArticles(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMStructureKey())) {
			int total = JournalArticleServiceUtil.getArticlesCountByStructureId(
				_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				getStatus());

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getArticlesByStructureId(
				_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				getStatus(), articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMTemplateKey())) {
			List<Long> folderIds = new ArrayList<>(1);

			if (getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(getFolderId());
			}

			int total = JournalArticleServiceUtil.searchCount(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.search(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (isSearch()) {
			List<Long> folderIds = new ArrayList<>(1);

			if (getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(getFolderId());
			}

			if (_journalWebConfiguration.journalArticlesSearchWithIndex()) {
				boolean orderByAsc = false;

				if (Objects.equals(getOrderByType(), "asc")) {
					orderByAsc = true;
				}

				Sort sort = null;

				if (Objects.equals(getOrderByCol(), "display-date")) {
					sort = new Sort("displayDate", Sort.LONG_TYPE, !orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "id")) {
					sort = new Sort(
						Field.getSortableFieldName(Field.ARTICLE_ID),
						Sort.STRING_TYPE, !orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "modified-date")) {
					sort = new Sort(
						Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "title")) {
					sort = new Sort("title", Sort.STRING_TYPE, !orderByAsc);
				}

				LinkedHashMap<String, Object> params = new LinkedHashMap<>();

				params.put("expandoAttributes", getKeywords());

				Indexer indexer = null;

				if (!showVersions) {
					indexer = JournalSearcher.getInstance();
				}
				else {
					indexer = IndexerRegistryUtil.getIndexer(
						JournalArticle.class);
				}

				SearchContext searchContext = buildSearchContext(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(), folderIds,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					getDDMStructureKey(), getDDMTemplateKey(), getKeywords(),
					params, articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(), sort, showVersions);

				Hits hits = indexer.search(searchContext);

				int total = hits.getLength();

				articleSearchContainer.setTotal(total);

				List results = new ArrayList<>();

				Document[] documents = hits.getDocs();

				for (Document document : documents) {
					String className = document.get(Field.ENTRY_CLASS_NAME);
					long classPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					if (className.equals(JournalArticle.class.getName())) {
						JournalArticle article = null;

						if (!showVersions) {
							article =
								JournalArticleLocalServiceUtil.
									fetchLatestArticle(
										classPK, WorkflowConstants.STATUS_ANY,
										false);
						}
						else {
							String articleId = document.get(Field.ARTICLE_ID);
							long groupId = GetterUtil.getLong(
								document.get(Field.GROUP_ID));
							double version = GetterUtil.getDouble(
								document.get(Field.VERSION));

							article =
								JournalArticleLocalServiceUtil.fetchArticle(
									groupId, articleId, version);
						}

						results.add(article);
					}
					else if (className.equals(JournalFolder.class.getName())) {
						JournalFolder folder =
							JournalFolderLocalServiceUtil.getFolder(classPK);

						results.add(folder);
					}
				}

				articleSearchContainer.setResults(results);
			}
			else {
				int total = JournalArticleServiceUtil.searchCount(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(), folderIds,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT, getKeywords(),
					-1.0, getDDMStructureKey(), getDDMTemplateKey(), null, null,
					getStatus(), null);

				articleSearchContainer.setTotal(total);

				List results = JournalArticleServiceUtil.search(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(), folderIds,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT, getKeywords(),
					-1.0, getDDMStructureKey(), getDDMTemplateKey(), null, null,
					getStatus(), null, articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(),
					articleSearchContainer.getOrderByComparator());

				articleSearchContainer.setResults(results);
			}
		}
		else {
			int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(
				_themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus());

			articleSearchContainer.setTotal(total);

			OrderByComparator<Object> folderOrderByComparator = null;

			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(getOrderByCol(), "display-date")) {
				folderOrderByComparator =
					new FolderArticleDisplayDateComparator(orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "id")) {
				folderOrderByComparator = new FolderArticleArticleIdComparator(
					orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "title")) {
				folderOrderByComparator = new FolderArticleTitleComparator(
					orderByAsc);
			}

			List results = JournalFolderServiceUtil.getFoldersAndArticles(
				_themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus(),
				_themeDisplay.getLocale(), articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), folderOrderByComparator);

			articleSearchContainer.setResults(results);
		}

		return articleSearchContainer;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		int defaultStatus = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				_themeDisplay.getCompanyId(),
				_themeDisplay.getScopeGroupId()) ||
			isNavigationMine()) {

			defaultStatus = WorkflowConstants.STATUS_ANY;
		}

		_status = ParamUtil.getInteger(_request, "status", defaultStatus);

		return _status;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_request, "tabs1");

		return _tabs1;
	}

	public String getTitleMapAsJSON() throws PortalException {
		if (_article == null) {
			_article = getArticle();
		}

		Map<Locale, String> titleMap = _article.getTitleMap();

		StringBundler sb = new StringBundler(2 + 8 * titleMap.size());

		sb.append("{");

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			sb.append(StringPool.QUOTE);
			sb.append(entry.getKey());
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.COLON);
			sb.append(StringPool.QUOTE);
			sb.append(HtmlUtil.escapeJS(entry.getValue()));
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.COMMA);
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer articleSearch = getSearchContainer(false);

		return articleSearch.getTotal();
	}

	public int getVersionsTotal() throws PortalException {
		SearchContainer articleSearch = getSearchContainer(true);

		return articleSearch.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				if (ArrayUtil.contains(getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	public boolean hasCommentsResults() throws PortalException {
		if (getCommentsTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean hasResults() throws PortalException {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	public boolean hasVersionsResults() throws PortalException {
		if (getVersionsTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean isCommentsTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "comments") ||
			(hasCommentsResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
	}

	public boolean isDisabled() throws PortalException {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		if (!isNavigationHome() ||
			(getStatus() != WorkflowConstants.STATUS_ANY)) {

			return false;
		}

		return true;
	}

	public boolean isNavigationHome() {
		if (Objects.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationMine() {
		if (Objects.equals(getNavigation(), "mine")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationRecent() {
		if (Objects.equals(getNavigation(), "recent")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationStructure() {
		if (Objects.equals(getNavigation(), "structure")) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton() throws PortalException {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if ((stagingGroupHelper.isLocalLiveGroup(group) ||
			 stagingGroupHelper.isRemoteLiveGroup(group)) &&
			stagingGroupHelper.isStagedPortlet(
				group, JournalPortletKeys.JOURNAL)) {

			return false;
		}

		if (JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), getFolderId(),
				ActionKeys.ADD_FOLDER) ||
			JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), getFolderId(),
				ActionKeys.ADD_ARTICLE)) {

			return true;
		}

		return false;
	}

	public boolean isShowEditActions() {
		if (_showEditActions != null) {
			return _showEditActions;
		}

		_showEditActions = ParamUtil.getBoolean(
			_request, "showEditActions", true);

		return _showEditActions;
	}

	public boolean isShowInfoButton() {
		if (Validator.isNotNull(getDDMStructureKey())) {
			return false;
		}

		if (isNavigationMine()) {
			return false;
		}

		if (isNavigationRecent()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowPublishArticleAction(JournalArticle article) {
		if (article == null) {
			return false;
		}

		StagedModelDataHandler<JournalArticle> stagedModelDataHandler =
			(StagedModelDataHandler<JournalArticle>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					JournalArticle.class.getName());

		if (_isShowPublishAction() &&
			ArrayUtil.contains(
				stagedModelDataHandler.getExportableStatuses(),
				article.getStatus())) {

			return true;
		}

		return false;
	}

	public boolean isShowPublishFolderAction(JournalFolder folder) {
		if (folder == null) {
			return false;
		}

		return _isShowPublishAction();
	}

	public boolean isShowSearch() throws PortalException {
		if (hasResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	public boolean isVersionsTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "versions") ||
			(hasVersionsResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
	}

	public boolean isWebContentTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "web-content") ||
			(hasResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String ddmStructureKey, String ddmTemplateKey, String keywords,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort,
		boolean showVersions) {

		String articleId = null;
		String title = null;
		String description = null;
		String content = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleId = keywords;
			title = keywords;
			description = keywords;
			content = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andOperator);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ARTICLE_ID, articleId);
		attributes.put(Field.CLASS_NAME_ID, classNameId);
		attributes.put(Field.CONTENT, content);
		attributes.put(Field.DESCRIPTION, description);
		attributes.put(Field.STATUS, getStatus());
		attributes.put(Field.TITLE, title);
		attributes.put("ddmStructureKey", ddmStructureKey);
		attributes.put("ddmTemplateKey", ddmTemplateKey);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setIncludeDiscussions(
			GetterUtil.getBoolean(params.get("includeDiscussions"), true));

		if (params != null) {
			keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		searchContext.setAttribute("head", !showVersions);
		searchContext.setAttribute("latest", !showVersions);
		searchContext.setAttribute("params", params);

		if (!showVersions) {
			searchContext.setAttribute("showNonindexable", Boolean.TRUE);
		}

		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private String _getFeedsURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_feeds.jsp");
		portletURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	private Consumer<DropdownItem> _getFilterNavigationDropdownItem(
		final boolean active, final String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(active);

			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "navigation",
				navigation, "folderId",
				String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID),
				"showEditActions", String.valueOf(isShowEditActions()));

			dropdownItem.setLabel(LanguageUtil.get(_request, navigation));
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					_getFilterNavigationDropdownItem(
						isNavigationHome(), "all"));
				add(
					_getFilterNavigationDropdownItem(
						isNavigationMine(), "mine"));
				add(
					_getFilterNavigationDropdownItem(
						isNavigationRecent(), "recent"));

				StringBundler sb = new StringBundler(
					isNavigationStructure() ? 4 : 1);

				sb.append(LanguageUtil.get(_request, "structures"));

				if (isNavigationStructure()) {
					sb.append(StringPool.COLON);
					sb.append(StringPool.SPACE);
					sb.append(getDDMStructureName());
				}

				add(
					dropdownItem -> {
						dropdownItem.setActive(isNavigationStructure());
						dropdownItem.putData(
							"action", "openStructuresSelector");
						dropdownItem.setLabel(sb.toString());
					});
			}
		};
	}

	private List<DropdownItem> _getFilterStatusDropdownItems() {
		return new DropdownItemList() {
			{
				for (int status : _getStatuses()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(getStatus() == status);
							dropdownItem.setHref(
								getPortletURL(), "status",
								String.valueOf(status));
							dropdownItem.setLabel(_getStatusLabel(status));
						});
				}
			}
		};
	}

	private JSONArray _getFoldersJSONArray(long groupId, long folderId) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<JournalFolder> folders = JournalFolderLocalServiceUtil.getFolders(
			groupId, folderId);

		for (JournalFolder folder : folders) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getFoldersJSONArray(
				groupId, folder.getFolderId());

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			jsonObject.put("icon", "folder");
			jsonObject.put("id", folder.getFolderId());
			jsonObject.put("name", folder.getName());

			if (folder.getFolderId() == getParentFolderId()) {
				jsonObject.put("selected", true);
			}

			if (folder.getFolderId() == getFolderId()) {
				jsonObject.put("disabled", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private Consumer<DropdownItem> _getOrderByDropdownItem(
		final String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(LanguageUtil.get(_request, orderByCol));
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				for (String orderColumn : getOrderColumns()) {
					add(_getOrderByDropdownItem(orderColumn));
				}
			}
		};
	}

	private List<Integer> _getStatuses() {
		List<Integer> statuses = new ArrayList<>();

		statuses.add(WorkflowConstants.STATUS_ANY);
		statuses.add(WorkflowConstants.STATUS_DRAFT);

		int workflowDefinitionLinksCount =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(),
					JournalFolder.class.getName());

		if (workflowDefinitionLinksCount > 0) {
			statuses.add(WorkflowConstants.STATUS_PENDING);
			statuses.add(WorkflowConstants.STATUS_DENIED);
		}

		statuses.add(WorkflowConstants.STATUS_SCHEDULED);
		statuses.add(WorkflowConstants.STATUS_APPROVED);
		statuses.add(WorkflowConstants.STATUS_EXPIRED);

		return statuses;
	}

	private String _getStatusLabel(int status) {
		String label = WorkflowConstants.getStatusLabel(status);

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			label = "with-expired-versions";
		}

		return LanguageUtil.get(_request, label);
	}

	private String _getStructuresURL() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDisplay.getId());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_liferayPortletRequest,
			PortletProviderUtil.getPortletId(
				DDMStructure.class.getName(), PortletProvider.Action.VIEW),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		portletURL.setParameter(
			"refererPortletName", JournalPortletKeys.JOURNAL);
		portletURL.setParameter(
			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
		portletURL.setParameter(
			"scopeTitle", LanguageUtil.get(_request, "structures"));
		portletURL.setParameter(
			"showAncestorScopes",
			String.valueOf(
				_journalWebConfiguration.showAncestorScopesByDefault()));
		portletURL.setParameter("showCacheableInput", Boolean.TRUE.toString());
		portletURL.setParameter("showManageTemplates", Boolean.TRUE.toString());

		return portletURL.toString();
	}

	private String _getTemplatesURL() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDisplay.getId());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_liferayPortletRequest,
			PortletProviderUtil.getPortletId(
				DDMTemplate.class.getName(), PortletProvider.Action.VIEW),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view_template.jsp");
		portletURL.setParameter(
			"navigationStartsOn", DDMNavigationHelper.VIEW_TEMPLATES);
		portletURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		portletURL.setParameter(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
		portletURL.setParameter(
			"resourceClassNameId",
			String.valueOf(PortalUtil.getClassNameId(JournalArticle.class)));
		portletURL.setParameter(
			"refererPortletName", JournalPortletKeys.JOURNAL);
		portletURL.setParameter(
			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
		portletURL.setParameter(
			"scopeTitle", LanguageUtil.get(_request, "templates"));
		portletURL.setParameter(
			"showAncestorScopes",
			String.valueOf(
				_journalWebConfiguration.showAncestorScopesByDefault()));
		portletURL.setParameter("showCacheableInput", Boolean.TRUE.toString());
		portletURL.setParameter("showHeader", Boolean.TRUE.toString());

		return portletURL.toString();
	}

	private boolean _isShowPublishAction() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		long scopeGroupId = _themeDisplay.getScopeGroupId();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		try {
			if (GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId,
					ActionKeys.EXPORT_IMPORT_PORTLET_INFO) &&
				stagingGroupHelper.isStagingGroup(scopeGroupId) &&
				stagingGroupHelper.isStagedPortlet(
					scopeGroupId, JournalPortletKeys.JOURNAL)) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An exception occured when checking if the publish " +
						"action should be displayed",
					pe);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalDisplayContext.class);

	private String[] _addMenuFavItems;
	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private String _ddmStructureKey;
	private String _ddmStructureName;
	private List<DDMStructure> _ddmStructures;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _keywords;
	private String _languageId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Integer _maxAddMenuItems;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private Long _parentFolderId;
	private final PortalPreferences _portalPreferences;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Integer _restrictionType;
	private Boolean _showEditActions;
	private Integer _status;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}