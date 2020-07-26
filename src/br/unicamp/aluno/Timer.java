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
            if (System.in.available() < 1) { // apenas se não houver entrada entrada
                Thread.sleep(TIMER_SEG * 1000);
                throw new TimeoutException("||| TIMEOUT |||"); // quando timer passar joga excessão
            } else {
                Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            engine.setWave(false); // sinaliza final da wave
            System.out.print(e.getMessage());
            Thread.interrupted();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
