package cmpe277.sjsu.spartandrive;

/**
 * Created by namithashetty on 12/1/15.
 */

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

public class UpdateFolderActivity extends ConnectionActivity {

    @Override
        public void onConnected(Bundle connectionHint) {
           // super.onConnected(connectionHint);
            //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3QXQtaTFQOVZjUDQ")
                    //.setResultCallback(idCallback);
            String folderName = getIntent().getExtras().getString("folderName");
            String driveId = getIntent().getExtras().getString("driveId");

        //DriveId driveId = Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId();
            DriveId drive = DriveId.decodeFromString(driveId);
            DriveFolder folder = drive.asDriveFolder();
            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(folderName).build();
            folder.updateMetadata(getGoogleApiClient(), changeSet);
            //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient())
             Intent intent = new Intent(getBaseContext(), MainActivity.class);
             startActivity(intent);
            //.setResultCallback(metadataResult);
    }

        /*final ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
            @Override
            public void onResult(DriveApi.DriveIdResult result) {
                String folderName = getIntent().getExtras().getString("folderName");
                if (!result.getStatus().isSuccess()) {
                    showMessage("Cannot find DriveId. Are you authorized to view this file?");
                    return;
                }
                DriveId driveId = result.getDriveId();
                DriveFolder folder = driveId.asDriveFolder();
                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle(folderName).build();
                folder.updateMetadata(getGoogleApiClient(), changeSet)
                        .setResultCallback(metadataCallback);
            }
        };*/

        /*final ResultCallback<DriveResource.MetadataResult> metadataCallback = new ResultCallback<DriveResource.MetadataResult>() {
            @Override
            public void onResult(DriveResource.MetadataResult result) {
                if (!result.getStatus().isSuccess()) {
                    showMessage("Problem while trying to update metadata");
                    return;
                }
                showMessage("Folder title successfully updated");
            }
        };*/
        /*final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
                ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult result) {
                        if (!result.getStatus().isSuccess()) {
                            showMessage("Problem while retrieving files");
                            return;
                        }
                        mResultsAdapter.clear();
                        mResultsAdapter.append(result.getMetadataBuffer());
                        //showMessage("Successfully updated the folder.");
                    }
                };*/
}
