package fi.jamk.k1786student.testandroidapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createHelloWorld();

        createSecondActivityButton();

        createLoginButton();

        createNotification();

    }

    private void createLoginButton()
    {

        Button btn3 = (Button)findViewById(R.id.show_dialog);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                AlertDialog.Builder yBuilder = new AlertDialog.Builder(MainActivity.this);
                View yView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                final EditText yEmail = (EditText)yView.findViewById(R.id.fieldEmail);
                final EditText yPword = (EditText)yView.findViewById(R.id.fieldPassword);
                Button yLogin = (Button)yView.findViewById(R.id.btnLogin);

                yLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!yEmail.getText().toString().isEmpty() && !yPword.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            createNotification(Notification.VISIBILITY_PRIVATE, getString(R.string.loggedin_text) + " " + yEmail.getText().toString(), getString(R.string.loggedin_title), getString(R.string.loggedin_ticker), null);
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                yBuilder.setView(yView);
                AlertDialog dialog = yBuilder.create();
                dialog.show();
            }
        });

    }

    private void createSecondActivityButton()
    {

        Button btn2 = (Button)findViewById(R.id.launch_second);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Clicked 'launch second'.", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Intent intent = SecondActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });

    }

    private void createHelloWorld()
    {

        Button btn = (Button)findViewById(R.id.btnTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                //Log.i("KApp","This is a test log message!");
                //Toast.makeText(getApplicationContext(), "Magic!", Toast.LENGTH_SHORT).show();
                TextView textView = (TextView) findViewById(R.id.hello_world);
                textView.setText(getString(R.string.helloworld) + " " + count);
            }
        });

    }

    Notification.Builder notification;
    private static final int uniqueID = 612903;

    private void createNotification()
    {
        notification = new Notification.Builder(this);
        notification.setAutoCancel(true);

        Button notify = (Button)findViewById(R.id.test_notification);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification(
                        Notification.VISIBILITY_PUBLIC,
                        getString(R.string.nTest_text),
                        getString(R.string.nTest_title),
                        getString(R.string.nTest_ticker),
                        SecondActivity.class
                );
            }
        });
    }

    private int notification_id = 1;

    public void createNotification(int visibility, String text, String title, String ticker, Class i) {

        Intent intent = (i != null)?new Intent(this, i):null;
        PendingIntent pendingIntent = (i != null)?PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT):null;

        // create a new notification
        Notification notification  = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVisibility(visibility).build();
        // connect notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // make a new notification with a new unique id
        notification_id++;
        notificationManager.notify(notification_id, notification);
    }
}
