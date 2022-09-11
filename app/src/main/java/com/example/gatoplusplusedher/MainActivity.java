package com.example.gatoplusplusedher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView PuntajeJugadorUno;
    private TextView PuntajeJugadorDos;
    private TextView estado;
    private Button[] botones = new Button[36];
    private Button reiniciarJuego;
    private int ContPuntajeJugadorUno;
    private int ContPuntajeJugadorDos;
    private int contRonda;
    boolean jugadorActivo;
    int [] estadoDeJuego = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
    int [][] posicionesGanadoras = {{00,01,02,03},{02,03,04,05},{01,02,03,04},{06,07,8,9},{8,9,10,11},{07,8,9,10},{12,13,14,15},
            {14,15,16,17},{13,14,15,16},{18,19,20,21},{20,21,22,23},{19,20,21,22},{24,25,26,27},{26,27,28,29},{25,26,27,28},
            {30,31,32,33},{32,33,34,35},{31,32,33,34},{00,06,12,18},{12,18,24,30},{06,12,18,24},{01,07,13,19},{13,19,25,31},{07,13,19,25},
            {02,8,14,20},{14,20,26,32},{8,14,20,26},{03,9,15,21,27},{15,21,27,33},{9,15,21,27},{04,10,16,22},{16,22,28,34},{10,16,22,28},
            {05,11,17,23},{17,23,29,35},{11,17,23,29,35},{12,19,26,33},{06,13,20,27},{13,20,27,34},{00,07,14,21},{14,21,28,35},{07,14,21,28},
            {01,8,15,22},{8,15,22,29},{02,9,16,23},{17,22,27,32},{11,16,21,26},{16,21,26,31},{05,10,15,20},{15,20,25,30},{10,15,20,25},
            {04,9,14,19},{9,14,19,24},{03,8,13,18}};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            PuntajeJugadorUno = (TextView) findViewById(R.id.puntajeJugadorUno);
            PuntajeJugadorDos = (TextView) findViewById(R.id.puntajeJugadorDos);
            estado = (TextView) findViewById(R.id.estado);

            reiniciarJuego = (Button) findViewById(R.id.reiniciarJuego);

            for(int i = 0; i < botones.length; i++){
                if(i < 10) {
                    String botonId = "btn_0" + i;
                    int resourceId = getResources().getIdentifier(botonId, "id", getPackageName());
                    botones[i] = (Button) findViewById(resourceId);
                    botones[i].setOnClickListener(this);
                }else if(i >= 10){
                    String botonId = "btn_" + i;
                    int resourceId = getResources().getIdentifier(botonId, "id", getPackageName());
                    botones[i] = (Button) findViewById(resourceId);
                    botones[i].setOnClickListener(this);
                }
            }

            contRonda = 0;
            ContPuntajeJugadorUno = 0;
            ContPuntajeJugadorDos = 0;
            jugadorActivo = true;

        }

        @Override
        public void onClick(View v) {
            if(!((Button)v).getText().toString().equals("")){
                return;
            }
            String botonId = v.getResources().getResourceEntryName(v.getId());
            int gameStatePointer = Integer.parseInt(botonId.substring(botonId.length()-2, botonId.length()));

            if(jugadorActivo){
                ((Button) v).setText("X");
                ((Button) v).setTextColor(Color.parseColor("#FF5733"));
                estadoDeJuego[gameStatePointer] = 0;
            } else{
                ((Button) v).setText("O");
                ((Button) v).setTextColor(Color.parseColor("#900C3F"));
                estadoDeJuego[gameStatePointer] = 1;
            }
            contRonda++;
            if(checarGanador()){
                if(jugadorActivo){
                    ContPuntajeJugadorUno++;
                    actualizarPuntaje();
                    Toast.makeText(this, "¡Ganó Jugador 1!", Toast.LENGTH_SHORT).show();
                    jugarDeNuevo();
                }else {
                    ContPuntajeJugadorDos++;
                    actualizarPuntaje();
                    Toast.makeText(this, "¡Ganó Jugador 2!", Toast.LENGTH_SHORT).show();
                    jugarDeNuevo();
                }
            }else if(contRonda == 36){
                jugarDeNuevo();
                Toast.makeText(this, "Empate /:", Toast.LENGTH_SHORT).show();
            }else{
                jugadorActivo = !jugadorActivo;
            }

            if(ContPuntajeJugadorUno > ContPuntajeJugadorDos){
                estado.setText("El Jugador 1 va ganando :D");
            }else if(ContPuntajeJugadorDos > ContPuntajeJugadorUno){
                estado.setText("El Jugador 2 va ganando :D");
            }else{
                estado.setText("");
            }

            reiniciarJuego.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    jugarDeNuevo();
                    ContPuntajeJugadorUno = 0;
                    ContPuntajeJugadorDos = 0;
                    estado.setText("");
                    actualizarPuntaje();
                }
            });
        }

        public boolean checarGanador(){
            boolean resultado = false;

            for(int [] posicionesGanadoras : posicionesGanadoras){
                if(estadoDeJuego[posicionesGanadoras[0]] == estadoDeJuego[posicionesGanadoras[1]] && estadoDeJuego[posicionesGanadoras[1]] == estadoDeJuego[posicionesGanadoras[2]] && estadoDeJuego[posicionesGanadoras[2]] == estadoDeJuego[posicionesGanadoras[3]] && estadoDeJuego[posicionesGanadoras[0]] != 2){
                    resultado = true;
                }
            }
            return resultado;
        }

        public void actualizarPuntaje(){
            PuntajeJugadorUno.setText(Integer.toString(ContPuntajeJugadorUno));
            PuntajeJugadorDos.setText(Integer.toString(ContPuntajeJugadorDos));
        }

        public void jugarDeNuevo(){
            contRonda = 0;
            jugadorActivo = true;

            for(int i = 0; i < botones.length; i++){
                estadoDeJuego[i] = 2;
                botones[i].setText("");
            }
        }
    }