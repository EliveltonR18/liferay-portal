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

import ClayButton from '@clayui/button';
import {ClayToggle} from '@clayui/form';
import {useModal} from '@clayui/modal';
import React, {useContext, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import {ItemTypes} from '../../../utils/itemTypes';
import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import {DragFieldItem, TName, TObjectLayoutRow} from '../types';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import ModalAddObjectLayoutField from './ModalAddObjectLayoutField';
import ObjectLayoutRows from './ObjectLayoutRows';

interface IObjectLayoutBoxProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	collapsable: boolean;
	name: TName;
	objectLayoutRows?: TObjectLayoutRow[];
	tabIndex: number;
}

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

const ObjectLayoutBox: React.FC<IObjectLayoutBoxProps> = ({
	boxIndex,
	collapsable,
	name,
	objectLayoutRows,
	tabIndex,
}) => {
	const ref = useRef<HTMLDivElement>(null);
	const [{isViewOnly}, dispatch] = useContext(LayoutContext);
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const [{handlerId}, drop] = useDrop({
		accept: [ItemTypes.FIELD, ItemTypes.BLOCK],
		collect: (monitor) => ({
			handlerId: monitor.getHandlerId(),
		}),
		drop: ({
			boxIndex: itemBoxIndex,
			columnIndex,
			objectField,
			objectFieldSize,
			rowIndex,
			tabIndex: itemTabIndex,
		}: DragFieldItem) => {
			if (
				objectField?.inLayout &&
				tabIndex === itemTabIndex &&
				boxIndex === itemBoxIndex
			) {
				return;
			}

			dispatch({
				payload: {
					boxIndex: itemBoxIndex,
					columnIndex,
					objectFieldId: objectField.id,
					rowIndex,
					tabIndex: itemTabIndex,
				},
				type: TYPES.DELETE_OBJECT_LAYOUT_FIELD,
			});

			dispatch({
				payload: {
					boxIndex,
					objectFieldId: objectField.id,
					objectFieldSize,
					tabIndex,
				},
				type: TYPES.ADD_OBJECT_LAYOUT_FIELD,
			});
		},
		hover: (item: any, monitor) => {
			if (!ref.current) {
				return;
			}

			const dragIndex = item.boxIndex;
			const hoverIndex = boxIndex;

			if (dragIndex === hoverIndex) {
				return;
			}

			const hoverBoundingRect = ref.current?.getBoundingClientRect();

			const hoverMiddleY =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			const clientOffset = monitor.getClientOffset();

			const hoverClientY = clientOffset!.y - hoverBoundingRect.top;

			if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
				return;
			}

			if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
				return;
			}

			dispatch({
				payload: {
					dragIndex,
					hoverIndex,
					tabIndex,
				},
				type: TYPES.CHANGE_OBJECT_LAYOUT_BOX_INDEX,
			});

			item.boxIndex = hoverIndex;
		},
	});

	const [{opacity}, drag] = useDrag({
		collect: (monitor) => ({
			opacity: monitor.isDragging() ? 0.4 : 1,
		}),
		item: {
			boxIndex,
			collapsable,
			name,
			objectLayoutRows,
			tabIndex,
			type: ItemTypes.BLOCK,
		},
	});

	drop(drag(ref));

	return (
		<>
			<Panel data-handler-id={handlerId} ref={ref} style={{opacity}}>
				<Panel.Header
					contentRight={
						<>
							<ClayToggle
								aria-label={Liferay.Language.get('collapsible')}
								disabled={isViewOnly}
								label={Liferay.Language.get('collapsible')}
								onToggle={(value) => {
									dispatch({
										payload: {
											attribute: {
												key: 'collapsable',
												value,
											},
											boxIndex,
											tabIndex,
										},
										type:
											TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE,
									});
								}}
								toggled={collapsable}
							/>

							<ClayButton
								className="ml-4"
								disabled={isViewOnly}
								displayType="secondary"
								onClick={() => setVisibleModal(true)}
								small
							>
								{Liferay.Language.get('add-field')}
							</ClayButton>

							<DropdownWithDeleteButton
								onClick={() => {
									dispatch({
										payload: {
											boxIndex,
											tabIndex,
										},
										type: TYPES.DELETE_OBJECT_LAYOUT_BOX,
									});
								}}
							/>
						</>
					}
					title={name[defaultLanguageId]}
				/>

				{!!objectLayoutRows?.length && (
					<Panel.Body>
						<ObjectLayoutRows
							boxIndex={boxIndex}
							objectLayoutRows={objectLayoutRows}
							tabIndex={tabIndex}
						/>
					</Panel.Body>
				)}
			</Panel>

			{visibleModal && (
				<ModalAddObjectLayoutField
					boxIndex={boxIndex}
					observer={observer}
					onClose={onClose}
					tabIndex={tabIndex}
				/>
			)}
		</>
	);
};

export default ObjectLayoutBox;
