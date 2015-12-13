package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

/**
 * Created by namithashetty on 12/2/15.
 */
public class SearchFileBasedOnTitleActivity extends ConnectionActivity {
    private static final String TAG = "SearchBasedOnTitle";

    private GridView mViewListView;
    private ViewAdapter mViewAdapter;
    private GridView mListViewSamples;
    private String driveId;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        mViewListView = (GridView) findViewById(R.id.listItems);
        mViewAdapter = new ViewAdapter(this);
        mViewListView.setAdapter(mViewAdapter);

        mListViewSamples = (GridView) findViewById(R.id.listItems);
        mListViewSamples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Selected item:" + mViewAdapter.getItem(position).getDriveId());
                driveId = mViewAdapter.getItem(position).getDriveId().encodeToString();

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(SearchFileBasedOnTitleActivity.this, mListViewSamples);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.i(TAG, "res" + item.getTitle());
                        if (item.getTitle().equals("View Content")) {
                            Intent intent = new Intent(getBaseContext(), listContentsActivity.class);
                            intent.putExtra("driveId", driveId);
                            startActivity(intent);
                        }
                        if (item.getTitle().equals("Edit")) {
                            Intent intent = new Intent(getBaseContext(), EditFolderActivity.class);
                            intent.putExtra("driveId", driveId);
                            startActivity(intent);
                        }
                        if (item.getTitle().equals("Delete")) {
                            Intent intent = new Intent(getBaseContext(), DeleteFolderActivity.class);
                            intent.putExtra("driveId", driveId);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    /**
     * Clears the result buffer to avoid memory leaks as soon as the activity is no longer
     * visible by the user.
     */
    @Override
    protected void onStop() {
        super.onStop();
        mViewAdapter.clear();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        //super.onConnected(connectionHint);
        String searchTerm = getIntent().getExtras().getString("SearchTerm");
        Log.i(TAG, "SearchTerm" +searchTerm);
        Query query = new Query.Builder()
                .addFilter(Filters.contains(SearchableField.TITLE, searchTerm))
                .build();
        Drive.DriveApi.query(getGoogleApiClient(), query)
                .setResultCallback(metadataCallback);
    }

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataCallback =
            new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving results");
                        return;
                    }
                    mViewAdapter.clear();
                    mViewAdapter.append(result.getMetadataBuffer());
                }
            };


}
