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
import {usePrevious} from '@liferay/frontend-js-react-web';
import {normalizeFieldName} from 'data-engine-js-components-web';
import React, {useEffect, useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const SearchLocation = ({
	defaultLanguageId,
	disabled,
	editingLanguageId,
	fieldName,
	id,
	label,
	localizable,
	localizedValue,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	readOnly,
	shouldUpdateValue,
	syncDelay,
	value: initialValue,
	...otherProps
}) => {
	const [value, setValue] = useSyncValue(
		initialValue,
		syncDelay,
		editingLanguageId
	);

	const inputRef = useRef(null);

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	const fieldDetailsId = id ? id + '_fieldDetails' : name + '_fieldDetails';

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId && localizable) {
			const newValue =
				localizedValue[editingLanguageId] !== undefined
					? localizedValue[editingLanguageId]
					: localizedValue[defaultLanguageId];
			setValue(newValue);
		}
	}, [
		defaultLanguageId,
		editingLanguageId,
		localizable,
		localizedValue,
		prevEditingLanguageId,
		setValue,
	]);

	useEffect(() => {
		if (
			fieldName === 'fieldReference' &&
			inputRef.current &&
			inputRef.current.value !== initialValue &&
			(inputRef.current.value === '' || shouldUpdateValue)
		) {
			setValue(initialValue);
			onChange({target: {value: initialValue}});
		}
	}, [
		initialValue,
		inputRef,
		fieldName,
		onChange,
		setValue,
		shouldUpdateValue,
	]);

	return (
		<FieldBase
			{...otherProps}
			fieldName={fieldName}
			id={id}
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
		>
			<ClayInput
				aria-labelledby={id}
				className="ddm-field-text"
				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={disabled}
				id={fieldDetailsId}
				lang={editingLanguageId}
				name={name}
				onBlur={(event) => {
					if (fieldName == 'fieldReference') {
						onBlur({target: {value: initialValue}});
					}
					else {
						onBlur(event);
					}
				}}
				onChange={(event) => {
					const {value} = event.target;

					if (
						fieldName === 'fieldReference' ||
						fieldName === 'name'
					) {
						event.target.value = normalizeFieldName(value);
					}
					else if (fieldName === 'inputMaskFormat') {
						event.target.value = value.replace(/[1-8]/g, '');
					}

					setValue(event.target.value);
					onChange(event);
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				ref={inputRef}
				type="text"
				value={value}
			/>
		</FieldBase>
	);
};

export default SearchLocation;
