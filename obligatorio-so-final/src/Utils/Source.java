package Utils;

public enum Source {
    input_wpp,
    input_app,
    input_web;

    private int currentLine;

    private Source(){
        currentLine = 0;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void addLines(int line){
        this.currentLine += line;
    }
}
