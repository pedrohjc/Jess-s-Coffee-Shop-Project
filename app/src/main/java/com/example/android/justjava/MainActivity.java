//Code written by Pedro Henrique Carvalho


package com.example.android.justjava;


import android.icu.text.NumberFormat;
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
    String costumerName;
    EditText getting_name;
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
        coffeeNum--;
        display(coffeeNum);
        displayPrice(calcPriceRegular());
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
            displayOrder(coffeeNum, addToppings(0.8),costumerName,"WC + Choc");
        }
        else if(whipped_CreamBX.isChecked()){
            displayOrder(coffeeNum, addToppings(0.3),costumerName,"Whipped \nCream");
        }else if(Chocolate_BX.isChecked()){
            displayOrder(coffeeNum, addToppings(0.5),costumerName,"Chocolate");
        }
        else{
        displayOrder(coffeeNum, calcPriceRegular(),costumerName,"No Topping");
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