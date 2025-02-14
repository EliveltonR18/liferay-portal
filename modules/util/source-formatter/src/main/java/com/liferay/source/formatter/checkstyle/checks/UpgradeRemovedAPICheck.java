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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class UpgradeRemovedAPICheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) ||
			AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {

			return;
		}

		String upgradeFromVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_FROM_VERSION);
		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION);

		try {
			JSONObject upgradeFromJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeFromVersion);
			JSONObject upgradeToJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeToVersion);

			List<String> removedImportNames = _getRemovedImportNames(
				detailAST, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);

			_checkRemovedMethods(
				detailAST, removedImportNames, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);
		}
		catch (Exception exception) {
		}
	}

	private void _checkRemovedMethods(
		DetailAST detailAST, List<String> removedImportNames,
		JSONObject upgradeFromJavaClassesJSONObject,
		JSONObject upgradeToJavaClassesJSONObject, String upgradeToVersion) {

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.getFirstChild();

			if (dotDetailAST.getType() != TokenTypes.DOT) {
				continue;
			}

			DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();
			DetailAST lastChildDetailAST = dotDetailAST.getLastChild();

			if ((firstChildDetailAST.getType() != TokenTypes.IDENT) ||
				(lastChildDetailAST.getType() != TokenTypes.IDENT)) {

				continue;
			}

			String variableTypeName = getVariableTypeName(
				firstChildDetailAST, firstChildDetailAST.getText(), false,
				true);

			if (!variableTypeName.startsWith("com.liferay.") ||
				removedImportNames.contains(variableTypeName)) {

				continue;
			}

			String methodName = lastChildDetailAST.getText();

			List<String> parameterTypes = _getParameterTypes(
				methodCallDetailAST);

			MethodStatus fromUpgradeMethodStatus = _getMethodStatus(
				upgradeFromJavaClassesJSONObject, variableTypeName, methodName,
				parameterTypes);

			if (!fromUpgradeMethodStatus.equals(MethodStatus.METHOD_FOUND)) {
				continue;
			}

			MethodStatus toUpgradeMethodStatus = _getMethodStatus(
				upgradeToJavaClassesJSONObject, variableTypeName, methodName,
				parameterTypes);

			if (toUpgradeMethodStatus.equals(MethodStatus.NO_CLASS_FOUND)) {
				log(
					methodCallDetailAST, _MSG_CLASS_NOT_FOUND, variableTypeName,
					upgradeToVersion);
			}
			else if (toUpgradeMethodStatus.equals(
						MethodStatus.NO_METHOD_FOUND)) {

				log(
					methodCallDetailAST, _MSG_METHOD_NOT_FOUND, methodName,
					variableTypeName, upgradeToVersion);
			}
		}
	}

	private synchronized JSONObject _getJavaClassesJSONObject(String version)
		throws Exception {

		JSONObject javaClassesJSONObject = _javaClassesJSONObjectMap.get(
			version);

		if (javaClassesJSONObject != null) {
			return javaClassesJSONObject;
		}

		JSONObject portalJSONObject =
			SourceFormatterUtil.getPortalJSONObjectByVersion(version);

		if (portalJSONObject.has("javaClasses")) {
			javaClassesJSONObject = portalJSONObject.getJSONObject(
				"javaClasses");
		}
		else {
			javaClassesJSONObject = new JSONObjectImpl();
		}

		_javaClassesJSONObjectMap.put(version, javaClassesJSONObject);

		return javaClassesJSONObject;
	}

	private MethodStatus _getMethodStatus(
		JSONObject jsonObject, String fullyQualifiedClassName,
		String methodName, List<String> parameterTypes) {

		JSONObject classJSONObject = jsonObject.getJSONObject(
			fullyQualifiedClassName);

		if (classJSONObject == null) {
			return MethodStatus.NO_CLASS_FOUND;
		}

		JSONArray methodsJSONArray = classJSONObject.getJSONArray("methods");

		if (methodsJSONArray != null) {
			Iterator<JSONObject> iterator = methodsJSONArray.iterator();

			outerLoop:
			while (iterator.hasNext()) {
				JSONObject methodJSONObject = iterator.next();

				if (!methodName.equals(methodJSONObject.getString("name"))) {
					continue;
				}

				JSONArray parametersJSONArray = methodJSONObject.getJSONArray(
					"parameters");

				if (parametersJSONArray == null) {
					if (parameterTypes.isEmpty()) {
						return MethodStatus.METHOD_FOUND;
					}

					continue;
				}

				if (parametersJSONArray.length() != parameterTypes.size()) {
					continue;
				}

				for (int i = 0; i < parameterTypes.size(); i++) {
					String actualType = parameterTypes.get(i);
					String methodType = parametersJSONArray.getString(i);

					if (!StringUtil.equalsIgnoreCase(actualType, methodType) &&
						!methodType.equals("Object")) {

						continue outerLoop;
					}
				}

				return MethodStatus.METHOD_FOUND;
			}
		}

		JSONArray extendedClassNamesJSONArray = classJSONObject.getJSONArray(
			"extendedClassNames");

		if (extendedClassNamesJSONArray != null) {
			Iterator<String> iterator = extendedClassNamesJSONArray.iterator();

			while (iterator.hasNext()) {
				String extendedClassName = iterator.next();

				if (!extendedClassName.startsWith("com.liferay.") &&
					Objects.equals(
						_getMethodStatus(
							jsonObject, extendedClassName, methodName,
							parameterTypes),
						MethodStatus.METHOD_FOUND)) {

					return MethodStatus.METHOD_FOUND;
				}
			}
		}

		return MethodStatus.NO_METHOD_FOUND;
	}

	private String _getParameterType(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.IDENT) {
			return getVariableTypeName(
				detailAST, detailAST.getText(), true, true);
		}

		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			return "java.lang.String";
		}

		if (detailAST.getType() == TokenTypes.CHAR_LITERAL) {
			return "char";
		}

		if ((detailAST.getType() == TokenTypes.LITERAL_FALSE) ||
			(detailAST.getType() == TokenTypes.LITERAL_TRUE) ||
			(detailAST.getType() == TokenTypes.LNOT)) {

			return "boolean";
		}

		if (detailAST.getType() == TokenTypes.PLUS) {
			DetailAST stringLiteralDetailAST = detailAST.findFirstToken(
				TokenTypes.STRING_LITERAL);

			if (stringLiteralDetailAST != null) {
				return "java.lang.String";
			}

			return null;
		}

		if (detailAST.getType() == TokenTypes.LITERAL_NEW) {
			String parameterType = null;

			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				parameterType = firstChildDetailAST.getText();
			}
			else {
				parameterType = _getParameterType(firstChildDetailAST);
			}

			if (parameterType == null) {
				return parameterType;
			}

			DetailAST curDetailAST = firstChildDetailAST.getNextSibling();

			while (true) {
				if (curDetailAST.getType() != TokenTypes.ARRAY_DECLARATOR) {
					return parameterType;
				}

				parameterType += "[]";

				curDetailAST = curDetailAST.getFirstChild();
			}
		}

		if (detailAST.getType() == TokenTypes.INDEX_OP) {
			String parameterType = _getParameterType(detailAST.getFirstChild());

			if (parameterType != null) {
				parameterType = StringUtil.replaceLast(parameterType, "[]", "");
			}

			return parameterType;
		}

		if (detailAST.getType() == TokenTypes.TYPECAST) {
			return getTypeName(detailAST, true, true);
		}

		if (ArrayUtil.contains(
				UNARY_OPERATOR_TOKEN_TYPES, detailAST.getType())) {

			return _getParameterType(detailAST.getFirstChild());
		}

		if (detailAST.getType() == TokenTypes.LITERAL_THIS) {
			DetailAST parentDetailAST = getParentWithTokenType(
				detailAST, TokenTypes.CLASS_DEF);

			if (parentDetailAST == null) {
				return null;
			}

			DetailAST identDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.IDENT);

			return identDetailAST.getText();
		}

		if (ArrayUtil.contains(
				CONDITIONAL_OPERATOR_TOKEN_TYPES, detailAST.getType()) ||
			ArrayUtil.contains(
				RELATIONAL_OPERATOR_TOKEN_TYPES, detailAST.getType())) {

			return "boolean";
		}

		if (detailAST.getType() == TokenTypes.NUM_DOUBLE) {
			return "double";
		}

		if (detailAST.getType() == TokenTypes.NUM_FLOAT) {
			return "float";
		}

		if (detailAST.getType() == TokenTypes.NUM_LONG) {
			return "long";
		}

		if (detailAST.getType() == TokenTypes.NUM_INT) {
			return "int";
		}

		if ((detailAST.getType() == TokenTypes.LITERAL_BOOLEAN) ||
			(detailAST.getType() == TokenTypes.LITERAL_BYTE) ||
			(detailAST.getType() == TokenTypes.LITERAL_DOUBLE) ||
			(detailAST.getType() == TokenTypes.LITERAL_FLOAT) ||
			(detailAST.getType() == TokenTypes.LITERAL_INT) ||
			(detailAST.getType() == TokenTypes.LITERAL_LONG) ||
			(detailAST.getType() == TokenTypes.LITERAL_SHORT)) {

			return detailAST.getText();
		}

		if (detailAST.getType() == TokenTypes.QUESTION) {
			return _getParameterType(detailAST.getLastChild());
		}

		return null;
	}

	private List<String> _getParameterTypes(DetailAST detailAST) {
		List<String> parameterTypes = new ArrayList<>();

		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		for (DetailAST exprDetailAST : exprDetailASTList) {
			parameterTypes.add(
				_getParameterType(exprDetailAST.getFirstChild()));
		}

		return parameterTypes;
	}

	private List<String> _getRemovedImportNames(
		DetailAST detailAST, JSONObject fromUpgradeJavaClassesJSONObject,
		JSONObject toUpgradeJavaClassesJSONObject, String upgradeToVersion) {

		List<String> removedImportNames = new ArrayList<>();

		List<String> importNames = getImportNames(detailAST);

		for (String importName : importNames) {
			JSONObject fromUpgradeClassJSONObject =
				fromUpgradeJavaClassesJSONObject.getJSONObject(importName);
			JSONObject toUpgradeClassJSONObject =
				toUpgradeJavaClassesJSONObject.getJSONObject(importName);

			if ((fromUpgradeClassJSONObject != null) &&
				(toUpgradeClassJSONObject == null)) {

				log(
					detailAST, _MSG_CLASS_NOT_FOUND, importName,
					upgradeToVersion);

				removedImportNames.add(importName);
			}
		}

		return removedImportNames;
	}

	private static final String _MSG_CLASS_NOT_FOUND = "class.not.found";

	private static final String _MSG_METHOD_NOT_FOUND = "method.not.found";

	private final Map<String, JSONObject> _javaClassesJSONObjectMap =
		new HashMap<>();

	private enum MethodStatus {

		METHOD_FOUND, NO_CLASS_FOUND, NO_METHOD_FOUND

	}

}