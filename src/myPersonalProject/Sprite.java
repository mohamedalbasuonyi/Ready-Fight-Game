package src.myPersonalProject;

import java.awt.*;

public class Sprite extends Rect////Class obtained from class lectures(not mine)
{
    //Note: After fixing the collision detection for walls
    // Use this Sprite class obtained from professor brian code to animate the Tank, Tank class will need to be changed.


    Animation[] animation;

    final static int UP = 0;
    final static int DN = 1;
    final static int LT = 2;
    final static int RT = 3;

    int pose = DN;

    static double scale = 1;

    boolean moving = false;

    public Sprite(String name, String[] pose, int imagecount, int start, String filetype, int x, int y, int w, int h)
    {
        super(x, y, w, h);

        animation = new Animation[pose.length];

        for(int i = 0; i < pose.length; i ++)
        {
            animation[i] = new Animation(name + "_" + pose[i], imagecount, start,  18, filetype);
        }
    }

    public void goLT(int dx)
    {
        pose = LT;

        moving = true;

        vx = -dx;
    }

    public void goRT(int dx)
    {
        pose = RT;

        moving = true;

        vx = dx;
    }

    public void goUP(int dy)
    {
        pose = UP;

        moving = true;

        vy = -dy;
    }

    public void goDN(int dy)
    {
        pose = DN;

        moving = true;

        vy = dy;
    }


    public void draw(Graphics pen)
    {
        Image temp;

        if (!moving)

            temp = animation[pose].getStaticImage();

        else

            temp = animation[pose].getCurrentImage();


        w = scale * temp.getWidth(null);
        h = scale * temp.getHeight(null);

        pen.drawImage(temp, (int)(x ), (int)(y ), (int)w, (int)h, null); ;

        //pen.drawRect((int)(x - Camera.x), (int)(y - Camera.y), (int)w, (int)h);
    }

}