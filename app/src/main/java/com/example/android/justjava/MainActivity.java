/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    int quantity = 0;

    public void  increase(View view){
        quantity = quantity + 1;
        if(quantity>100){
            Toast.makeText(getApplicationContext(), getString(R.string.maxAlert), Toast.LENGTH_SHORT ).show();
            quantity=100;
        }
        displayQuantity(quantity);

    }

    public void decrease(View view){
        quantity =  quantity - 1;
        if(quantity<0){
            Toast.makeText(getApplicationContext(), getString(R.string.minimumAlert), Toast.LENGTH_SHORT ).show();
            quantity=0;
        }
        displayQuantity(quantity);
    }

    public int calculatePrice(int quantity, boolean cream, boolean choco){
        int price = quantity * 5;
        if(cream)
            price += 1*quantity ;
        if(choco)
            price += 2*quantity ;
        return price;
    }

    public String createOrderSummary(String Name , boolean cream , boolean choco){
        int price = calculatePrice(quantity,cream,choco);
        return   R.string.orderSummaryName + " : " + Name
                + "\n" + getString(R.string.orderSummaryQuant) +" : " + quantity
                + "\n" + getString(R.string.orderSummaryCream) +" : "+ cream
                + "\n" + getString(R.string.orderSummaryChoco) +" : "+ choco
                + "\n" + getString(R.string.orderSummaryPrice) +": $" + price
                + "\n" + getString(R.string.orderSummaryGreeting) ;
    }

    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.user_name);
        String user = name.getText().toString();

        EditText email = (EditText) findViewById(R.id.user_email);
        String[] userMail = {email.getText().toString()};

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocoChips = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocoChips = chocoChips.isChecked();

        String priceMessage = createOrderSummary(user, hasWhippedCream, hasChocoChips);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, userMail );
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }


    public void toastBoxCream(View view){
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        if(hasWhippedCream){
            Toast.makeText(getApplicationContext(), getString(R.string.addCream), Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.removeCream), Toast.LENGTH_SHORT ).show();
        }
    }
    public void toastBoxChoco(View view){
        CheckBox choco = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChoco = choco.isChecked();
        if(hasChoco){
            Toast.makeText(getApplicationContext(), getString(R.string.addChoco), Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.removeChoco), Toast.LENGTH_SHORT ).show();
        }
    }
}