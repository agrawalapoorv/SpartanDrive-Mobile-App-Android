package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;

/**
 * Created by namithashetty on 11/26/15.
 */
public class listContentsActivity extends ConnectionActivity {

    private GridView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private GridView mListViewSamples;
    private static final String TAG = "ConnectionDriveActivity";
    private String driveId;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mResultsListView = (GridView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();


        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText searchText=(EditText)findViewById(R.id.searchText);
                //searchTerm = searchText.getText().toString();
                Log.d("ListContainActivity","hellllllloooooooooo");
                Intent intent = new Intent(getBaseContext(), SearchPopUpActivity.class);
                //intent.putExtra("searchTerm", searchTerm);
                startActivity(intent);
            }
        });



        //Code to handle onClick for CreateFolder button
       /* final Button button = (Button) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(),  CreateFolderInFolderActivity.class);
                startActivity(intent);
            }
        });
*/
        mListViewSamples = (GridView) findViewById(R.id.listItems);
        mListViewSamples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Selected item:" + mResultsAdapter.getItem(position).getDriveId());
                driveId = mResultsAdapter.getItem(position).getDriveId().encodeToString();
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(listContentsActivity.this, mListViewSamples);
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

    @Override
    public void onConnected(Bundle connectionHint) {
        //super.onConnected(connectionHint);
        //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);
        String driveId = getIntent().getExtras().getString("driveId");
        Log.i(TAG, "Selected item:" + driveId);
        DriveId drive = DriveId.decodeFromString(driveId);
        DriveFolder folder = drive.asDriveFolder();
        folder.listChildren(getGoogleApiClient())
                .setResultCallback(metadataResult);
    }

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving files");
                        return;
                    }
                    mResultsAdapter.clear();
                    mResultsAdapter.append(result.getMetadataBuffer());
                    showMessage("Successfully listed files.");
                }
            };
}
