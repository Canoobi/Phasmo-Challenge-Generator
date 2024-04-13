public class WheelElement {
    private String text;
    private String message;
    private Boolean openSelectItemFrame;
    private int[] reqPlayers;

    public WheelElement(String text, Boolean openSelectItemFrame, String message, int[] reqPlayers) {
        this.text = text;
        this.openSelectItemFrame = openSelectItemFrame;
        this.message = message;
        this.reqPlayers = reqPlayers;
    }

    public String getText() {
        return this.text;
    }

    public Boolean getOpenSelectItemFrame() {
        return this.openSelectItemFrame;
    }

    public String getMessage() {
        return this.message;
    }

    public int[] getReqPlayers() {
        return this.reqPlayers;
    }

    public Boolean containsRightPlayerNum(int playerNum) {
        for (int i = 0; i < this.reqPlayers.length; i++) {
            if (this.reqPlayers[i] == playerNum) {
                return true;
            }
        }
        return false;
    }
}
