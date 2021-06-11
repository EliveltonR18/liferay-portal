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

import {ClayInput} from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import {SettingsContext} from 'dynamic-data-mapping-form-builder';
import React, {useEffect, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import usePlaces from './usePlaces.es';

const parse = (value, defaultValue) => {
	try {
		return JSON.parse(value);
	}
	catch (e) {
		return defaultValue !== undefined ? defaultValue : {};
	}
};

const Field = ({
	disabled,
	editingLanguageId,
	id,
	label,
	name,
	onBlur,
	onChange,
	onFocus,
	parsedValue,
	placeholder,
	readOnly,
	visibleField,
	...otherProps
}) => {
	return (
		<FieldBase
			{...otherProps}
			label={label[editingLanguageId] ?? label}
			name={name}
			readOnly={readOnly}
		>
			<ClayInput
				className="ddm-field-text"
				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={disabled}
				id={id}
				name={name}
				onBlur={onBlur}
				onChange={(event) => {
					onChange(
						event,
						JSON.stringify({
							...parsedValue,
							[visibleField]: event.target.value,
						})
					);
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				type="text"
				value={parsedValue[visibleField] ?? ''}
			/>
		</FieldBase>
	);
};

const Main = ({
	disabled,
	googlePlacesAPIKey,
	label,
	labels,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	readOnly,
	settingsContext,
	value,
	visibleFields,
	...otherProps
}) => {
	usePlaces({
		elementId: `${name}#place`,
		googlePlacesAPIKey,
		isReadOnly: readOnly,
	});

	const [availableLabels, setAvailableLabels] = useState();
	const [availableVisibleFields, setAvailableVisibleFields] = useState([]);
	const currentVisibleFields = Array.isArray(visibleFields)
		? visibleFields
		: parse(visibleFields, []);

	const {editingLanguageId} = useFormState();

	const parsedValue = parse(value, {});

	useEffect(() => {
		if (settingsContext) {
			const options = SettingsContext.getSettingsContextProperty(
				settingsContext,
				'visibleFields',
				'options'
			);

			setAvailableLabels(
				options.reduce((accumulator, currentOption) => {
					return {
						...accumulator,
						[currentOption.value]: currentOption.label,
					};
				}, {})
			);

			setAvailableVisibleFields(options.map((option) => option.value));
		}
		else {
			setAvailableLabels(labels ?? {});
			setAvailableVisibleFields(currentVisibleFields);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div>
			<Field
				{...otherProps}
				disabled={disabled}
				editingLanguageId={editingLanguageId}
				id={`${name}#place`}
				label={label}
				name={`${name}#place`}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
				parsedValue={parsedValue}
				placeholder={placeholder}
				readOnly={readOnly}
				visibleField="place"
			/>

			{availableVisibleFields.length > 0 &&
				availableVisibleFields.map((visibleField) => {
					if (currentVisibleFields.includes(visibleField)) {
						const visibleFieldName = name + '#' + visibleField;

						return (
							<Field
								{...otherProps}
								disabled={disabled}
								editingLanguageId={editingLanguageId}
								key={visibleFieldName}
								label={availableLabels[visibleField]}
								name={visibleFieldName}
								onBlur={onBlur}
								onChange={onChange}
								onFocus={onFocus}
								parsedValue={parsedValue}
								placeholder={placeholder}
								readOnly={readOnly}
								visibleField={visibleField}
							/>
						);
					}
				})}

			<ClayInput name={name} type="hidden" value={value} />
		</div>
	);
};

Main.displayName = 'SearchLocation';

export default Main;
