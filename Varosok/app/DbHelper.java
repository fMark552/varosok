import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "varosok.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "varosok";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nev";
    private static final String COLUMN_COUNTRY = "orszag";
    private static final String COLUMN_POPULATION = "lakossag";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_COUNTRY + " TEXT NOT NULL, " +
                COLUMN_POPULATION + " INTEGER NOT NULL);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<String> searchCitiesByCountry(String country) {
        List<String> cityList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_NAME};
        String selection = COLUMN_COUNTRY + "=?";
        String[] selectionArgs = {country};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String cityName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                cityList.add(cityName);
            }
            cursor.close();
        }

        db.close();
        return cityList;
    }

    public boolean insertCity(String name, String country, int population) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (isCityNameExists(db, name)) {
            db.close();
            return false;
        }

        String insertQuery = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_NAME + ", " +
                COLUMN_COUNTRY + ", " +
                COLUMN_POPULATION + ") VALUES (?, ?, ?)";
        db.execSQL(insertQuery, new String[]{name, country, String.valueOf(population)});

        db.close();
        return true;
    }

    private boolean isCityNameExists(SQLiteDatabase db, String cityName) {
        String[] projection = {COLUMN_NAME};
        String selection = COLUMN_NAME + "=?";
        String[] selectionArgs = {cityName};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
