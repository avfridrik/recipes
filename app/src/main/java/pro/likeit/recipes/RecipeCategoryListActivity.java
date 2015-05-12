package pro.likeit.recipes;

import pro.likeit.recipes.CustomListAdapter;
import pro.likeit.recipes.AppController;
import pro.likeit.recipes.RecipeCategory;
import pro.likeit.recipes.ExternalDbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class RecipeCategoryListActivity extends Activity {

    private static final String DB_NAME = "recipes.sqlite3";
    //Хорошей практикой является задание имен полей БД константами
    private static final String TABLE_NAME = "recipe_category";
    private static final String CATEGORY_ID = "_id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_IMAGE = "image";
    private static final String CATEGORY_PARENT = "parent_id";
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    final String LOG_TAG = "myLogs";
    // RecipeCategorys json url
    private ProgressDialog pDialog;
    private List<RecipeCategory> categoryList = new ArrayList<RecipeCategory>();
    private ListView listView;
    private CustomListAdapter adapter;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_category_list);

       listView = (ListView) findViewById(R.id.list);
       adapter = new CustomListAdapter(this, categoryList);
         listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));

        //Наш ключевой хелпер
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();

        Cursor c = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(CATEGORY_ID);
            int nameColIndex = c.getColumnIndex(CATEGORY_NAME);
            int imageColIndex = c.getColumnIndex(CATEGORY_IMAGE);

            do {
                RecipeCategory category = new RecipeCategory();
                category.setTitle(c.getString(nameColIndex));
                category.setThumbnailUrl(c.getString(imageColIndex));
                Log.d(LOG_TAG, c.getString(imageColIndex));
                // adding category to categorys array
                categoryList.add(category);
            } while (c.moveToNext());
        } else
        c.close();

        adapter.notifyDataSetChanged();
        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(categoryReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}