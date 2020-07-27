package br.unicamp.aluno;

import java.util.concurrent.TimeoutException;

public class Timer extends Thread {
    private final int TIMER_SEG = 60; // wave tem duração de 1 minuto
    private Game engine;

    public Timer(Game game) {
        engine = game;
    }

    @Override
    public void run() { // timer é chamado e se wave não tiver finalizado até ela terminar termina
        try {
            Thread.sleep(TIMER_SEG * 1000);
            throw new TimeoutException("||| TIMEOUT |||"); // quando timer passar joga excessão
        } catch (InterruptedException e) {
            return;
        } catch (TimeoutException e) {
            engine.setWave(false); // sinaliza final da wave
            System.out.print(e.getMessage());
            Thread.interrupted();
        }

    }
}
