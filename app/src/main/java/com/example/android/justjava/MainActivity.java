//Code written by Pedro Henrique Carvalho


package com.example.android.justjava;


import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int coffeeNum = 0;
    Boolean wp=false,ch=false;
    double testv;
    String costumerName, costumerEmail;
    EditText getting_name,getting_email;
    CheckBox whipped_CreamBX, Chocolate_BX;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getNameCostumer(View view){
        getting_name = (EditText)findViewById(R.id.get_costumer_name);
        costumerName =  getting_name.getText().toString();
        if(costumerName.matches("")){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Costumer's name saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetButton(View view){
        coffeeNum =0;
        display(coffeeNum);
        displayPrice(coffeeNum);
        displayOrder(0,0,"Costumer","");
    }

    public void addcoffee(View view){
        coffeeNum++;
        display(coffeeNum);
        displayPrice(calcPriceRegular());
    }

    public void lesscoffee(View view){
        if(coffeeNum <= 0){
            Toast.makeText(this, "Minimum of 0 coffee", Toast.LENGTH_SHORT).show();
            coffeeNum= 0;
        }else{
        coffeeNum--;
        display(coffeeNum);
        displayPrice(calcPriceRegular());
        }
    }
    public double addToppings(double value){
        float pricenew = calcPriceRegular();
        for(int i =0;i<coffeeNum;i++){
            pricenew += value;
        }
        return pricenew;
    }

    //Method used to submit the order
    public void submitOrder(View view) {
        whipped_CreamBX = (CheckBox) findViewById(R.id.wc_box);
        Chocolate_BX = (CheckBox) findViewById(R.id.chocolate_box);
        if(whipped_CreamBX.isChecked()&&Chocolate_BX.isChecked()){
            wp = true;
            ch = true;
            testv = addToppings(0.8);
            displayOrder(coffeeNum, addToppings(0.8),costumerName,"WC + Choc");
        }
        else if(whipped_CreamBX.isChecked()){
            displayOrder(coffeeNum, addToppings(0.3),costumerName,"Whipped \nCream");
            wp = true;
        }else if(Chocolate_BX.isChecked()){
            displayOrder(coffeeNum, addToppings(0.5),costumerName,"Chocolate");
            ch = true;
        }
        else{
        displayOrder(coffeeNum, calcPriceRegular(),costumerName,"No Topping");
        }
    }

    public void finishOrder(View view) {
        getting_name = (EditText)findViewById(R.id.get_costumer_name);
        costumerName =  getting_name.getText().toString();
        double value;
        String email = " Enter Your email";
        String subject = "Order for " + costumerName;
        String body;
        String chooserTitle = "This is the Tittle";

        if (whipped_CreamBX.isChecked() && Chocolate_BX.isChecked()) {
            value= addToppings(0.8);
            String valueafter;
            valueafter = NumberFormat.getCurrencyInstance().format(value);
            String bodywpch = "Hello, " + costumerName + "\n\nYour order is " + coffeeNum + " Coffee \nWith wippedCream and Chocolate\nPaying: " + valueafter + " Dollars";
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", bodywpch)
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        } else if (whipped_CreamBX.isChecked()) {
            value= addToppings(0.3);
            String valueafter;
            valueafter = NumberFormat.getCurrencyInstance().format(value);
            String bodywp = "Hello, " + costumerName + "\n\nYour order is " + coffeeNum + " Coffee \nWith wippedCream\nPaying: " + valueafter + " Dollars";
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", bodywp)
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        } else if (Chocolate_BX.isChecked()) {
            value= addToppings(0.5);
            String valueafter;
            valueafter = NumberFormat.getCurrencyInstance().format(value);
            String bodych = "Hello, " + costumerName + "\n\nYour order is " + coffeeNum + " Coffee \nWith Chocolate\nPaying: " + valueafter + " Dollars";
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", bodych)
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        } else {
            value = calcPriceRegular();
            String valueafter;
            valueafter = NumberFormat.getCurrencyInstance().format(value);
            body = "Hello, " + costumerName + "\n\nYour order is " + coffeeNum + " Coffee \n With no Toppings \nPaying: " + valueafter + " Dollars";
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", body)
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        }
    }

    public int calcPriceRegular(){
        int price = coffeeNum*2;
        return price;
    }



//    This method displays the given quantity value on the screen.

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayOrder(int qnt, double price, String name, String wp) {
        TextView quantityTextView = (TextView) findViewById(R.id.order_text_view);
        quantityTextView.setText("" + qnt);
        TextView priceTextView = (TextView) findViewById(R.id.order_price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
        TextView nameTextView = (TextView) findViewById(R.id.costumer_name_order);
        nameTextView.setText("" + name);
        TextView wpTextView = (TextView) findViewById(R.id.order_wp_text_view);
        wpTextView.setText("" + wp);
    }

}