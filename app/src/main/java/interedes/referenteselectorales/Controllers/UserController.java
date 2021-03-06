package interedes.referenteselectorales.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import interedes.referenteselectorales.Database.SQLiteDBHelper;
import interedes.referenteselectorales.Models.Sesion;
import interedes.referenteselectorales.Models.User;

public class UserController {

    private SQLiteDBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    int idUser;
    int Estado_Sesion;

    public UserController(Context c) {
        context = c;
    }

    public UserController abrirBaseDeDatos() throws SQLException {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }

    public void InsertUser(int User_Id, long User_Type_Id, String Nombre_Usuario_Tipo, String Referente_Id, String Referente_Name,
                           int Sector_Id, String Name_Municipe, String FirstName, String LastName, String Identification_Card,
                           String Profession, String Birth_Date, String Phone1, String Phone2, String Email, String Address,
                           String Coords_Location, String Have_Vehicle, String Vehicle_Type, String Vehicle_Plate, String Password,
                           String Picture, String Is_Leader, int department_id, int zone_id, int estado_sincronizacion, int id_city) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_USER_ID, User_Id);
        values.put(SQLiteDBHelper.COLUMN_USER_TYPE_ID_REMOTE, User_Type_Id);
        values.put(SQLiteDBHelper.COLUMN_USER_NAME_USER_TYPE, Nombre_Usuario_Tipo);
        values.put(SQLiteDBHelper.COLUMN_REFERENTE_ID, Referente_Id);
        values.put(SQLiteDBHelper.COLUMN_REFERENTE_NAME, Referente_Name);
        values.put(SQLiteDBHelper.COLUMN_SECTOR_ID_REMOTE, Sector_Id);
        values.put(SQLiteDBHelper.COLUMN_MUNICIPE_NAME, Name_Municipe);
        values.put(SQLiteDBHelper.COLUMN_USER_FIRSTNAME, FirstName);
        values.put(SQLiteDBHelper.COLUMN_USER_LASTNAME, LastName);
        values.put(SQLiteDBHelper.COLUMN_USER_IDENTIFICATION_CARD, Identification_Card);
        values.put(SQLiteDBHelper.COLUMN_USER_PROFESSION, Profession);
        values.put(SQLiteDBHelper.COLUMN_USER_BIRTH_DATE, Birth_Date);
        values.put(SQLiteDBHelper.COLUMN_USER_PHONE1, Phone1);
        values.put(SQLiteDBHelper.COLUMN_USER_PHONE2, Phone2);
        values.put(SQLiteDBHelper.COLUMN_USER_EMAIL, Email);
        values.put(SQLiteDBHelper.COLUMN_USER_ADDRESS, Address);
        values.put(SQLiteDBHelper.COLUMN_USER_COORDS_LOCATION, Coords_Location);
        values.put(SQLiteDBHelper.COLUMN_USER_HAVE_VEHICLE, Have_Vehicle);
        values.put(SQLiteDBHelper.COLUMN_USER_VEHICLE_TYPE, Vehicle_Type);
        values.put(SQLiteDBHelper.COLUMN_USER_VEHICLE_PLATE, Vehicle_Plate);
        values.put(SQLiteDBHelper.COLUMN_USER_PASSWORD, Password);
        values.put(SQLiteDBHelper.COLUMN_USER_PICTURE, Picture);
        values.put(SQLiteDBHelper.COLUMN_USER_IS_LEADER, Is_Leader);
        values.put(SQLiteDBHelper.COLUMN_USER_DEPARTMENT_ID, department_id);
        values.put(SQLiteDBHelper.COLUMN_USER_ZONE_ID, zone_id);
        values.put(SQLiteDBHelper.COLUMN_USER_ESTADO_SINCRONIZACION, estado_sincronizacion);
        values.put(SQLiteDBHelper.COLUMN_USER_ID_CITY, id_city);
        database.insert(SQLiteDBHelper.TABLE_NAME_USER, null, values);
    }

    public int GetUserIdByFirstNameAndLastname(String FirstAndLastName) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_USER_FIRSTNAME + " || ' ' || " + SQLiteDBHelper.COLUMN_USER_LASTNAME + " = '" + FirstAndLastName + "'";
        Cursor cursor = database.rawQuery(select, null);
        try {
            if (cursor.moveToFirst()) {
                idUser = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_USER_ID));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return idUser;
    }


    public ArrayList<User> GetUsersByEstadoSincronizacionFalse(int estadosincronizacion) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_USER_ESTADO_SINCRONIZACION + " = " + estadosincronizacion;
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;
    }

    public ArrayList<User> GetUserByEmailAndPassword(String Email, String Password) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_USER_EMAIL + " = '" + Email + "'" + " AND " + SQLiteDBHelper.COLUMN_USER_PASSWORD + " = '" + Password + "'";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUsers(String Email, String Password) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER;
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUserReferenteByIdRemote(String Id_User) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_REFERENTE_ID + " = '" + Id_User + "'";
        ;
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUserReferenteByIdRemoteAndDepartamentoId(String Id_User, int Id_Departamento) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_REFERENTE_ID + " = '" + Id_User + "'" + " AND " + SQLiteDBHelper.COLUMN_USER_DEPARTMENT_ID + " = '" + Id_Departamento + "'";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUserReferenteByIdRemoteAndMunicipioName(String Id_User, String Name_Municipio) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_REFERENTE_ID + " = '" + Id_User + "'" + " AND " + SQLiteDBHelper.COLUMN_MUNICIPE_NAME + " = '" + Name_Municipio + "'";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }


    public ArrayList<User> GetUserReferenteByIdRemoteAndNameZone(String Id_User, int Id_Zone) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_REFERENTE_ID + " = '" + Id_User + "'" + " AND " + SQLiteDBHelper.COLUMN_USER_ZONE_ID + " = '" + Id_Zone + "'";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUserReferenteByIdRemoteAndNameSector(String Id_User, int Id_Sector) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_REFERENTE_ID + " = '" + Id_User + "'" + " AND " + SQLiteDBHelper.COLUMN_SECTOR_ID_REMOTE + " = '" + Id_Sector + "'";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    public ArrayList<User> GetUserByIdRemote(int Id_User) {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_USER + " where " + SQLiteDBHelper.COLUMN_USER_ID + " = '" + Id_User + "'";
        ;
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User user = cursorToNote(cursor);
                list.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }


    /////Asignar datos de la base de datos al metodos Set
    private User cursorToNote(Cursor cursor) {
        User user = new User();
        user.setUser_Id_Local(cursor.getLong(0));
        user.setUser_Id(cursor.getInt(1));
        user.setUser_Type_Id(cursor.getLong(2));
        user.setUser_Type_Id_Remote(cursor.getLong(3));
        user.setName_User_Type(cursor.getString(4));
        user.setReferente_Id(cursor.getLong(5));
        user.setReferente_Id_Local(cursor.getLong(6));
        user.setName_Referente(cursor.getString(7));
        user.setSector_Id(cursor.getLong(8));
        user.setSector_Id_Remote(cursor.getLong(9));
        user.setName_Municipe(cursor.getString(10));
        user.setFirstName(cursor.getString(11));
        user.setLastName(cursor.getString(12));
        user.setIdentification_Card(cursor.getString(13));
        user.setProfession(cursor.getString(14));
        user.setBirth_Date(cursor.getString(15));
        user.setPhone1(cursor.getString(16));
        user.setPhone2(cursor.getString(17));
        user.setEmail(cursor.getString(18));
        user.setAddress(cursor.getString(19));
        user.setCoords_Location(cursor.getString(20));
        user.setHave_Vehicle(cursor.getString(21));
        user.setVehicle_Type(cursor.getString(22));
        user.setVehicle_Plate(cursor.getString(23));
        user.setPassword(cursor.getString(24));
        user.setPicture(cursor.getString(25));
        user.setIs_Leader(cursor.getString(26));
        user.setDepartment_Id(cursor.getInt(27));
        user.setId_Zone(cursor.getInt(28));
        user.setEstado_Sincronizacion(cursor.getInt(29));
        user.setId_City(cursor.getInt(30));
        return user;
    }

    public void InsertSesion(int estado_sesion, int user_id_sesion, String name_user_type_sesion,
                             String firstname_sesion, String lastname_sesion) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_ESTADO_SESION, estado_sesion);
        values.put(SQLiteDBHelper.COLUMN_USER_ID_SESION, user_id_sesion);
        values.put(SQLiteDBHelper.COLUMN_NAME_USER_TYPE_SESION, name_user_type_sesion);
        values.put(SQLiteDBHelper.COLUMN_FIRSTNAME_SESION, firstname_sesion);
        values.put(SQLiteDBHelper.COLUMN_LASTNAME_SESION, lastname_sesion);
        database.insert(SQLiteDBHelper.TABLE_NAME_SESION, null, values);
    }

    public void UpdateSesion(int estado_sesion, int user_id_sesion, String name_user_type_sesion,
                             String firstname_sesion, String lastname_sesion) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_ESTADO_SESION, estado_sesion);
        values.put(SQLiteDBHelper.COLUMN_USER_ID_SESION, user_id_sesion);
        values.put(SQLiteDBHelper.COLUMN_NAME_USER_TYPE_SESION, name_user_type_sesion);
        values.put(SQLiteDBHelper.COLUMN_FIRSTNAME_SESION, firstname_sesion);
        values.put(SQLiteDBHelper.COLUMN_LASTNAME_SESION, lastname_sesion);
        database.update(SQLiteDBHelper.TABLE_NAME_SESION, values, SQLiteDBHelper.COLUMN_USER_ID_SESION + " = '" + user_id_sesion + "'", null);
    }

    public ArrayList<Sesion> GetSesion() {
        dbHelper = new SQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        String select = "select * from " + SQLiteDBHelper.TABLE_NAME_SESION + " order by " + SQLiteDBHelper.COLUMN_ID_SESION + " desc limit 1";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<Sesion> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                Sesion sesion = cursorToNoteSesion(cursor);
                list.add(sesion);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return list;

    }

    private Sesion cursorToNoteSesion(Cursor cursor) {
        Sesion sesion = new Sesion();
        sesion.setId_Sesion(cursor.getInt(0));
        sesion.setEstado_Sesion(cursor.getInt(1));
        sesion.setUser_Id_Sesion(cursor.getInt(2));
        sesion.setName_User_Type_Sesion(cursor.getString(3));
        sesion.setFirstname_Sesion(cursor.getString(4));
        sesion.setLastname_Sesion(cursor.getString(5));
        return sesion;
    }


}
