package com.example.graphrepresentation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import com.example.graphrepresentation.category_company_class.CategoryWiseAsset;
import com.example.graphrepresentation.category_company_class.CompanyWise;
import com.example.graphrepresentation.DatabaseHelper;
import com.example.graphrepresentation.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    Button button;

    BarChart barChart,barChart_category;
    List<BarEntry> barEntryList;
    List<BarEntry>barEntryListCategory;

    DatabaseHelper helper;
    List<CompanyWise>companyWiseList;
    List<CategoryWiseAsset>categoryWiseAssetslist;
    ArrayList<String> company_nameList;
    ArrayList<String> category_nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        barChart =findViewById(R.id.barChart_img);
        barChart_category=findViewById(R.id.barChart_img_category);
        button=findViewById(R.id.pie);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),PieActivity.class));
//            }
//        });

        barEntryList=new ArrayList<>();
        companyWiseList=new ArrayList<>();
        company_nameList = new ArrayList<>();

        barEntryListCategory=new ArrayList<>();
        categoryWiseAssetslist=new ArrayList<>();
        category_nameList=new ArrayList<>();

        helper=new DatabaseHelper(this);
        barChartFnCompany(barChart);
        barChartFnCategory(barChart_category);
    }

    private void barChartFnCategory(BarChart barChart_category) {
        String category="current value";
        Cursor cursor=helper.getCategoryWiseAsset();
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount()>0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    CategoryWiseAsset categoryWiseAsset = new CategoryWiseAsset(cursor.getString(cursor.getColumnIndex("sCategory")),
                            cursor.getString(cursor.getColumnIndex("fPurchaseValue")),
                            cursor.getString(cursor.getColumnIndex("fDepValue")),
                            cursor.getString(cursor.getColumnIndex("fCurrentValue")));
                    float currentValue = Float.parseFloat(categoryWiseAsset.getfCurrentValue());
                    categoryWiseAssetslist.add(categoryWiseAsset);
                    barEntryListCategory.add(new BarEntry(i, currentValue));
                    cursor.moveToNext();
                    category_nameList.add(categoryWiseAsset.getsCategory());
                }


            }
        }
        settingBarData(barEntryListCategory, category_nameList,barChart_category,category);

    }


    private void barChartFnCompany(BarChart barChart) {
        String company="current value";
        Cursor cursor=helper.getCompanyWiseValues();
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                for (int i=0;i<cursor.getCount();i++) {
                    CompanyWise companyWise = new CompanyWise(cursor.getString(cursor.getColumnIndex("sName")),
                            cursor.getString(cursor.getColumnIndex("fPurchaseValue")),
                            cursor.getString(cursor.getColumnIndex("depreciatedValue")),
                            cursor.getString(cursor.getColumnIndex("CurrentValue")));
                    float currentValue = Float.parseFloat(companyWise.getCurrentValue());
                    companyWiseList.add(companyWise);
                    barEntryList.add(new BarEntry(i, currentValue));
                    cursor.moveToNext();
                    company_nameList.add(companyWise.getsName());
                }

            }
        }
        settingBarData(barEntryList, company_nameList, barChart, company);

    }

    private void settingBarData(List<BarEntry> barEntryList, ArrayList<String> nameList, BarChart barChart, String name) {
        BarDataSet barDataSet=new BarDataSet(barEntryList,name);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barChart.setData(barData);
        Description description=new Description();
        description.setText("rate");
        barChart.setDescription(description);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameList));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(nameList.size());
        xAxis.setLabelRotationAngle(270);
        barChart.invalidate();
    }
}