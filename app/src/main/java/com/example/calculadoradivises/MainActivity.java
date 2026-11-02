package com.example.calculadoradivises;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int[] NumMostrat = new int[10];  // els dígits escrits, en ordre
    int numsTotals = 0;              // quants dígits hi ha ara
    TextView caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        caixa = findViewById(R.id.tvEuros);
        resultat = findViewById(R.id.tvResult);
        Button boto0 = findViewById(R.id.btn0);
        Button boto1 = findViewById(R.id.btn1);
        Button boto2 = findViewById(R.id.btn2);
        Button boto3 = findViewById(R.id.btn3);
        Button boto4 = findViewById(R.id.btn4);
        Button boto5 = findViewById(R.id.btn5);
        Button boto6 = findViewById(R.id.btn6);
        Button boto7 = findViewById(R.id.btn7);
        Button boto8 = findViewById(R.id.btn8);
        Button boto9 = findViewById(R.id.btn9);
        View botoBorrar = findViewById(R.id.btnBack);
        Button botoCE = findViewById(R.id.btnCE);
        Button botoComma = findViewById(R.id.btnComma);
        Button botoGBP = findViewById(R.id.btnPound);
        Button botoUSD = findViewById(R.id.btnDollar);
        Button botoJPY = findViewById(R.id.btnYen);
        Button botoCNY = findViewById(R.id.btnYuan);
        Button botoIgual = findViewById(R.id.btnEquals);

        boto0.setOnClickListener(v -> afegirNumero(0));
        boto1.setOnClickListener(v -> afegirNumero(1));
        boto2.setOnClickListener(v -> afegirNumero(2));
        boto3.setOnClickListener(v -> afegirNumero(3));
        boto4.setOnClickListener(v -> afegirNumero(4));
        boto5.setOnClickListener(v -> afegirNumero(5));
        boto6.setOnClickListener(v -> afegirNumero(6));
        boto7.setOnClickListener(v -> afegirNumero(7));
        boto8.setOnClickListener(v -> afegirNumero(8));
        boto9.setOnClickListener(v -> afegirNumero(9));
        botoGBP.setOnClickListener(V-> canviarMoneda("pound"));
        botoUSD.setOnClickListener(v -> canviarMoneda("dollar"));
        botoIgual.setOnClickListener(V-> ferCalcul());

        botoJPY.setOnClickListener(v -> canviarMoneda("yen"));

        botoCNY.setOnClickListener(v -> canviarMoneda("yuan"));



        botoBorrar.setOnClickListener(v -> borrarUltim());
        botoCE.setOnClickListener(V -> borrarTot());
        botoComma.setOnClickListener(V-> afegirComma());


    }
    void ferCalcul(){
        if(canviTriat){
            calcular();
        }
    }
    double multiplicador;
    boolean canviTriat = false;
    void canviarMoneda(String moneda){
    canviTriat=true;
        switch (moneda) {
            case "pound":  multiplicador = 0.85; break;
            case "dollar": multiplicador = 1.15; break;
            case "yen":    multiplicador = 175;  break;
            case "yuan":   multiplicador = 8.2;  break;    }
    }

    void afegirNumero(int numero) {
        if (!(posComma >= 0 && numsTotals - posComma >= 2) && numsTotals < 10) {
            String actual = caixa.getText().toString();

            if (actual.equals("0")) {
                numsTotals = 0;
            }

            NumMostrat[numsTotals] = numero;
            numsTotals++;
            actualitzarPantalla();
        }
    }
    int posComma = -1;               // quants dígits hi ha abans de la coma (-1 = sense coma)
    TextView resultat;
    void afegirComma() {
        if (posComma >= 0 && numsTotals - posComma >= 2) return;
        if (posComma >= 0 || numsTotals >= 10) return;
        if (caixa.getText().toString().equals("0")) {
            NumMostrat[0] = 0;        // si no hi ha res, la coma va darrere d'un 0
            numsTotals = 1;
        }
        posComma = numsTotals;
        actualitzarPantalla();
    }
void borrarTot(){

    numsTotals = 0;
    actualitzarPantalla();
    posComma = -1;
}
    void calcular() {
        double euros = Double.parseDouble(caixa.getText().toString().replace(",", "."));
        resultat.setText(String.format("%.2f", euros * multiplicador));
    }
    void borrarUltim() {
        if (posComma == numsTotals && posComma >= 0) {
            posComma = -1;            // primer esborra la coma
            actualitzarPantalla();
        } else if (numsTotals > 0) {
            numsTotals--;
            actualitzarPantalla();
        }
    }

    void actualitzarPantalla() {
        if (numsTotals == 0) {
            caixa.setText("0");
            return;
        }
        String text = "";
        for (int i = 0; i < numsTotals; i++) {
            if (i == posComma) text = text + ",";
            text = text + NumMostrat[i];  // enganxa els dígits en ordre
        }
        if (posComma == numsTotals) text = text + ",";
        caixa.setText(text);
    }
}