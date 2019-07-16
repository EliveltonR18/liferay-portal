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

package com.liferay.document.library.opener.internal.upgrade.v1_1_0;

import com.liferay.document.library.opener.internal.upgrade.v1_1_0.util.DLOpenerFileEntryReferenceTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class UpgradeDLOpenerFileEntryReference extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DLOpenerFileEntryReferenceTable.class,
			new AlterTableAddColumn("referenceType STRING null"));

		_addReferenceTypeColumnGoogleValue();
	}

	private void _addReferenceTypeColumnGoogleValue() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update ");
		sb.append(DLOpenerFileEntryReferenceTable.TABLE_NAME);
		sb.append(" set referenceType = 'Google'");

		runSQL(sb.toString());
	}

}