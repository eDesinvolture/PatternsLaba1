package university.view.patterns.pool;

import java.util.ArrayList;
import java.util.List;

public class WidgetPool {
    private static WidgetPool instance;
    private final List<DisciplineWidget> available = new ArrayList<>();
    private final List<DisciplineWidget> inUse = new ArrayList<>();

    private WidgetPool() {}

    public static WidgetPool getInstance() {
        if (instance == null) {
            instance = new WidgetPool();
        }
        return instance;
    }

    public DisciplineWidget getWidget() {
        if (available.isEmpty()) {
            DisciplineWidget widget = new DisciplineWidget();
            inUse.add(widget);
            return widget;
        } else {
            DisciplineWidget widget = available.remove(0);
            inUse.add(widget);
            return widget;
        }
    }

    public void returnWidget(DisciplineWidget widget) {
        inUse.remove(widget);
        available.add(widget);
    }

    public void returnAll() {
        available.addAll(inUse);
        inUse.clear();
    }
}