package com.zurefaseverler.kithub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookPage extends AppCompatActivity implements View.OnClickListener {

    private TextView summary, author;
    private boolean boolSummary, boolAuthor;

    private Book book;
    private Button addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);


        String bookName = "Hayvan Çiftliği";
      
        getBookInfo(bookName);
        /*-------*/
        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        author = findViewById(R.id.bookPage_aboutAuthor);
        summary = findViewById(R.id.bookPage_summary);

        Button buttonSummary = findViewById(R.id.urun_sayfasi_ozet_button);
        Button buttonAuthor = findViewById(R.id.urun_sayfasi_yazar_button);
        buttonAuthor.setOnClickListener(this);
        buttonSummary.setOnClickListener(this);

        addCart = findViewById(R.id.bookPage_addCartButton);
        addCart.setOnClickListener(this);

    }

    private void getBookInfo(final String bookName) {
        String url = "http://18.204.251.116/books.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            book = new Book(jsonObject.getInt("id"),
                                    jsonObject.getString("first_name") + jsonObject.getString("last_name"),
                                    jsonObject.getInt("stock_quantity"), jsonObject.getString("category_name"),
                                    jsonObject.getString("book_type_name"), jsonObject.getInt("price"),
                                    jsonObject.getInt("sales"), jsonObject.getInt("no_people_rated"),
                                    Float.parseFloat(jsonObject.getString("rating")), jsonObject.getString("ISBN"),
                                    bookName, jsonObject.getString("summary"),
                                    jsonObject.getString("image"));

                            setBookInfo(book);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("title", bookName);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setBookInfo(Book book) {
        TextView bookName = findViewById(R.id.bookPage_bookName);
        ImageView bookImage = findViewById(R.id.bookPage_bookImage);
        TextView author_type = findViewById(R.id.bookPage_author_type);
        RatingBar ratingBar = findViewById(R.id.bookPage_ratingBar);
        TextView price = findViewById(R.id.bookPage_price);

        addCart = findViewById(R.id.bookPage_addCartButton);

        bookName.setText(book.getTitle());
        author_type.setText(String.format("%s / %s", book.getAuthor(), book.getBookType()));
        price.setText(String.format("%s TL",book.getPrice()));
        ratingBar.setRating(book.getRating());
        summary.setText(book.getSummary());

        String[] temp = book.getImage().split("html/");
        Picasso.get().load("http://18.204.251.116/"+temp[1]).into(bookImage);

        if(book.getStockQuantity() == 0) addCart.setText(R.string.book_page_remindMe);
        else addCart.setText(R.string.book_page_addCart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.bookPage_addCartButton:
                if(book.getStockQuantity() == 0) Toast.makeText(getApplicationContext(),"stokta yok",Toast.LENGTH_SHORT).show();
                else addItem_intoCart(book.getBook_id());
                break;

            case R.id.urun_sayfasi_ozet_button:
                boolSummary = !boolSummary;
                summary.setVisibility(boolSummary ? View.VISIBLE: View.GONE);
                break;

            case R.id.urun_sayfasi_yazar_button:
                boolAuthor = !boolAuthor;
                author.setVisibility(boolAuthor ? View.VISIBLE: View.GONE);
                break;
        }

    }

    private void addItem_intoCart(final int book_id) {
        String url = "http://18.204.251.116/add_to_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(Integer.parseInt(success) > 0)   Toast.makeText(getApplicationContext(),"eklendi",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("book_id",Integer.toString(book_id));

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id",-1));
                params.put("customer_id",customer_id);

                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
