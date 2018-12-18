package com.example.skopal.foodme.layouts.footprint;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Area;
import com.anychart.core.ui.Crosshair;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.ScaleStackMode;
import com.anychart.graphics.vector.Stroke;
import com.example.skopal.foodme.MainActivity;
import com.example.skopal.foodme.R;
import com.example.skopal.foodme.layouts.footprint.AdviceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Footprint extends Fragment {

    private String backgroundColor;
    private String colorWhite;
    private String meatColor;
    private String fishColor;
    private String dairyColor;
    private String vegetableColor;

    public Footprint() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_footprint, container, false);

        MainActivity activity = ((MainActivity) getActivity());
        if (activity != null) {
            activity.setActionBarTitle(getString(R.string.title_footprint));
            activity.showSpinner("");
        }

        Button button = view.findViewById(R.id.footprint_advice_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = ((MainActivity) getActivity());
                if (activity != null) {
                    activity.hideSpinner();
                    activity.changeScreen(new AdviceFragment(), R.id.main_frame, true);
                }
            }
        });


        Context context = getContext();
        if (context != null) {
            this.backgroundColor = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.backgroundColor)).substring(2, 8);
            this.colorWhite = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorWhite)).substring(2, 8);
            this.meatColor = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorChartMeat)).substring(2, 8);
            this.fishColor = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorChartFish)).substring(2, 8);
            this.dairyColor = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorChartDairy)).substring(2, 8);
            this.vegetableColor = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorChartVegetables)).substring(2, 8);
        }

        AnyChartView anyChartView = view.findViewById(R.id.chart_footprint);
        anyChartView.setProgressBar(view.findViewById(R.id.loading_frame));

        Cartesian areaChart = AnyChart.area();
        areaChart.title(getString(R.string.title_footprint));
        anyChartView.setBackgroundColor(this.backgroundColor);
        areaChart.background().fill(this.backgroundColor);
        areaChart.animation(true);

        Crosshair crosshair = areaChart.crosshair();
        crosshair.enabled(true);
        crosshair.xStroke(this.colorWhite,
                1d, null, (String) null, (String) null)
                .yStroke((Stroke) null, 0d, null, (String) null, (String) null)
                .zIndex(39d);
        //crosshair.yLabel(0).enabled(true);
        areaChart.yScale().stackMode(ScaleStackMode.VALUE);


        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("June", 29.3, 17.9, 12.4, 4.8));
        seriesData.add(new CustomDataEntry("July", 30.6, 17.2, 13.1, 5.4));
        seriesData.add(new CustomDataEntry("August", 21.316, 12.204, 11.823, 3.457));
        seriesData.add(new CustomDataEntry("September", 20.3, 10.342, 13.23, 4.0));
        seriesData.add(new CustomDataEntry("October", 15.3, 10.577, 13.518, 7.0));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Data = set.mapAs("{ x: 'month', value: 'meat' }");
        Mapping series2Data = set.mapAs("{ x: 'month', value: 'fish' }");
        Mapping series3Data = set.mapAs("{ x: 'month', value: 'dairy' }");
        Mapping series4Data = set.mapAs("{ x: 'month', value: 'vegetables' }");

        Area series1 = areaChart.area(series1Data);
        series1.name("Meat");
        series1.stroke("0 #fff");
        series1.color(this.meatColor);

        Area series2 = areaChart.area(series2Data);
        series2.name("Fish");
        series2.stroke("0 #fff");
        series2.color(this.fishColor);

        Area series3 = areaChart.area(series3Data);
        series3.name("Dairy");
        series3.stroke("0 #fff");
        series3.color(this.dairyColor);

        Area series4 = areaChart.area(series4Data);
        series4.name("Vegetables");
        series4.stroke("0 #fff");
        series4.color(this.vegetableColor);

        areaChart.legend().enabled(true);
        areaChart.legend().fontSize(10d);
        areaChart.legend().padding(0d, 0d, 10d, 0d);
        areaChart.xAxis(0).title(false);
        areaChart.yAxis(0).title(getString(R.string.title_footprint_chart_x_axis));

        anyChartView.setChart(areaChart);

        if (activity != null) {
            activity.hideSpinner();
        }

        return view;
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String month, Number meat, Number fish, Number dairy, Number vegetables) {
            super(month, meat);
            setValue("fish", fish);
            setValue("dairy", dairy);
            setValue("vegetables", vegetables);
        }
    }
}