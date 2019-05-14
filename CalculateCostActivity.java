package txstate.edu.amb309.rentalcarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class CalculateCostActivity extends AppCompatActivity {

    int intID;
    String strName;
    String strBrand;
    double rentalCostPerDay;
    String strColor;
    int intPosition;
    DecimalFormat currency = new DecimalFormat("$###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_cost);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        //initialize value if nothing is passed in
        intID = sp.getInt("id", 0);
        strName = sp.getString("name", "");
        strBrand = sp.getString("brand", "");
        rentalCostPerDay = sp.getFloat("rentalCostPerDay", 0);
        strColor = sp.getString("color", "");
        intPosition = sp.getInt("position", 0);

        TextView txtID = findViewById(R.id.txtCarID);
        txtID.setText("ID " + intID);
        TextView txtName = findViewById(R.id.txtCarName);
        txtName.setText(strName);
        TextView txtBrand = findViewById(R.id.txtCarBrand);
        txtBrand.setText(strBrand);
        TextView txtColor = findViewById(R.id.txtCarColor);
        txtColor.setText(strColor);
        TextView txtCost = findViewById(R.id.txtCostPerDay);
        txtCost.setText("Rental Cost Per Day " + currency.format(rentalCostPerDay));

        Button calculateCost = findViewById(R.id.btnCalculateCost);
        final EditText noOfDays = findViewById(R.id.txtInputNumberOfDays);

        calculateCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intNoOfDays = 0;
                try {
                    intNoOfDays = Integer.parseInt(noOfDays.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(CalculateCostActivity.this, "Please enter the number of days to calculate cost", Toast.LENGTH_LONG).show();
                }

                if (intNoOfDays == 0) {
                    Toast.makeText(CalculateCostActivity.this, "Please enter the number of days to calculate cost", Toast.LENGTH_LONG).show();
                } else if (intNoOfDays > 30) {
                    Toast.makeText(CalculateCostActivity.this, "Please call our representatives at: (512)777-2222", Toast.LENGTH_LONG).show();
                } else {
                    double dblTotalCost = rentalCostPerDay * intNoOfDays;

                    Toast.makeText(CalculateCostActivity.this, "Your Total Cost Is " + currency.format(dblTotalCost), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnUpdateRentalCost = findViewById(R.id.btnUpdateRentalCost);
        btnUpdateRentalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalculateCostActivity.this, UpdateCostActivity.class));
            }
        });
    }
}
