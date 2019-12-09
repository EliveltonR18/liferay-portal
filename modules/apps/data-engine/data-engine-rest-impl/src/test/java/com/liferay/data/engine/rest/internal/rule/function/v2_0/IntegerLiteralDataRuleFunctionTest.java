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

package com.liferay.data.engine.rest.internal.rule.function.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.internal.rule.function.v2_0.util.DataRuleFunctionTestUtil;
import com.liferay.data.engine.rule.function.DataRuleFunction;
import com.liferay.data.engine.rule.function.DataRuleFunctionResult;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcelo Mello
 */
public class IntegerLiteralDataRuleFunctionTest {

	@Test
	public void testInvalidInteger() {
		_dataRecord.setDataRecordValues(
			HashMapBuilder.<String, Object>put(
				"fieldName", "number"
			).build());

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testNullInteger() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", null);
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testOutOfBoundsInteger() {
		_dataRecord.setDataRecordValues(
			HashMapBuilder.<String, Object>put(
				"fieldName", "2312321243423432423424234233234324324242"
			).build());

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testValidInteger() {
		_dataRecord.setDataRecordValues(
			HashMapBuilder.<String, Object>put(
				"fieldName", "132512"
			).build());

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	private static final String _ERROR_CODE = "value-must-be-an-integer-value";

	private static final String _FIELD_TYPE = "numeric";

	private final DataRecord _dataRecord = new DataRecord();
	private final DataRuleFunction _dataRuleFunction =
		new IntegerLiteralDataRuleFunction();

}