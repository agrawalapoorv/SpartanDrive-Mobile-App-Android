package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;

/**
 * Created by namithashetty on 12/13/15.
 */
public class DeleteFileActivity extends ConnectionActivity {
    private static final String TAG = "ConnectionDriveActivity";

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        String driveId = getIntent().getExtras().getString("driveId");
        Log.i(TAG, "Selected item:" + driveId);
        DriveId drive = DriveId.decodeFromString(driveId);
        DriveFile file = drive.asDriveFile();
        file.delete(getGoogleApiClient());

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);

        //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
        //.setResultCallback(idCallback1);

    }
}
