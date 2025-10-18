package university.view.patterns.display.state;

//ИТЕРАТОР МЕНЯЕТ СОСТОЯНИЕ НА ВПЕРЕД И ВПЕРЕД

import university.view.patterns.display.strategy.IDisplayStrategy;
import university.view.patterns.display.strategy.SpiralDisplayStrategy;

public class SpiralViewState implements IDisplayState {
    @Override
    public IDisplayStrategy getStrategy() {
        return new SpiralDisplayStrategy();
    }

    @Override
    public String getName() {
        return "Спираль";
    }

    @Override
    public IDisplayState getNextState() {
        return new TableViewState();
    }
}