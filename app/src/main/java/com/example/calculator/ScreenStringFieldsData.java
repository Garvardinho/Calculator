package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

public class ScreenStringFieldsData implements Parcelable {
    TextView mInput;
    TextView mResult;

    public ScreenStringFieldsData() {}

    protected ScreenStringFieldsData(Parcel in) {
        mInput = (TextView) in.readValue(TextView.class.getClassLoader());
        mResult = (TextView) in.readValue(TextView.class.getClassLoader());
    }

    public static final Creator<ScreenStringFieldsData> CREATOR = new Creator<ScreenStringFieldsData>() {
        @Override
        public ScreenStringFieldsData createFromParcel(Parcel in) {
            return new ScreenStringFieldsData(in);
        }

        @Override
        public ScreenStringFieldsData[] newArray(int size) {
            return new ScreenStringFieldsData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mInput);
        dest.writeValue(mResult);
    }

    public TextView getInput() {
        return mInput;
    }

    public void setInput(TextView mInput) {
        this.mInput = mInput;
    }

    public TextView getResult() {
        return mResult;
    }

    public void setResult(TextView mResult) {
        this.mResult = mResult;
    }
}
