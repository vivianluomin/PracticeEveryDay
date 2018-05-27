package com.example.asus1.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.UrlQuerySanitizer;

public class DatabaseProvider extends ContentProvider {

    public  static  final  int BOOK_DIR = 0;
    public  static  final  int BOOK_ITEM = 1;
    public  static  final  int  CATEGORY_DIR = 2;
    public  static  final  int  CATEGORY_ITEM = 3;
    private static UriMatcher uriMatcher;
    private  MyDatabaseHelper dbHelper;
    public  static  final String AUTHORITY = "com.example.asus1.databasetest.provider";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"Book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"Book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"Category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"Category/#",CATEGORY_ITEM);
    }

    public DatabaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Book", "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Category", "id = ?", new String[]{categoryId});

                break;
            default:
                break;


        }
        return deleteRows;
    }





    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.asus1.databasetest.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.asus1.databasetest.provider.book";

            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.asus1.databasetest.provider.category";

            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.asus1.databasetest.provider.category";
            default:
                break;
        }
        return  null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book",null,values);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
                break;
            case CATEGORY_DIR:
            case  CATEGORY_ITEM:
                long newCategoryId = db.insert("Category",null,values);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/category"+newCategoryId);
                break;
            default:
                break;
        }
        return  uriReturn;

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDatabaseHelper(getContext(),"BookStore.db",null,2);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = db.query("Book",projection,selection,selectionArgs,null,
                        null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book",projection,"id = ？",new String[] {bookId},
                        null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category",projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categorgId = uri.getPathSegments().get(1);
                cursor = db.query("Category",projection,"id = ?",new String[]{categorgId},
                        null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                updatedRows = db.update("Book",values,selection,selectionArgs );
                break;
            case BOOK_ITEM :
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book",values,"id = ?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updatedRows = db.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM :
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category",values,"id = ?",new  String[]{categoryId});
                break;
            default: break;
        }
        return updatedRows;
    }
}
