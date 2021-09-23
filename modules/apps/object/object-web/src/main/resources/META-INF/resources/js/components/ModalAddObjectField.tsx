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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import useForm from '../hooks/useForm';
import {
	firstLetterLowercase,
	firstLetterUppercase,
	removeAllSpecialCharacters,
} from '../utils/string';
import Input from './form/Input';
import Select from './form/Select';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
}

type TPicklist = {
	id: string;
	name: string;
};

type TNormalizeName = (str: string) => string;

type TInitialValues = {
	label: string;
	name: string;
	type: string;
	listTypeDefinitionId: number;
	required: boolean;
};

const objectFieldTypes = [
	'BigDecimal',
	'Boolean',
	'Date',
	'Double',
	'Integer',
	'Long',
	'Picklist',
	'String',
];

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const headers = new Headers({
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

const normalizeName: TNormalizeName = (str) => {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return firstLetterLowercase(removeAllSpecialCharacters(join));
};

const ModalAddObjectField: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [error, setError] = useState<string>('');
	const [picklist, setPicklist] = useState<TPicklist[]>([]);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const onSubmit = async ({
		label,
		listTypeDefinitionId,
		name,
		required,
		type,
	}: TInitialValues) => {
		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: null,
				label: {
					[defaultLanguageId]: label,
				},
				listTypeDefinitionId,
				name: name || normalizeName(label),
				required,
				type: type === 'Picklist' ? 'String' : type,
			}),
			headers,
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = await response.json();

			setError(title);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.label) {
			errors.label = Liferay.Language.get('required');
		}

		if (!values.label && !values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.type) {
			errors.type = Liferay.Language.get('required');
		}

		if (values.type === 'Picklist' && !values.listTypeDefinitionId) {
			errors.listTypeDefinitionId = Liferay.Language.get('required');
		}

		return errors;
	};

	const handleOpenObjectFieldModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectField', handleOpenObjectFieldModal);

		return () => {
			Liferay.detach('addObjectField', handleOpenObjectFieldModal);
		};
	}, []);

	const initialValues: TInitialValues = {
		label: '',
		listTypeDefinitionId: 0,
		name: '',
		required: false,
		type: '',
	};

	const {errors, handleChange, handleSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayForm onSubmit={handleSubmit}>
						<ClayModal.Header>
							{Liferay.Language.get('new-field')}
						</ClayModal.Header>

						<ClayModal.Body>
							{error && (
								<ClayAlert displayType="danger">
									{error}
								</ClayAlert>
							)}

							<Input
								error={errors.label}
								id="objectFieldLabel"
								label={Liferay.Language.get('label')}
								name="label"
								onChange={handleChange}
								required
								value={values.label}
							/>

							<Input
								error={errors.name || errors.label}
								id="objectFieldName"
								label={Liferay.Language.get('field-name')}
								name="name"
								onChange={handleChange}
								required
								value={
									values.name || normalizeName(values.label)
								}
							/>

							<Select
								error={errors.type}
								label={Liferay.Language.get('type')}
								onChange={async ({target: {value}}: any) => {
									const selectedType =
										objectFieldTypes[Number(value) - 1];

									if (selectedType === 'Picklist') {
										const result = await Liferay.Util.fetch(
											'/o/headless-admin-list-type/v1.0/list-type-definitions',
											{
												headers,
												method: 'GET',
											}
										);

										const {
											items = [],
										} = await result.json();

										setPicklist(
											items.map(
												({id, name}: TPicklist) => ({
													id,
													name,
												})
											)
										);
									}

									handleChange({
										target: {
											name: 'type',
											value: selectedType,
										},
									} as any);
								}}
								options={objectFieldTypes}
								required
							/>

							{!!picklist.length && (
								<Select
									error={errors.listTypeDefinitionId}
									label={Liferay.Language.get('picklist')}
									onChange={({target: {value}}: any) => {
										handleChange({
											target: {
												name: 'listTypeDefinitionId',
												value:
													picklist[Number(value) - 1]
														.id,
											},
										} as any);
									}}
									options={picklist.map(({name}) => name)}
									required
								/>
							)}

							<ClayToggle
								label={Liferay.Language.get('mandatory')}
								onToggle={() => {
									handleChange({
										target: {
											name: 'required',
											value: !values.required,
										},
									} as any);
								}}
								toggled={values.required}
							/>
						</ClayModal.Body>

						<ClayModal.Footer
							last={
								<ClayButton.Group key={1} spaced>
									<ClayButton
										displayType="secondary"
										onClick={() => onClose()}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>

									<ClayButton
										displayType="primary"
										type="submit"
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</ClayForm>
				</ClayModal>
			)}
		</>
	);
};

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	return (
		<ClayModalProvider>
			<ModalAddObjectField apiURL={apiURL} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
