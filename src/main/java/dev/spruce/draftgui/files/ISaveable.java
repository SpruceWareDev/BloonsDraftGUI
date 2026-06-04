package dev.spruce.draftgui.files;

public interface ISaveable {
    String save();
    void load(String data);
    String getSaveName();
}
