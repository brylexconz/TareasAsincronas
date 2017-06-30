package bryan_alexander.tareasasincronas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miHilo();
        sinHilo();
        miHiloAsync();





    }


    private void unSegundo(){

        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }


    }

    private void miHilo(){

        btnConHilo = (Button) findViewById(R.id.btn1);
        btnConHilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i=1;i<6;i++){

                            unSegundo();

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "TERMINO SIMPLE HILO", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();

            }
        });


    }

    private void sinHilo(){

        btnSinHilo = (Button) findViewById(R.id.btn0);

        btnSinHilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=1;i<10;i++){

                    unSegundo();

                }
                Toast.makeText(MainActivity.this, "TERMINO SIN HILO", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void miHiloAsync(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnAsyncTask = (Button) findViewById(R.id.btn2);

        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EjemploAsyncTask hilo_asynctask = new EjemploAsyncTask();
                hilo_asynctask.execute();


            }
        });



    }

    private class EjemploAsyncTask extends AsyncTask<Void,Integer,Boolean>{

        //001 - hilo principal
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setMax(100);
            progressBar.setProgress(0);
        }

        //004
        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);

            if (resultado){
                Toast.makeText(MainActivity.this, "Tarea COMPLETA !", Toast.LENGTH_SHORT).show();
            }



            
        }

        //003 - hilo principal - actualizar progreso
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0].intValue());

        }

        //005 si cortas la ejecucion del segundo hilo que se ejecute esto
        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(MainActivity.this, "Se cancelo la Tarea!", Toast.LENGTH_SHORT).show();



        }

        //002 - hilo secundario
        @Override
        protected Boolean doInBackground(Void... voids) {

            for (int i=1;i<=10;i++){

                unSegundo();
                publishProgress(i*10);

                if (isCancelled()){
                    break;

                }

            }
            return true;
        }
    }




    //Declaracion
    Button btnSinHilo;
    Button btnConHilo;
    Button btnAsyncTask;
    ProgressBar progressBar;

}
