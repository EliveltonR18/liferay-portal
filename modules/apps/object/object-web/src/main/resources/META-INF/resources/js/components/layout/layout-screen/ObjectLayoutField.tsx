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

import React, {useContext, useRef} from 'react';
import {DropTargetMonitor, useDrag, useDrop} from 'react-dnd';

import {ItemTypes} from '../../../utils/itemTypes';
import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import {TObjectField} from '../types';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import RequiredLabel from './RequiredLabel';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

interface IObjectLayoutFieldProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	columnIndex: number;
	objectFieldId: number;
	rowIndex: number;
	tabIndex: number;
}

const ObjectLayoutField: React.FC<IObjectLayoutFieldProps> = ({
	boxIndex,
	columnIndex,
	objectFieldId,
	rowIndex,
	tabIndex,
}) => {
	const ref = useRef<HTMLDivElement>(null);
	const [
		{
			objectFields,
			objectLayout: {objectLayoutTabs},
		},
		dispatch,
	] = useContext(LayoutContext);
	const {FIELD} = ItemTypes;

	const objectField = objectFields.find(
		({id}) => id === objectFieldId
	) as TObjectField;
	const {objectLayoutBoxes} = objectLayoutTabs[tabIndex];
	const {objectLayoutRows} = objectLayoutBoxes[boxIndex];
	const {objectLayoutColumns} = objectLayoutRows[rowIndex];
	const objectLayoutColumn = objectLayoutColumns[columnIndex];

	const [{handlerId}, drop] = useDrop({
		accept: [ItemTypes.FIELD],
		collect(monitor) {
			return {
				handlerId: monitor.getHandlerId(),
			};
		},
		hover(item: any, monitor: DropTargetMonitor) {
			if (!ref.current) {
				return;
			}

			const dragIndex = item.columnIndex;
			const hoverIndex = columnIndex;

			if (dragIndex === hoverIndex) {
				return;
			}

			if (
				!objectLayoutColumns.some(
					(column) => column.objectFieldId === item.objectField.id
				)
			) {
				return;
			}

			const hoverBoundingRect = ref.current?.getBoundingClientRect();

			const hoverMiddleX =
				(hoverBoundingRect.left - hoverBoundingRect.right) / 2;

			const clientOffset = monitor.getClientOffset();

			const hoverClientX = clientOffset!.x - hoverBoundingRect.right;

			if (dragIndex < hoverIndex && hoverClientX < hoverMiddleX) {
				return;
			}

			if (dragIndex > hoverIndex && hoverClientX > hoverMiddleX) {
				return;
			}

			dispatch({
				payload: {
					boxIndex,
					dragIndex,
					hoverIndex,
					rowIndex,
					tabIndex,
				},
				type: TYPES.CHANGE_OBJECT_LAYOUT_COLUMN_INDEX,
			});

			item.columnIndex = hoverIndex;
		},
	});

	const [{opacity}, drag] = useDrag({
		collect: (monitor) => ({
			opacity: monitor.isDragging() ? 0.4 : 1,
		}),
		item: {
			boxIndex,
			columnIndex,
			objectField,
			objectFieldSize: objectLayoutColumn?.size,
			rowIndex,
			tabIndex,
			type: FIELD,
		},
	});

	drag(drop(ref));

	return (
		<>
			<Panel
				data-handler-id={handlerId}
				key={`field_${objectFieldId}`}
				ref={ref}
				style={{opacity}}
			>
				<Panel.SimpleBody
					contentRight={
						<DropdownWithDeleteButton
							onClick={() => {
								dispatch({
									payload: {
										boxIndex,
										columnIndex,
										objectFieldId,
										rowIndex,
										tabIndex,
									},
									type: TYPES.DELETE_OBJECT_LAYOUT_FIELD,
								});
							}}
						/>
					}
					title={objectField?.label[defaultLanguageId]}
				>
					<small className="text-secondary">
						{objectField?.type} |{' '}
					</small>

					<RequiredLabel required={objectField?.required} />
				</Panel.SimpleBody>
			</Panel>
		</>
	);
};

export default ObjectLayoutField;
