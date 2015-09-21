package in.antaragni.ant;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper{

    private static final int DATABASE_Version = 1;
    private static final String DATABASE_Name = "events";

   public DatabaseOpenHelper(Context context){
       super(context,DATABASE_Name,null,DATABASE_Version);
   }

}
