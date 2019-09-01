package objects;

public class Wolf implements Position, GraphicImage {
    public int liveTime = 50;
    public int moveSpeed = 2;
    public String sex;
    private int positionX;
    private int positionY;

    public Wolf() {}

    public Wolf(String sex, int positionX, int positionY) {
        this.sex = sex;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public String getGraphicImage() {
        return "W";
    }
}
