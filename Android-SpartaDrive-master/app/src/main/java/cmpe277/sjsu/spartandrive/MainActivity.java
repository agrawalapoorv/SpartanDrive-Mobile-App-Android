package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;

public class MainActivity extends ConnectionActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewAdapter mViewAdapter;
    private static final int REQUEST_CODE_OPENER = 1;
    private static final String TAG = "ConnectionDriveActivity";

    private GridView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private GridView mListViewSamples;
    private String driveId;
    NavigationView navigationView=null;
    Toolbar toolbar=null;
    boolean folderType;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mResultsListView = (GridView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CustomFolderNameActivity.class);
                startActivity(intent);
            }
        });

        /*FloatingActionButton fileFab = (FloatingActionButton) findViewById(R.id.fab1);
        fileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CustomFileNameActivity.class);
                intent.putExtra("driveId",Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId());
                startActivity(intent);
            }
        });*/

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText searchText=(EditText)findViewById(R.id.searchText);
                    //searchTerm = searchText.getText().toString();
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
        mListViewSamples.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Selected item:" + mResultsAdapter.getItem(position).getDriveId());
                driveId = mResultsAdapter.getItem(position).getDriveId().encodeToString();
                folderType = mResultsAdapter.getItem(position).isFolder();
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, mListViewSamples);
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
                        Intent intentEdit;
                        if (item.getTitle().equals("Edit")) {
                            if (folderType) {
                                intentEdit = new Intent(getBaseContext(), EditFolderActivity.class);
                            }
                            else{
                                intentEdit = new Intent(getBaseContext(), EditFileActivity.class);
                            }
                            intentEdit.putExtra("driveId", driveId);
                            startActivity(intentEdit);
                        }
                        Intent intentDelete;
                        if (item.getTitle().equals("Delete")) {
                            Log.i(TAG, "Request code" + folderType);

                            if (folderType) {
                                intentDelete = new Intent(getBaseContext(), DeleteFolderActivity.class);
                            }
                            else {
                                intentDelete = new Intent(getBaseContext(), DeleteFileActivity.class);
                            }
                            intentDelete.putExtra("driveId", driveId);
                            startActivity(intentDelete);
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
       // super.onConnected(connectionHint);
        Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);
        //Log.i(TAG,"Drive" +Drive.DriveApi.getFolder(getGoogleApiClient(),getDriveId());
       // Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
         //       .setResultCallback(idCallback);
    }

    final private ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            folder.listChildren(getGoogleApiClient())
                    .setResultCallback(metadataResult);
        }
    };

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
                    //showMessage("Successfully listed files.");
                }
            };

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


}






