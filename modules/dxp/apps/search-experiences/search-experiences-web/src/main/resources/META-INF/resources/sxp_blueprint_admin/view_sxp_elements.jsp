<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPElementsDisplayContext viewSXPElementsDisplayContext = (ViewSXPElementsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT);
%>

<clay:headless-data-set-display
	apiURL="<%= viewSXPElementsDisplayContext.getAPIURL() %>"
	clayDataSetActionDropdownItems="<%= viewSXPElementsDisplayContext.getClayDataSetActionDropdownItems() %>"
	creationMenu="<%= viewSXPElementsDisplayContext.getCreationMenu() %>"
	formName="fm"
	id="<%= SXPBlueprintAdminClayDataSetDisplayNames.SXP_ELEMENTS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>

<div id="<portlet:namespace />addSXPElement">
	<react:component
		module="sxp_blueprint_admin/js/view_sxp_elements/AddSXPElementModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).put(
				"editSXPElementURL",
				PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCRenderCommandName(
					"/sxp_blueprint_admin/edit_sxp_element"
				).buildString()
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
	/>
</div>