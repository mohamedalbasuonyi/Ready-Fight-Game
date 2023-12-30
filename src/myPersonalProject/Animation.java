package src.myPersonalProject;

import java.awt.*;

public class Animation/////Class obtained from class lectures  (not mine)
    // hello
{
    //Note: Use this Animation class  obtained from professor brian code to animate the Tank, Tank class will need to be changed
    private Image[] image;
    private int current = 0;
    private int duration;
    private int delay;

    private int start = 0;

    public Animation(String name, int count, int start, int duration, String type)
    {
        this.start    = start;

        this.duration = duration;
        delay         = duration;


        image = new Image[count];

        for(int i = 0; i < count; i++)
        {
            image[i] = Toolkit.getDefaultToolkit().getImage(name + "_" + i + "." + type);
        }
    }


    public Image getStaticImage()
    {
        return image[0];
    }

    public Image getCurrentImage()
    {
        delay--;

        if(delay == 0)
        {
            current++;

            if(current == image.length)  current = start;

            delay = duration;
        }

        return image[current];
    }


}
