package mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(
    context: Context?,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int): SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("CREATE TABLE PROPIETARIO(CURP VARCHAR(50) PRIMARY KEY NOT NULL, NOMBRE VARCHAR(200) NOT NULL, TELEFONO VARCHAR(50) NOT NULL, EDAD INTEGER NOT NULL);")
            db?.execSQL("CREATE TABLE MASCOTA(ID_MASCOTA INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NOMBRE VARCHAR(200) NOT NULL, RAZA VARCHAR(50) NOT NULL,CURP_PRO VARCHAR(50) NOT NULL, FOREIGN KEY(CURP_PRO) REFERENCES PROPIETARIO(CURP));")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
}