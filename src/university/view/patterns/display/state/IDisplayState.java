package university.view.patterns.display.state;

import university.view.patterns.display.strategy.IDisplayStrategy;

public interface IDisplayState {
    IDisplayStrategy getStrategy();
    String getName();
    IDisplayState getNextState();
}
