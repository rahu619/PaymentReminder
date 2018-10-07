package com.rahul.android.payment;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;
import java.util.stream.Collectors;

import Db.BillContent;
import Db.IReminder;
import Db.Reminder;

public class ReportActivity extends Fragment {
    private static PieData data = null;
    IReminder _reminder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_report, container, false);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        _reminder =new Reminder(getContext(),null);

    }

    @Override
    public void onResume(){
        super.onResume();
        LoadData(_reminder.GetReport());
    }

    private void LoadData(List<SingleSeries> _data){

       List<PieEntry> _pieEntries= _data.stream().map(f-> new PieEntry(f.get_data(),f.get_label())).collect(Collectors.toList());

        //format chart
        PieDataSet pds=new PieDataSet(_pieEntries,"Comparison");
        pds.setColors(ColorTemplate.createColors(new int[]{
                Color.parseColor("#75d34a"),
                Color.parseColor("#b24a35")
        }));

        pds.setValueTextSize(25f);
        pds.setValueTextColor(Color.WHITE);
        pds.setValueFormatter(new PercentFormatter());

        pds.setSliceSpace(10f);  //Gap between pieces
        pds.setSelectionShift(15f);
        pds.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

       PieChartComparison(new PieData(pds));
    }

    private void PieChartComparison(PieData data){

            PieChart _pc = getView().findViewById(R.id.pcCompare);
            _pc.invalidate();
            _pc.clear();

            _pc.getLegend().setTextColor(Color.WHITE);
            _pc.setUsePercentValues(true);
            _pc.setDrawHoleEnabled(true);
            _pc.setRotationEnabled(true);
            _pc.setHoleRadius(35);
            _pc.setHoleColor(Color.BLACK);
            _pc.getDescription().setEnabled(true);
            // _pc.setMaxAngle(180);
            _pc.setRotationAngle(180f);
            _pc.setData(data);
            _pc.animateY(2000); //animate chart on displaying
            _pc.invalidate();



    }
}
