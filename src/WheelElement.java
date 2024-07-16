public record WheelElement(String text, Boolean openSelectItemFrame, String message, int[] reqPlayers) {
    public Boolean containsRightPlayerNum(int playerNum) {
        for (int reqPlayer : this.reqPlayers) {
            if (reqPlayer == playerNum) {
                return true;
            }
        }
        return false;
    }
}
