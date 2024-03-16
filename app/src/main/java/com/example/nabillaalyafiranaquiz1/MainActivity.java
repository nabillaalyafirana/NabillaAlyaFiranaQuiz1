package com.example.nabillaalyafiranaquiz1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

        EditText etCustomer, etCode, etUnit;
        RadioGroup radioGroup;
        Button btnProcess;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            etCustomer = findViewById(R.id.etCustomer);
            etCode = findViewById(R.id.etCode);
            etUnit = findViewById(R.id.etUnit);
            radioGroup = findViewById(R.id.radioGroup);
            btnProcess = findViewById(R.id.btnProcess);

            btnProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processTransaction();
                }
            });
        }

        private void processTransaction() {
            String customerName = etCustomer.getText().toString().trim();
            String itemCode = etCode.getText().toString().trim();
            int unit = Integer.parseInt(etUnit.getText().toString().trim());
            String membershipType = getMembershipType();

            // Lakukan pengiriman data ke activity Total
            Intent intent = new Intent(MainActivity.this, Total.class);
            intent.putExtra("customerName", customerName);
            intent.putExtra("itemCode", itemCode);
            intent.putExtra("unit", unit);
            intent.putExtra("membershipType", membershipType);
            startActivity(intent);
        }

        private String getMembershipType() {
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return selectedRadioButton.getText().toString();
        }
    }


