package comp3350.fittrack.presentation.user;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.fittrack.R;
import comp3350.fittrack.application.Main;
import comp3350.fittrack.business.user.AccessUser;
import comp3350.fittrack.persistence.hsqldb.SQLSeedData;
import comp3350.fittrack.presentation.menu.HomeActivity;

/** **************************************************
 CLASS NAME: LoginActivity.java

 First screen of the app. Handles login, database initialization,
 and navigation to registration or home.

 ************************************************** */


public class LoginActivity extends AppCompatActivity {
    private AccessUser accessUser;
    private EditText usernameField, passwordField;
    private Button loginButton;
    private TextView txtRegister;

    private static boolean isDBInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Copy database and init DB only once
        if (!isDBInitialized) {
            copyDatabaseToDevice();
            Main.initializeDB();
            SQLSeedData.insertDefaults();
            isDBInitialized = true;
        }

        accessUser = new AccessUser();
        setupViews();
        setupListeners();
    }

    private void loginUser() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.login_fields_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        int result = accessUser.logIn(username, password);
        if (result < 0) {
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(getString(R.string.user_id), result);
            startActivity(intent);
            finish();
        }
    }

    private void startRegistration() {
        Intent intent = new Intent(this, UserRegistrationActivity.class);
        startActivity(intent);
    }

    private void setupViews() {
        usernameField = findViewById(R.id.inputUsername);
        passwordField = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(view -> loginUser());
        txtRegister.setOnClickListener(view -> startRegistration());
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            String dbFullPath = dataDirectory.toString() + "/" + Main.getDBPathName();
            Main.setDBPathName(dbFullPath);

        } catch (final IOException ioe) {
            System.out.println(getString(R.string.unable_to_access_data) + ioe.getMessage());
        }
    }

    private void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
