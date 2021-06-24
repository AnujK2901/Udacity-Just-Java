package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    ScrollView activityLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLayout = (ScrollView) findViewById(R.id.scroll_view);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Snackbar snackbar = Snackbar.make(activityLayout, "You Can Not Have More Than 100 Coffees At A Time !!", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 0) {
            Snackbar snackbar = Snackbar.make(activityLayout, "You Can Just Order Coffee In Range Of 1 and 100 !!", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if (quantity == 0) {
            Snackbar snackbar = Snackbar.make(activityLayout, "Oops ! It Looks Like That You Ordered Nothing ..", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        String hasWhippedCreamString;
        String hasChocolateString;
        String pronoun;

        RadioButton male = (RadioButton) findViewById(R.id.male);
        boolean isMale = male.isChecked();

        RadioButton female = (RadioButton) findViewById(R.id.female);
        boolean isFemale = female.isChecked();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        if (hasWhippedCream) {
            hasWhippedCreamString = "Has Selected Whipped Cream.";
        } else {
            hasWhippedCreamString = "Has Not Selected Whipped Cream";
        }

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        if (hasChocolate) {
            hasChocolateString = "Has Selected Chocolate Topping.";
        } else {
            hasChocolateString = "Has Not Selected Chocolate Topping.";
        }

        int price = calculatePrice(hasChocolate, hasWhippedCream);

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        if (isMale) {
            pronoun = "He";
        } else if (isFemale) {
            pronoun = "She";
        } else pronoun = name;

        Toast.makeText(this, "Thank You !", Toast.LENGTH_SHORT).show();
        String message = createOrderSummary(hasWhippedCreamString, hasChocolateString, name, price, pronoun);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:orderyourcoffee@test.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return Total Price As Per Quantity.
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }


    /**
     * This Method makes the order summary in the order_summary_text_view
     *
     * @param addChocolate    is string for chocolate.
     * @param addWhippedCream is string for whipped cream.
     * @param name            is string which is get by the edit text.
     * @return Order Summary Price Message.
     */
    private String createOrderSummary(String addWhippedCream, String addChocolate, String name, int price, String pronoun) {
        String priceMessage = "Hey Complete These Orders !";
        priceMessage += "\nName : " + name;
        priceMessage += "\n" + pronoun + " " + addWhippedCream;
        priceMessage += "\n" + pronoun + " " + addChocolate;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nTotal : ₹ " + price;
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     *
     * @param numberOfCoffees is the number of Coffees as per quantity specified.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}