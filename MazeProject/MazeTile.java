
public class MazeTile {
    public char type; // 'E': Empty, 'W': Wall, 'T': Trap, 'P': Power-up, 'G': Goal
    public int x, y;
    public boolean hasAgent;

    public MazeTile(char type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.hasAgent = false;
    }
    //Abi buralar seyahat edilebilir noktalar 
    public boolean isTraversable() {
        return type == 'E' || type == 'P' || type == 'G';
    }
    //burda ajan dediğimiz şey bulunuyor mu ?
    public boolean hasAgent() {
        return hasAgent;
    }

    public void setHasAgent(boolean hasAgent) {
        this.hasAgent = hasAgent;
    }

    public char getType() {
        return type;
    }
    @Override//bunu compiler yazmamız gerektiğini söyledi sarı uyarı veriyodu
    public String toString() {
        return hasAgent ? "A" : String.valueOf(type);
    }
    public void removeAgent() {
        this.hasAgent = false;
    }

    public void placeAgent() {
        this.hasAgent = true;
    }
}