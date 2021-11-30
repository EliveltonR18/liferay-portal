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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {logError} from '../utils/logError';
import TooltipTextRenderer from './TooltipTextRenderer';

function DefaultRenderer({options, value}) {
	if (
		typeof value === 'number' ||
		(typeof value === 'string' && !options.truncate) ||
		value === undefined ||
		value === null
	) {
		return <>{value ?? ''}</>;
	}
	else if (value.icon) {
		return <ClayIcon symbol={value.icon} />;
	}
	else if (!!value.iconSymbol && !!value.text) {
		return <TooltipTextRenderer value={value} />;
	}
	else if (value.label) {
		return <>{value.label}</>;
	}
	else if (options.truncate) {
		return (
			<span
				className={classNames(
					'default-renderer__text-truncate',
					'text-truncate'
				)}
			>
				{value}
			</span>
		);
	}

	logError(
		`The object ${JSON.stringify(value)} doesn't match the template schema`
	);

	return null;
}

DefaultRenderer.propTypes = {
	value: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.number,
		PropTypes.shape({
			label: PropTypes.string,
		}),
		PropTypes.shape({
			icon: PropTypes.string,
		}),
		PropTypes.shape({
			iconSymbol: PropTypes.string,
			text: PropTypes.string,
		}),
	]),
};

export default DefaultRenderer;
