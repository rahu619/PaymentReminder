package com.rahul.android.payment;

public class SingleSeries{
    private String _label;
    private float _data;

    public SingleSeries(String _label,float _data){
        this._label=_label;
        this._data=_data;

    }

    public String get_label() {
        return _label;
    }

    public float get_data() {
        return _data;
    }


}

