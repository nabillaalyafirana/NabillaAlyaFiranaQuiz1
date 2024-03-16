package com.example.nabillaalyafiranaquiz1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class Total extends AppCompatActivity {
    TextView tvCustomer, tvMembershipType, tvItemCode, tvItemName, tvItemPrice, tvTotalPrice,
            tvDiscount, tvMemberDiscount, tvTotalPayment, tvThankYou;

    // Data barang
    String[] itemCodes = {"AAE", "LV3", "MP3"};
    String[] itemNames = {"Acer Aspire E14", "Lenovo V14 Gen 3", "Macbook Pro M3"};
    double[] itemPrices = {8676981, 6666666, 28999999};

    // Variabel diskon
    double discountBasic = 0.02; // 2%
    double discountPremium = 0.05; // 5%
    double discountVIP = 0.10; // 10%
    double discountThreshold = 10000000; // Batas diskon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        tvCustomer = findViewById(R.id.tvCustomer);
        tvMembershipType = findViewById(R.id.tvMembershipType);
        tvItemCode = findViewById(R.id.tvItemCode);
        tvItemName = findViewById(R.id.tvItemName);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvMemberDiscount = findViewById(R.id.tvMemberDiscount);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvThankYou = findViewById(R.id.tvThankYou);

        // Mendapatkan data dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String customerName = extras.getString("customerName");
            String membershipType = extras.getString("membershipType");
            String itemCode = extras.getString("itemCode");
            int unit = extras.getInt("unit");

            // Menampilkan data customer dan membership type
            tvCustomer.setText(customerName);
            tvMembershipType.setText("Membership Type: " + membershipType);

            // Menghitung total harga dan diskon
            int selectedItemIndex = findItemIndex(itemCode);
            double itemPrice = itemPrices[selectedItemIndex] * unit;
            double discountAmount = calculateDiscount(itemPrice, membershipType);
            double totalPrice = itemPrice - discountAmount;
            double totalPayment = totalPrice;

            // Menampilkan data barang dan transaksi
            tvItemCode.setText("Kode barang: " + itemCode);
            tvItemName.setText("Nama Barang: " + itemNames[selectedItemIndex]);
            tvItemPrice.setText("Harga: Rp " + formatPrice(itemPrice));
            tvTotalPrice.setText("Total Harga: Rp " + formatPrice(totalPrice));
            tvDiscount.setText("Discount harga: Rp " + formatPrice(discountAmount));
            tvMemberDiscount.setText("Discount member: Rp " + formatPrice(discountAmount));
            tvTotalPayment.setText("Jumlah Bayar: Rp " + formatPrice(totalPayment));
            tvThankYou.setText("Thank you for shopping here:))");

            // Set onClickListener untuk tombol Share
            Button btnShare = findViewById(R.id.btnShare);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareTransaction();
                }
            });
        }
    }

    // Fungsi untuk mencari indeks item berdasarkan kode
    private int findItemIndex(String itemCode) {
        for (int i = 0; i < itemCodes.length; i++) {
            if (itemCodes[i].equals(itemCode)) {
                return i;
            }
        }
        return -1;
    }

    // Fungsi untuk menghitung diskon
    private double calculateDiscount(double price, String membershipType) {
        double discountAmount = 0;

        // Menerapkan diskon berdasarkan tipe member
        if (membershipType.equals("Basic")) {
            discountAmount = price * discountBasic;
        } else if (membershipType.equals("Premium")) {
            discountAmount = price * discountPremium;
        } else if (membershipType.equals("VIP")) {
            discountAmount = price * discountVIP;
        }

        // Menambahkan diskon tambahan jika total harga melebihi threshold
        if (price > discountThreshold) {
            discountAmount += 100000;
        }

        return discountAmount;
    }

    // Fungsi untuk memformat harga dengan format Rp XXX.XXX.XXX
    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    // Fungsi untuk berbagi transaksi ke media sosial
    private void shareTransaction() {
        String message = tvCustomer.getText().toString() + "\n" +
                tvMembershipType.getText().toString() + "\n" +
                tvItemCode.getText().toString() + "\n" +
                tvItemName.getText().toString() + "\n" +
                tvItemPrice.getText().toString() + "\n" +
                tvTotalPrice.getText().toString() + "\n" +
                tvDiscount.getText().toString() + "\n" +
                tvMemberDiscount.getText().toString() + "\n" +
                tvTotalPayment.getText().toString() + "\n" +
                tvThankYou.getText().toString();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}