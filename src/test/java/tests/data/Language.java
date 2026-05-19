package tests.data;

public enum Language {
    rus("Новые поступления"),
    eng("New products");
    public final String description;

    Language(String description) {
        this.description = description;
    }
}
