package racingcar.service;

import camp.nextstep.edu.missionutils.Console;
import racingcar.Constants;
import racingcar.Entity.Car;
import racingcar.Entity.Racing;
import racingcar.view.SystemInputMessage;
import racingcar.view.SystemOutputMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RacingService {

    Racing racing = new Racing();
    SystemOutputMessage systemOutputMessage = new SystemOutputMessage();

    public void initRacing() {
        SystemInputMessage.showInputNameMessage();
        racing.ready(InputCarName());
        SystemInputMessage.showTryCountMessage();
        racing.setCount(InputTryCount());
    }

    private int InputTryCount() {
        return Integer.parseInt(Console.readLine());
    }

    // 자동차 이름 입력
    public String[] InputCarName() {
        String carsString = Console.readLine();
        String[] cars = carsString.split(Constants.COMMA);
        try {
            IsValidName(cars);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return InputCarName();
        }
        return cars;
    }

    private void IsValidName(String[] cars) {
        for (String car : cars) {
            if (car.length() > Constants.MAXIMUM_CAR_NAME_LENGTH) {
                throw new IllegalArgumentException(Constants.NAME_LENGTH_ERROR_MESSAGE);
            }
        }
    }

    public void startRacing() {
        SystemInputMessage.showStartRacingMessage();
        for (int i = 0; i < racing.getCount(); i++) {
            play();
        }
    }

    private void play() {
        for (Car car : racing.getCars()) {
            car.move();
            systemOutputMessage.showCarPosition(car.getName(), car.getPosition());
        }
        System.out.println();
    }

    public void selectWinner() {
        comparePosition();
    }

    private void comparePosition() {
        List<Car> cars = racing.getCars();
        List<Car> winner = new ArrayList<>();
        Collections.sort(cars, (c1, c2) -> (c2.getPosition() - c1.getPosition()));

        for (int i = 0; i < cars.size(); i++) {
            if(cars.get(0).getPosition() != cars.get(i).getPosition()) break;
            winner.add(cars.get(i));
        }

        systemOutputMessage.showWinnerMessage(winner);
    }
}
