import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {
    private EditText nameEditText, countryEditText, populationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        nameEditText = findViewById(R.id.nameEditText);
        countryEditText = findViewById(R.id.countryEditText);
        populationEditText = findViewById(R.id.populationEditText);

        Button addButton = findViewById(R.id.addButton);
        Button backButton = findViewById(R.id.backButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addCity() {
        String name = nameEditText.getText().toString().trim();
        String country = countryEditText.getText().toString().trim();
        String populationString = populationEditText.getText().toString().trim();

        if (name.isEmpty() || country.isEmpty() || populationString.isEmpty()) {
            Toast.makeText(this, "Kérlek tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
            return;
        }

        int population = Integer.parseInt(populationString);

        DbHelper dbHelper = new DbHelper(this);

        boolean success = dbHelper.insertCity(name, country, population);

        if (success) {
            Toast.makeText(this, "Város hozzáadva!", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "A város már létezik!", Toast.LENGTH_SHORT).show();
        }

        dbHelper.close();
    }

    private void clearFields() {
        nameEditText.setText("");
        countryEditText.setText("");
        populationEditText.setText("");
    }
}

