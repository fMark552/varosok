import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText countryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryEditText = findViewById(R.id.countryEditText);
        Button searchButton = findViewById(R.id.searchButton);
        Button addButton = findViewById(R.id.addButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country = countryEditText.getText().toString().trim();

                if (!country.isEmpty()) {
                    Intent searchIntent = new Intent(MainActivity.this, SearchResultActivity.class);
                    searchIntent.putExtra("country", country);
                    startActivity(searchIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Kérlek add meg az ország nevét!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCityIntent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(addCityIntent);
            }
        });
    }
}
