package team.uninortetasks.uninortetasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import team.uninortetasks.uninortetasks.Database.TasksDB;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //Inicializa la conexion con la base de datos de Realm
        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//        Realm.setDefaultConfiguration(config);
        TasksDB.init();
    }

}
