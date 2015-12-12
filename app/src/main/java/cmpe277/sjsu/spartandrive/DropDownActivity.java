package cmpe277.sjsu.spartandrive;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by namithashetty on 11/28/15.
 */
public class DropDownActivity extends ConnectionActivity {
    @Override
    public void onConnected(Bundle connectionHint) {
        //super.onConnected(connectionHint);
       // Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
            //    .setResultCallback(idCallback);
        Spinner dropdown = (Spinner)findViewById(R.id.fab);
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

}
