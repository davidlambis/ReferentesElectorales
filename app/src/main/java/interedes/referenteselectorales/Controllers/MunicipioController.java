package interedes.referenteselectorales.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import interedes.referenteselectorales.Database.SQLiteDBHelper;
import interedes.referenteselectorales.Models.City;

public class MunicipioController {

    private SQLiteDBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    private int Id_Municipio;
    private String Name_Municipio;

    public MunicipioController(Context c) {
        context = c;
    }

    public MunicipioController abrirBaseDeDatos() throws SQLException {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }

    public void InsertMunicipio(long Id_Departamento_Local, int City_Id, String nombreMunicipio) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_DEPARTMENT_ID_ON_CITY, Id_Departamento_Local);
        values.put(SQLiteDBHelper.COLUMN_CITY_ID, City_Id);
        values.put(SQLiteDBHelper.COLUMN_CITY_NAME, nombreMunicipio);
        database.insert(SQLiteDBHelper.TABLE_NAME_CITIES, null, values);
    }

    public int GetIdMunicipioByName(String nombre_municipio) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_CITIES + " where " + SQLiteDBHelper.COLUMN_CITY_NAME + " = '" + nombre_municipio + "'";
        Cursor cursor = database.rawQuery(select, null);
        try {
            if (cursor.moveToFirst()) {
                Id_Municipio = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_CITY_ID));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Id_Municipio;
    }

    public String GetNameMunicipioById(int id_municipio) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_CITIES + " where " + SQLiteDBHelper.COLUMN_CITY_ID + " = '" + id_municipio + "'";
        Cursor cursor = database.rawQuery(select, null);
        try {
            if (cursor.moveToFirst()) {
                Name_Municipio = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_CITY_NAME));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Name_Municipio;
    }


    public ArrayList<City> GetMunicipiosByIdDepartamento(long Id_Departamento) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_CITIES + " where " + SQLiteDBHelper.COLUMN_DEPARTMENT_ID_ON_CITY + " = '" + Id_Departamento + "'";

        Cursor cursor = database.rawQuery(select, null);
        ArrayList<City> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                City municipio = cursorToNote(cursor);
                list.add(municipio);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    /////Asignar datos de la base de datos al metodos Set
    private City cursorToNote(Cursor cursor) {
        City municipio = new City();
        municipio.setCity_Id_Local(cursor.getLong(0));
        municipio.setCity_Id(cursor.getInt(1));
        municipio.setDepartment_Id(cursor.getLong(2));
        municipio.setName(cursor.getString(3));
        return municipio;
    }


}
