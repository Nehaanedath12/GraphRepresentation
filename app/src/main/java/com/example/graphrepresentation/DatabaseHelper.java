package com.example.graphrepresentation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.graphrepresentation.category_company_class.CategoryWiseAsset;
import com.example.graphrepresentation.category_company_class.CompanyWise;

public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Graph.db";
    private static final String TABLE_COMPANY_WISE = "company_wise ";
    private static final String TABLE_CATEGORY_WISE = "category_wise ";


    private static final String S_NAME = "sName";
    private static final String PURCHASE_VALUE = "fPurchaseValue";
    private static final String DEPRECIATED_VALUE = "depreciatedValue";
    private static final String CURRENT_VALUE = "CurrentValue";

    private static final String S_CATEGORY = "sCategory";
    private static final String F_DEPRECIATED_VALUE = "fDepValue";
    private static final String F_CURRENT_VALUE = "fCurrentValue";




    public static final String CREATE_TABLE_COMPANY_WISE = " create table if not exists " + TABLE_COMPANY_WISE + " (" +

            "" + S_NAME + " TEXT DEFAULT NULL , " +
            "" + PURCHASE_VALUE + " VARCHAR(30) DEFAULT null, " +
            "" + DEPRECIATED_VALUE + " VARCHAR(150) DEFAULT null, " +
            "" + CURRENT_VALUE + " TEXT(50) DEFAULT null " + ");";

    public static final String CREATE_TABLE_CATEGORY_WISE = " create table if not exists " + TABLE_CATEGORY_WISE + " (" +

            "" + S_CATEGORY + " TEXT DEFAULT NULL , " +
            "" + PURCHASE_VALUE + " VARCHAR(30) DEFAULT null, " +
            "" + F_DEPRECIATED_VALUE + " VARCHAR(150) DEFAULT null, " +
            "" + F_CURRENT_VALUE + " TEXT(50) DEFAULT null " + ");";
    Context context;
    SQLiteDatabase db;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CREATE_TABLE_COMPANY_WISE);
        db.execSQL(CREATE_TABLE_CATEGORY_WISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean InsertCompanyWise(CompanyWise companyWise) {
        this.db=getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(S_NAME,companyWise.getsName());
        cv.put(PURCHASE_VALUE,companyWise.getfPurchaseValue());
        cv.put(DEPRECIATED_VALUE,companyWise.getDepreciatedValue());
        cv.put(CURRENT_VALUE,companyWise.getCurrentValue());
        long status=db.insert(TABLE_COMPANY_WISE,null,cv);
        return status!=-1;
    }

    public boolean InsertCategoryWiseAsset(CategoryWiseAsset categoryWiseAsset) {
        this.db=getReadableDatabase();
        this.db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(S_CATEGORY,categoryWiseAsset.getsCategory());
        cv.put(PURCHASE_VALUE,categoryWiseAsset.getfPurchaseValue());
        cv.put(F_DEPRECIATED_VALUE,categoryWiseAsset.getfDepValue());
        cv.put(F_CURRENT_VALUE,categoryWiseAsset.getfCurrentValue());
        long status=db.insert(TABLE_CATEGORY_WISE,null,cv);
        return status!=-1;
    }

    public Cursor getCompanyWiseValues() {
        this.db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_COMPANY_WISE,null);
        return  cursor;


    }

    public Cursor getCategoryWiseAsset() {
        this.db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_CATEGORY_WISE,null);
        return  cursor;
    }
}
