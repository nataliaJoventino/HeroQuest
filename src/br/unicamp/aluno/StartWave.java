package br.unicamp.aluno;

public class StartWave extends Thread{
    private TextEngine textEngine;
    private Coordinator coordinator;
    public StartWave(TextEngine textEngine) {
        this.textEngine = textEngine;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            Thread.yield();
            textEngine.starWave();
            textEngine.readCommandFromKeyboard();
        }
        System.out.println("StartWave stopped!");
    }

}
