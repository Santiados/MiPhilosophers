package com.example.miphilosophers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/*

 * @Autor: Santiago Rengifo De La Cruz
 * @anio: 2019
 * @clase: Dispositivos/DAM
 *
 * */

/*
*
*
* */


class Filosofo extends Thread {
    int identifier;
    String nombre;
    Palillo derecho;
    Palillo izquierdo;
    int vc = 0;
    int tiempoParaComer;
    TextView myview;
    Activity activity;
    boolean activo = true;

    public Filosofo(int identifier,String nombre, Palillo izquierdo, Palillo derecho, Activity activity) {
        this.identifier = identifier;
        this.nombre = nombre;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.tiempoParaComer = (int) (Math.random() * 3);
        this.activity = activity;
    }

    public TextView getTexView(Activity context) {
        TextView v = new TextView(context);
        int id = View.generateViewId();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(100,50,100,50);
        v.setLayoutParams(params);
        v.setId(id);
        v.setText(getNombre());
        return v;
    }


    public Palillo getDerecho() {
        return derecho;
    }

    public void setDerecho(Palillo derecho) {
        this.derecho = derecho;
    }

    public Palillo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Palillo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVc() {
        return vc;
    }

    public void setVc(int vc) {
        this.vc = vc;
    }

    public int getIdentifier(){
        return  identifier;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    private void setState(int color, String state, final boolean flag){
        final int fcolor = color;
        final String fstate = state;
        final boolean fflag = flag;
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int identificador = getIdentifier();
                TextView v = (TextView) activity.findViewById(identificador);
                v.setTextColor(fcolor);
                v.setText(fstate);
            }
        });
    }


    @Override
    public void run() {

        while (activo) {
            try {
                if (this.izquierdo.getOcupado().availablePermits() == 1 && this.derecho.getOcupado().availablePermits() == 1) {
                    this.vc++;
                    this.izquierdo.coger();
                    this.derecho.coger();
                    System.out.println(getNombre() + " comiendo...");
                    setState(Color.BLUE,"Comiendo",true);
                    sleep((this.tiempoParaComer * 100) + 1000);
                    this.izquierdo.soltar();
                    this.derecho.soltar();
                    sleep(100);
                } else {
                    setState(Color.WHITE,"Pensando",true);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        setState(Color.WHITE,"Pensando",true);
    }

}