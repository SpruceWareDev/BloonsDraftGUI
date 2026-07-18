package dev.spruce.draftgui.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIManager {

    private final CopyOnWriteArrayList<UIComponent> components;

    public UIManager() {
        this.components = new CopyOnWriteArrayList<>();
    }

    public void addComponent(UIComponent component) {
        components.add(component);
    }

    public void removeComponent(UIComponent component) {
        components.remove(component);
    }

    public void render() {
        for (UIComponent component : components) {
            component.render();
        }
    }

    public void update() {
        for (UIComponent component : components) {
            component.update();
        }
    }

    public CopyOnWriteArrayList<UIComponent> getComponents() {
        return components;
    }

    public List<UIComponent> getComponentsByClass(Class<? extends UIComponent> clazz) {
        return components.stream()
                .filter(clazz::isInstance)
                .toList();
    }
}
