package model;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class Entity {
    // position et dimension
    protected int x;
    protected int y;
    protected int x_relative = 0;
    protected int y_relative = 0;
    protected int height = 100;
    protected int width = 100;
    protected boolean is_colidable = true;
    Hitbox hitbox;
    Hitbox hitboxTemp;


    // image
    protected String image_path;
    protected BufferedImage image;

    // debug
    public boolean show_hitbox = false;

    Entity(int x,int y){
        this.x = x;
        this.y = y;
        image_path = "model\\images\\entity.png";
        load_image();
        this.load_hitbox();
    }

    public void move(int x,int y,Entities entities){
        this.x += x;
        this.y += y;
        this.hitbox.move(this.get_x(),this.get_y());
        this.can_move(x, y, entities);
    }

    public void move_relative(int x,int y,Entities entities){
        this.x_relative += x;
        this.y_relative += y;
        this.hitbox.move(this.get_x(),this.get_y());
    }
    
    public boolean can_move(int x, int y,Entities entities) {
    	//System.out.println(this.getClass().getName());
    	hitboxTemp = new Hitbox(this.get_x(), this.get_y() , this.get_width(), this.get_height());
		for(int i=0;i<entities.size();i++) {
			if(entities.get_by_id(i).get_hitbox().colide(hitboxTemp) && entities.get_by_id(i).is_colidable && entities.get_by_id(i) != this) {
				System.out.println(this.getClass().getName() + " " + entities.get_by_id(i).getClass().getName() + " colide");
				return false;
			}
		}
    	return true;
    }

    private Hitbox get_hitbox() {
		return this.hitbox;
	}

	public int get_x(){
        return x + x_relative;
    }
    public int get_y(){
        return y + y_relative;
    }

    public void load_image(){
        try {
            image = ImageIO.read(new File(image_path));
       }
       catch(IOException e) {
           System.err.println("image not load for "+this.getClass().getName());
       }
    }

    public Image get_image() {
        return image;
    }

    public int get_width(){
        return width;
    }

    public int get_height(){
        return height;
    }

    public int get_speed() {
        throw new UnsupportedOperationException("Unimplemented method 'get_speed'");
    }

    public boolean colide(Entity entity2) {
    	return this.hitbox.colide(entity2.hitbox);
    }

    public boolean colidable(){
        return is_colidable;
    }
    public void load_hitbox() {
    	this.hitbox = new Hitbox(this.get_x(),this.get_y(),this.get_width(),this.get_height());
    }
}
