package txstate.edu.amb309.rentalcarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class UpdateCostActivity extends AppCompatActivity {

    int updateCarID;
    String updateCarName;
    int updateCarPosition;
    DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cost);

        final TextView txtUpdateID = findViewById(R.id.txtUpdateCarID);
        final TextView txtUpdateName = findViewById(R.id.txtUpdateCarName);
        final EditText txtInputRentalCost = findViewById(R.id.txtInputNewCost);
        Button btnUpdateCost = findViewById(R.id.btnUpdateCost);
        Button btnGoToHomePage = findViewById(R.id.btnGoToHomePage);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        //initialize value if nothing is passed in
        updateCarID = sp.getInt("id", 0);
        updateCarName = sp.getString("name", "");
        updateCarPosition = sp.getInt("position", 0);


        txtUpdateID.setText("ID " + updateCarID);
        txtUpdateName.setText(updateCarName);

        btnUpdateCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "cars/" + updateCarPosition + "/rentalCostPerDay.json";
                StringEntity entity = null;

                try {
                    String strRentalCost = txtInputRentalCost.getText().toString();
                    float dblNewCost;

                    if(strRentalCost.equals("")) {
                        Toast.makeText(UpdateCostActivity.this, "Please enter a rental cost.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        dblNewCost = Float.parseFloat(strRentalCost);
                        entity = new StringEntity("" + decimalFormat.format(dblNewCost));
                    }

                }  catch(Exception ex){
                    ex.printStackTrace();
                }
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
                RestAPIClient.put(UpdateCostActivity.this, url, entity,
                        "application/text", new TextHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Toast.makeText(UpdateCostActivity.this, "Update Successful", Toast.LENGTH_LONG).show();

                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(UpdateCostActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        btnGoToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateCostActivity.this, MainActivity.class));
            }
        });
    }
}
