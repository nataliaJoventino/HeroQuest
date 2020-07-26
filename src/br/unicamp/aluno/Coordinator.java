package br.unicamp.aluno;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Coordinator extends Thread {
    private Game engine;
    public Coordinator(Game game) {
        engine = game;
    }



    @Override
    public void run() {
        try {
            if (System.in.available() < 1){ // se não tem entrada
                Thread.sleep(5*1000);
                throw new TimeoutException("||| TIMEOUT |||");
            } else{
                Thread.yield(); // se teve entrada chama Thread de novo
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            engine.setWave(false);
            System.out.print(e.getMessage());

//USUARIO TEM QUE DAR ENTER
//            try { // NA HORA DE RODAR TEM QUE ESTAR COM O CURSOR DENTRO DO CONSOLE SE NÃO DÁ RUIM
//                    Robot robot = new Robot();
//                    robot.keyPress(KeyEvent.VK_SPACE);
//                    robot.keyPress(KeyEvent.VK_ENTER);
//                    robot.keyRelease(KeyEvent.VK_ENTER);
//            } catch (AWTException ex) {
//                ex.printStackTrace();
//            }


        }

    }
}
