package com.kafka;

public class ProdMain {
    public static void main(String[] args) {
        int numProducteurs = 3;
        int nbmessages = 1000000;
        int debut;
        int fin;
        Thread[] threads = new Thread[numProducteurs];

        for (int i = 0; i < numProducteurs; i++) {
            debut = i * (nbmessages / numProducteurs);
            fin = (i + 1) * (nbmessages / numProducteurs) - 1;

            if (i == numProducteurs - 1) {
                fin = nbmessages - 1;
            }
            Producteur prod = new Producteur(i, debut, fin);
            threads[i] = new Thread(prod);
            threads[i].start();
        }

        for (int i = 0; i < numProducteurs; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Production des messages terminée.");
    }
}
