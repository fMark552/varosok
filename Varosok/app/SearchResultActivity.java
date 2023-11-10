import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultTextView = findViewById(R.id.resultTextView);
        Button backButton = findViewById(R.id.backButton);

        String country = getIntent().getStringExtra("country");

        DbHelper dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();
        dbHelper.close();

        List<String> cities = dbHelper.searchCitiesByCountry(country);

        if (cities.isEmpty()) {
            resultTextView.setText("Nem található egyezés a következő adattal: " + country);
        } else {
            StringBuilder resultText = new StringBuilder();
            for (String city : cities) {
                resultText.append(city).append("\n");
            }
            resultTextView.setText(resultText.toString());
        }

        dbHelper.close();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
