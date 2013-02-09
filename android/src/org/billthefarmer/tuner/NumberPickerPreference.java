////////////////////////////////////////////////////////////////////////////////
//
//  Tuner - An Android Tuner written in Java.
//
//  Copyright (C) 2013	Bill Farmer
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License along
//  with this program; if not, write to the Free Software Foundation, Inc.,
//  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//
//  Bill Farmer	 william j farmer [at] yahoo [dot] co [dot] uk.
//
///////////////////////////////////////////////////////////////////////////////

package org.billthefarmer.tuner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class NumberPickerPreference extends DialogPreference
{
    private final int mMaxValue;
    private final int mMinValue;

    private int mValue;

    private NumberPicker mPicker;

    // Constructors

    public NumberPickerPreference(Context context, AttributeSet attrs)
    {
	super(context, attrs);

	TypedArray a =
	    context.obtainStyledAttributes(attrs,
					   R.styleable.NumberPickerPreference);
	mMaxValue = a.getInteger(R.styleable.NumberPickerPreference_maxValue, 450);
	mMinValue = a.getInteger(R.styleable.NumberPickerPreference_minValue, 430);
	 a.recycle();
    }

    @Override
    protected void onBindDialogView(View view)
    {
	super.onBindDialogView(view);

	mPicker = (NumberPicker)view.findViewById(R.id.picker);

	mPicker.setMaxValue(mMaxValue);
	mPicker.setMinValue(mMinValue);

	mPicker.setValue(mValue);

	mPicker.setFormatter(new NumberPicker.Formatter()
	    {
		@SuppressLint("DefaultLocale")
		@Override
		public String format(int value)
		{
		    return String.format("%dHz", value);
		}
	    });

	mPicker.setWrapSelectorWheel(false);
	mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    // On get default value

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
	return a.getInteger(index, 0);
    }

    // On set initial value

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue,
				     Object defaultValue)
    {
	if (restorePersistedValue)
	{
	    // Restore existing state

	    mValue = getPersistedInt(0);
	}

	else
	{
	    // Set default state from the XML attribute

	    mValue = (Integer) defaultValue;
	    persistInt(mValue);
	}
    }

    // On dialog closed

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
	// When the user selects "OK", persist the new value

	if (positiveResult)
	{
	    mValue = mPicker.getValue();
	    persistInt(mValue);
	}
    }

    // Get value

    protected int getValue()
    {
	return mValue;
    }
}