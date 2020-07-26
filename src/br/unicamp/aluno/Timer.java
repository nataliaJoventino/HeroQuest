package br.unicamp.aluno;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Timer extends Thread {
    private final int TIMER_SEG = 60; // wave tem duração de 1 minuto
    private Game engine;
    public Timer(Game game) {
        engine = game;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(TIMER_SEG*1000);
            throw new TimeoutException("||| TIMEOUT |||"); // quando timer passar joga excessão
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {

            try {
                if (System.in.available() < 1) { // apenas se não houver entrada entrada
                    engine.setWave(false); // sinaliza final da wave
                    System.out.print(e.getMessage());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Thread.interrupted();
        }

    }
}
