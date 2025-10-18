package university.view.patterns.display.state;

import university.view.patterns.display.strategy.IDisplayStrategy;
import university.view.patterns.display.strategy.TableDisplayStrategy;

public class TableViewState implements IDisplayState {

    @Override
    public IDisplayStrategy getStrategy() {
        return new TableDisplayStrategy();
    }

    @Override
    public String getName() {
        return "Таблица";
    }
    @Override
    public IDisplayState getNextState(){
        return new SpiralViewState();
    }
}
